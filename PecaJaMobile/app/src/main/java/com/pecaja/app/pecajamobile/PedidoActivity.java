package com.pecaja.app.pecajamobile;

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

import java.text.DecimalFormat;

public class PedidoActivity extends AppCompatActivity {

    private Cliente cliente;
    private Produto produto;
    private Pedido pedido;

    // UI References
    LinearLayout layoutCategoria;
    TextView tvCategoria;
    TextView tvFantasia;
    TextView tvNota;
    TextView tvPrevisao;
    TextView tvPreco;
    TextView tvMarca;
    TextView tvTotal;
    TextView tvMarcaCard;
    TextView tvPrecoCard;
    TextView tvPeso;
    Button buttonDecrement;
    Button buttonIncrement;
    Button buttonQuantidade;
    Button buttonAvancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.get("cliente");
        produto = (Produto) bundle.get("produto");

        initView();
        setListener();
        displayProduto(produto);

        buttonAvancar.setTextColor(Color.parseColor("#EEEEEE"));
        buttonAvancar.getBackground().setAlpha(100);
        buttonAvancar.setEnabled(false);
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
        tvMarca = findViewById(R.id.tv_marca);
        tvTotal = findViewById(R.id.tv_total);
        tvMarcaCard = findViewById(R.id.tv_marca_card);
        tvPrecoCard = findViewById(R.id.tv_preco_card);
        tvPeso = findViewById(R.id.tv_peso);
        buttonDecrement = findViewById(R.id.btn_decrement);
        buttonIncrement = findViewById(R.id.btn_increment);
        buttonQuantidade = findViewById(R.id.btn_quantidade);
        buttonAvancar = findViewById(R.id.btn_avancar);
    }

    /**
     *
     */
    private void setListener() {

        buttonDecrement.setOnClickListener(v -> {
            int qtd =  Integer.parseInt(buttonQuantidade.getText().toString().trim());
            diminuirTotal(qtd-1);
        });

        buttonIncrement.setOnClickListener(v -> {
            int qtd =  Integer.parseInt(buttonQuantidade.getText().toString().trim());
            aumentarTotal(qtd+1);
        });

        buttonAvancar.setOnClickListener(v -> {
            pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setEstimativaEntrega(produto.getEstimativa_entrega());
            pedido.setProduto(produto);
            pedido.setQuantidade(Integer.parseInt(buttonQuantidade.getText().toString().trim()));
            pedido.setValor(Double.parseDouble(tvTotal.getText().toString().trim()
                            .replace("R$", "")
                            .replace(",",".")));

            avancar(pedido);
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

        tvPreco.setText( (produto != null) ?
                "R$ " + new DecimalFormat("###,##0.00").format(produto.getPreco()).toString() :
                "INEFINIDO");

        tvMarca.setText( (produto.getMarca() != null) ?
                produto.getMarca().getNome() :
                "INEFINIDO");

        tvMarcaCard.setText((produto.getMarca() != null) ?
                produto.getMarca().getNome() :
                "INEFINIDO");

        tvPrecoCard.setText( (produto != null) ?
                "R$ " + new DecimalFormat("###,##0.00").format(produto.getPreco()).toString() + " uni" :
                "INDEFINIDO");

        if (categoria.equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
            tvPeso.setText("Garrafão de " + produto.getPeso() + " L");
        else
            tvPeso.setText("Botijão de " + produto.getPeso() + " Kg");
    }

    /**
     *
     * @param quantidade
     */
    private void diminuirTotal(int quantidade) {

        if (quantidade > 0) {
            double total = (produto.getPreco() * quantidade);
            tvTotal.setText("R$ " + new DecimalFormat("###,##0.00").format(total).toString());
            buttonQuantidade.setText(String.valueOf(quantidade));
            buttonAvancar.setText("Avançar " + tvTotal.getText());
        } else {

            if (quantidade == 0) {
                double total = (produto.getPreco() * quantidade);
                tvTotal.setText("R$ " + new DecimalFormat("###,##0.00").format(total).toString());
                buttonQuantidade.setText(String.valueOf(quantidade));
            }
            buttonAvancar.setText("Avançar");
            buttonAvancar.setTextColor(Color.parseColor("#EEEEEE"));
            buttonAvancar.getBackground().setAlpha(100);
            buttonAvancar.setEnabled(false);
        }
    }

    /**
     *
     * @param quantidade
     */
    private void aumentarTotal(int quantidade) {

        if (quantidade <= produto.getQuantidade()) {
            double total = (produto.getPreco() * quantidade);
            tvTotal.setText("R$ " + new DecimalFormat("###,##0.00").format(total).toString());
            buttonQuantidade.setText(String.valueOf(quantidade));
        }
        buttonAvancar.setText("Avançar " + tvTotal.getText());
        buttonAvancar.setTextColor(Color.WHITE);
        buttonAvancar.getBackground().setAlpha(255);
        buttonAvancar.setEnabled(true);
    }

    /**
     *
     * @param pedido
     */
    private void avancar(Pedido pedido) {
        Intent intent = new Intent(getApplicationContext(), FinalizarPedidoActivity.class);
        intent.putExtra("cliente", cliente);
        intent.putExtra("pedido", pedido);
        startActivity(intent);
    }

    /**
     *
     */
    private void logout() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(PedidoActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(PedidoActivity.this);

        builder.setTitle("Sair?");
        builder.setMessage("Clique em OK para sair da sua conta !");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            SharedPreferences sp = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();
            Toast.makeText(PedidoActivity.this, "Sessão encerrada", Toast.LENGTH_LONG).show();
            startActivity(new Intent(PedidoActivity.this, LoginActivity.class));
            finish();   //finish current activity
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
