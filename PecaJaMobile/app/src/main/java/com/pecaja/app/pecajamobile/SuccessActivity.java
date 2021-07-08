package com.pecaja.app.pecajamobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pecaja.app.pecajamobile.api.Api;
import com.pecaja.app.pecajamobile.api.Service;
import com.pecaja.app.pecajamobile.enums.CategoriaEnum;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.models.Pedido;
import com.pecaja.app.pecajamobile.models.Revendedor;
import com.pecaja.app.pecajamobile.models.User;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {

    private Cliente cliente;
    private Pedido pedido;

    // UI References
    private LinearLayout layoutCategoria;
    private TextView tvContador;
    private TextView tvCategoria;
    private TextView tvFantasia;
    private TextView tvData;
    private TextView tvEstimativa;
    private TextView tvEntrega;
    private TextView tvMarca;
    private TextView tvTotal;
    private Button buttonNovoPedido;
    private TextView tvLinkCancelarPedido;

    private Service mService;

    public SuccessActivity() {
        mService = Api.getAPIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.get("cliente");
        pedido = (Pedido) bundle.get("pedido");

        initView();
        setListener();
        displayPedido(pedido);
    }

    /**
     *
     */
    private void initView() {
        layoutCategoria = findViewById(R.id.layout_categoria);
        tvContador = findViewById(R.id.tv_contador);
        tvCategoria = findViewById(R.id.tv_categoria);
        tvFantasia = findViewById(R.id.tv_fantasia);
        tvData = findViewById(R.id.tv_data);
        tvEstimativa = findViewById(R.id.tv_estimativa);
        tvEntrega = findViewById(R.id.tv_entrega);
        tvMarca = findViewById(R.id.tv_marca);
        tvTotal = findViewById(R.id.tv_total);
        buttonNovoPedido = findViewById(R.id.btn_novo_pedido);
        tvLinkCancelarPedido = findViewById(R.id.tv_link_cancelar_pedido);
    }

    /**
     *
     */
    private void setListener() {
        buttonNovoPedido.setOnClickListener(v -> {
            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder = new AlertDialog.Builder(SuccessActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            else
                builder = new AlertDialog.Builder(SuccessActivity.this);

            builder.setTitle("Realizar novo pedido?");
            builder.setMessage("Clique em OK para iniciar um novo pedido.");
            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("cliente", cliente);
                finish();
                startActivity(intent);
            });
            builder.setNegativeButton(android.R.string.no, (dialog, which) -> {
                // do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        tvLinkCancelarPedido.setOnClickListener(v -> {

            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder = new AlertDialog.Builder(SuccessActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            else
                builder = new AlertDialog.Builder(SuccessActivity.this);

            builder.setTitle("Cancelar seu pedido ?");
            builder.setMessage("Clique em OK para cancelar o pedido no valor de\n Total R$ "
                    + new DecimalFormat("###,##0.00").format(pedido.getValor()).toString());
            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                cancelarPedido(pedido);

            });
            builder.setNegativeButton(android.R.string.no, (dialog, which) -> {
                // do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    /**
     *
     * @param pedido
     */
    private void cancelarPedido(Pedido pedido) {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Aguarde um momento", "Seu pedido está sendo cancelado", true, false);

        mService.deletePedidoById(pedido.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(SuccessActivity.this, "Seu pedido foi cancelado", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("cliente", cliente);
                    finish();
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Falha: " + response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SuccessActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param pedido
     */
    private void displayPedido(Pedido pedido) {

        String categoria = pedido.getProduto().getCategoria().getNome();
        if (categoria.equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
            layoutCategoria.setBackgroundColor(Color.parseColor("#439AB5"));
        else
            layoutCategoria.setBackgroundColor(Color.parseColor("#00923F"));

        tvCategoria.setText(pedido.getProduto() != null ?
                pedido.getProduto().getCategoria().getNome() : "INDEFINIDO");

        tvFantasia.setText(pedido.getProduto().getUser() != null ?
                pedido.getProduto().getUser().getFantasia() : "INDEFINIDO");

        tvMarca.setText(pedido.getProduto() != null ?
                pedido.getProduto().getMarca().getNome() : "INDEFINIDO");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        tvData.setText(date);

        tvEstimativa.setText(pedido != null ?
                pedido.getEstimativaEntrega() + " min" : "INDEFINIDO");

        tvEntrega.setText("AGUARDANDO");

        tvTotal.setText("R$ " + new DecimalFormat("###,##0.00").format(pedido.getValor()).toString());
    }

    /**
     *
     */
    private void logout() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(SuccessActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(SuccessActivity.this);

        builder.setTitle("Sair?");
        builder.setMessage("Clique em OK para sair da sua conta !");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            SharedPreferences sp = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();
            Toast.makeText(SuccessActivity.this, "Sessão encerrada", Toast.LENGTH_LONG).show();
            finish();   //finish current activity
            startActivity(new Intent(SuccessActivity.this, LoginActivity.class));
        });
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // do nothing
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     *
     */
    private void goToHistorico() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(SuccessActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(SuccessActivity.this);

        builder.setTitle("Visitar seu Histórico de Pedidos?");
        builder.setMessage("Clique em OK para sair e ir para histórico de pedidos!");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            Intent intent = new Intent(getApplicationContext(), HistoricoPedidoActivity.class);
            intent.putExtra("cliente", cliente);
            finish();
            startActivity(intent);
        });
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // do nothing
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_success, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout)
            logout();
        if (id == R.id.action_history)
            goToHistorico();

        return true;
    }

}
