package com.pecaja.app.pecajamobile;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pecaja.app.pecajamobile.enums.CategoriaEnum;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.models.Pedido;
import com.pecaja.app.pecajamobile.models.Produto;
import com.pecaja.app.pecajamobile.api.Service;
import com.pecaja.app.pecajamobile.api.Api;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarPedidoActivity extends AppCompatActivity {

    private Cliente cliente;
    private Pedido pedido;

    //UI References
    LinearLayout layoutCategoria;
    TextView tvCategoria;
    TextView tvFantasia;
    TextView tvNota;
    TextView tvPrevisao;
    TextView tvPreco;
    TextView tvPeso;
    TextView tvTotal;
    TextView tvNome;
    TextView tvTelefone;
    TextView tvRuaAndNumero;
    TextView tvBairroAndCidade;
    MaterialEditText etDicasEntregador;
    Button buttonQuantidade;
    Button buttonMudarEndereco;
    Button buttonFinalizarPedido;

    private Service mService;

    public FinalizarPedidoActivity() {
        mService = Api.getAPIService();
    }

    @SuppressLint({"PrivateResource", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.get("cliente");
        pedido = (Pedido) bundle.get("pedido");

        initView();
        setListener();
        displayProduto(pedido.getProduto());
        displayCliente(cliente);

        buttonFinalizarPedido.setText("Concluir pedido R$ "
                + new DecimalFormat("###,##0.00").format(pedido.getValor()));
    }

    /**
     *
     */
    private void initView() {
        layoutCategoria = findViewById(R.id.layout_categoria);
        tvCategoria = findViewById(R.id.tv_categoria);
        tvFantasia = findViewById(R.id.tv_fantasia);
        tvNota = findViewById(R.id.tv_nota);
        tvPrevisao = findViewById(R.id.tv_previsao);
        tvPreco = findViewById(R.id.tv_preco);
        tvPeso = findViewById(R.id.tv_peso);
        tvTotal = findViewById(R.id.tv_total);
        tvNome = findViewById(R.id.tv_nome);
        tvTelefone = findViewById(R.id.tv_telefone);
        tvRuaAndNumero = findViewById(R.id.tv_rua_and_numero);
        tvBairroAndCidade = findViewById(R.id.tv_bairro_and_cidade);
        etDicasEntregador = findViewById(R.id.et_dicas_entregador);
        buttonQuantidade = findViewById(R.id.btn_quantidade);
        buttonMudarEndereco = findViewById(R.id.btn_edit_locale);
        buttonFinalizarPedido = findViewById(R.id.btn_finalizar_pedido);
    }

    /**
     *
     */
    private void setListener() {

        buttonMudarEndereco.setOnClickListener(v -> {
            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder = new AlertDialog.Builder(FinalizarPedidoActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            else
                builder = new AlertDialog.Builder(FinalizarPedidoActivity.this);

            builder.setTitle("Sair e Editar Endereço ?");
            builder.setMessage("Perderá os dados do pedido até aqui.");
            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                Intent intent = new Intent(getApplicationContext(), EditarPerfilActivity.class);
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

        buttonFinalizarPedido.setOnClickListener(v -> {
            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder = new AlertDialog.Builder(FinalizarPedidoActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            else
                builder = new AlertDialog.Builder(FinalizarPedidoActivity.this);

            builder.setTitle("Concluir Pedido?");
            builder.setMessage("Clique em OK para concluir seu pedido. \n\nTotal R$ "
                    + new DecimalFormat("###,##0.00").format(pedido.getValor()));
            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

                realizarPedido(pedido);
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
     * @param produto
     */
    private void displayProduto(Produto produto) {

        String categoria = produto.getCategoria().getNome();
        if (categoria.equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
            layoutCategoria.setBackgroundColor(Color.parseColor("#439AB5"));
        else
            layoutCategoria.setBackgroundColor(Color.parseColor("#00923F"));

        tvCategoria.setText( (produto.getCategoria() != null) ?
                produto.getCategoria().getNome() :
                "INDEFINIDO");

        tvFantasia.setText( (produto.getUser() != null) ?
                produto.getUser().getFantasia() : "INEFINIDO");

        tvNota.setText("4,7 ✩");

        tvPrevisao.setText( (produto != null) ?
                produto.getEstimativa_entrega()
                        + "-" + ((produto.getEstimativa_entrega())+1) + " min" : "SEM PREVISÃO");

        tvPreco.setText("R$ " + new DecimalFormat("###,##0.00").format(produto.getPreco()).toString());

        buttonQuantidade.setText(String.valueOf(pedido.getQuantidade()));

        if (categoria.equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
            tvPeso.setText("Garrafão de " + produto.getPeso() + "L");
        else
            tvPeso.setText("Botijão de " + produto.getPeso() + "Kg");

        tvTotal.setText("R$ " + new DecimalFormat("###,##0.00").format(pedido.getValor()).toString());
    }

    /**
     *
     * @param cliente
     */
    private void displayCliente(Cliente cliente) {

        tvNome.setText( (cliente != null) ?
                cliente.getNome() : "");

        tvTelefone.setText( (cliente != null) ?
                cliente.getTelefone() : "");

        tvRuaAndNumero.setText( (cliente.getEndereco() != null) ?
                cliente.getEndereco().getRua() + " " + cliente.getEndereco().getNumero() : "");

        tvBairroAndCidade.setText( (cliente.getEndereco() != null) ?
                cliente.getEndereco().getBairro() + ", " + cliente.getEndereco().getCidade().getNome() : "");
    }

    /**
     *
     * @param pedido
     */
    private void realizarPedido(Pedido pedido) {

        String dicasEntregador = (!etDicasEntregador.getText().toString().trim().isEmpty()) ?
                etDicasEntregador.getText().toString().trim() : "";

        pedido.setCliente(cliente);
        pedido.setDicaEntregador(dicasEntregador);

        final ProgressDialog progressDialog = ProgressDialog.show(FinalizarPedidoActivity.this, "Aguarde um momento", "Enviando seu pedido ...", true, false);

        mService.cadastrarPedido(pedido).enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(FinalizarPedidoActivity.this, "Pedido realizado com sucesso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), SuccessActivity.class);
                    intent.putExtra("cliente", cliente);
                    intent.putExtra("pedido", response.body());
                    finish();
                    startActivity(intent);

                } else
                    Toast.makeText(getApplicationContext(), "Falha: " + response.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FinalizarPedidoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     */
    private void logout() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(FinalizarPedidoActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(FinalizarPedidoActivity.this);

        builder.setTitle("Sair?");
        builder.setMessage("Clique em OK para sair da sua conta !");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            SharedPreferences sp = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();
            Toast.makeText(FinalizarPedidoActivity.this, "Sessão encerrada", Toast.LENGTH_LONG).show();
            finish();   //finish current activity
            startActivity(new Intent(FinalizarPedidoActivity.this, LoginActivity.class));
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
        getMenuInflater().inflate(R.menu.menu_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout)
            logout();
        return true;
    }

}
