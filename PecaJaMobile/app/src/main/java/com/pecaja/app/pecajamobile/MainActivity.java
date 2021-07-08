package com.pecaja.app.pecajamobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pecaja.app.pecajamobile.adapters.ProdutoAdapter;
import com.pecaja.app.pecajamobile.enums.CategoriaEnum;
import com.pecaja.app.pecajamobile.enums.SortTypeEnum;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.models.Produto;
import com.pecaja.app.pecajamobile.models.Revendedor;
import com.pecaja.app.pecajamobile.api.Service;
import com.pecaja.app.pecajamobile.api.Api;
import com.pecaja.app.pecajamobile.utils.CollectionsSortList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Cliente cliente;
    private List<Revendedor> revendedores;
    private List<Produto> produtos;

    // UI References
    private ListView listViewProdutos;
    private TextView tvRuaAndNumero;
    private TextView tvBairroAndCidade;
    private Button btnEditLocation;
    private RadioGroup radioGroupCategoria;
    private RadioButton radioButtonCategoriaAgua;

    private Service mService;

    public MainActivity() {
        mService = Api.getAPIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.get("cliente");

        initView();
        setListener();
        getProdutosRevendedoresProximos(cliente);

        // Display Endereço Cliente
        tvRuaAndNumero.setText(cliente.getEndereco() != null ?
                cliente.getEndereco().getRua() + " " + cliente.getEndereco().getNumero() : "");
        tvBairroAndCidade.setText(cliente.getEndereco() != null ?
                cliente.getEndereco().getBairro() + ", " + cliente.getEndereco().getCidade().getNome() : "");
    }

    /**
     *
     */
    private void initView() {
        btnEditLocation = findViewById(R.id.btn_edit_locale);
        listViewProdutos = findViewById(R.id.listview_produtos);
        tvRuaAndNumero = findViewById(R.id.tv_rua_and_numero);
        tvBairroAndCidade = findViewById(R.id.tv_bairro_and_cidade);
        radioGroupCategoria = findViewById(R.id.radio_group_categoria);
        radioButtonCategoriaAgua = findViewById(R.id.radio_btn_agua);
    }

    /**
     *
     */
    private void setListener() {

        btnEditLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditarPerfilActivity.class);
            intent.putExtra("cliente", cliente);
            startActivity(intent);
        });

        radioGroupCategoria.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_btn_agua)
                displayProdutos(produtos, CategoriaEnum.ÁGUA, SortTypeEnum.MAIS_RAPIDO);
            else if (checkedId == R.id.radio_btn_gas)
                displayProdutos(produtos, CategoriaEnum.GÁS, SortTypeEnum.MAIS_RAPIDO);
        });

        listViewProdutos.setOnItemClickListener((parent, view, position, id) -> {
            Produto produto = (Produto) view.getTag();

            Intent intent = new Intent(getApplicationContext(), PedidoActivity.class);
            intent.putExtra("cliente", cliente);
            intent.putExtra("produto", produto);
            startActivity(intent);
        });
    }

    /**
     *
     * @param cliente
     */
    private void getProdutosRevendedoresProximos(Cliente cliente) {

        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Aguarde um momento", "Carregando revendedores próximos à você ...", true, false);

        mService.getRevendedoresPoximos(cliente.getEndereco().getShortEndereco()).enqueue(new Callback<List<Revendedor>>() {
            @Override
            public void onResponse(Call<List<Revendedor>> call, Response<List<Revendedor>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    revendedores = response.body();
                    produtos = new ArrayList<>();

                    for (Revendedor r : revendedores) {

                        for (Produto p : r.getProdutos()) {
                            p.setEstimativa_entrega(r.getEstimativaEntrega());
                            produtos.add(p);
                        }
                    }

                    radioButtonCategoriaAgua.setChecked(true);
                    displayProdutos(produtos, CategoriaEnum.ÁGUA, SortTypeEnum.MAIS_RAPIDO);

                } else
                    Toast.makeText(getApplicationContext(), "Falha: " + response.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Revendedor>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param produtos
     * @param categoriaEnum
     * @param sortTypeEnum
     */
    private void displayProdutos(List<Produto> produtos, CategoriaEnum categoriaEnum, SortTypeEnum sortTypeEnum) {

        List<Produto> produtosByCategoria;
        if (produtos != null) {

            produtosByCategoria = getProdutosByCategoria(produtos, categoriaEnum);
            sortProdutos(produtosByCategoria, sortTypeEnum);

            ProdutoAdapter produtoAdapter = new ProdutoAdapter(this, produtosByCategoria);
            listViewProdutos.setAdapter(produtoAdapter);
        }
    }

    /**
     *
     * @param produtos
     * @param categoriaEnum
     * @return
     */
    private List<Produto> getProdutosByCategoria(List<Produto> produtos, CategoriaEnum categoriaEnum) {

        List<Produto> produtosByCategoria = new ArrayList<>();

        if (categoriaEnum == CategoriaEnum.ÁGUA) {

            for (Produto p : produtos) {
                if (p.getCategoria().getNome().equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
                    produtosByCategoria.add(p);
            }

        } else {

            for (Produto p : produtos) {
                if (p.getCategoria().getNome().equalsIgnoreCase(CategoriaEnum.GÁS.toString()))
                    produtosByCategoria.add(p);
            }
        }
        return produtosByCategoria;
    }

    /**
     *
     * @param produtos
     * @param sortTypeEnum
     */
    private void sortProdutos(List<Produto> produtos, SortTypeEnum sortTypeEnum) {

        if (sortTypeEnum == SortTypeEnum.MAIS_RAPIDO)
            CollectionsSortList.sortProdutoByMaisRapido(produtos);
        else if (sortTypeEnum == SortTypeEnum.MAIS_BARATO)
            CollectionsSortList.sortProdutoByMaisBarato(produtos);
        else
            CollectionsSortList.sortProdutoByMelhorAvaliacao(produtos);
    }

    /**
     *
     */
    private void logout() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Sair?");
        builder.setMessage("Clique em OK para sair da sua conta !");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            SharedPreferences sp = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();
            finish();   //finish current activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            Toast.makeText(getApplicationContext(), "Sessão encerrada", Toast.LENGTH_LONG).show();
        });
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // do nothing
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        int idRadioCategoriaChecked = radioGroupCategoria.getCheckedRadioButtonId();

        if (id == R.id.action_refresh)
            getProdutosRevendedoresProximos(cliente);

        else if (id == R.id.action_filter_by_mais_rapido) {

            if (idRadioCategoriaChecked == R.id.radio_btn_agua)
                displayProdutos(produtos, CategoriaEnum.ÁGUA, SortTypeEnum.MAIS_RAPIDO);
             else
                displayProdutos(produtos, CategoriaEnum.GÁS, SortTypeEnum.MAIS_RAPIDO);

        } else if (id == R.id.action_filter_by_mais_barato) {

            if (idRadioCategoriaChecked == R.id.radio_btn_agua)
                displayProdutos(produtos, CategoriaEnum.ÁGUA, SortTypeEnum.MAIS_BARATO);
            else
                displayProdutos(produtos, CategoriaEnum.GÁS, SortTypeEnum.MAIS_BARATO);

        } else if (id == R.id.action_filter_by_melhor_avaliacao) {

            if (idRadioCategoriaChecked == R.id.radio_btn_agua)
                displayProdutos(produtos, CategoriaEnum.ÁGUA, SortTypeEnum.MELHOR_AVALIACAO);
            else
                displayProdutos(produtos, CategoriaEnum.GÁS, SortTypeEnum.MELHOR_AVALIACAO);
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_history) {

            Intent intent = new Intent(getApplicationContext(), HistoricoPedidoActivity.class);
            intent.putExtra("cliente", cliente);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {

            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            intent.putExtra("cliente", cliente);
            startActivity(intent);

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_logout)
            logout();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

}
