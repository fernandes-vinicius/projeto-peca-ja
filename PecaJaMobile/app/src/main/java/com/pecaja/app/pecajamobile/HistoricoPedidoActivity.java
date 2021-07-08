package com.pecaja.app.pecajamobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pecaja.app.pecajamobile.adapters.PedidoAdapter;
import com.pecaja.app.pecajamobile.enums.CategoriaEnum;
import com.pecaja.app.pecajamobile.enums.SortTypeEnum;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.models.Pedido;
import com.pecaja.app.pecajamobile.api.Service;
import com.pecaja.app.pecajamobile.api.Api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoPedidoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Cliente cliente;
    private List<Pedido> pedidos;

    //UI Reference
    private ListView listViewPedidos;
    private RadioGroup radioGroupCategoria;
    private RadioButton radioButtonCategoriaAgua;

    private Service mService;

    public HistoricoPedidoActivity() {
        mService = Api.getAPIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_pedido);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.get("cliente");

        initView();
        setListener();
        getPedidos(cliente);
    }

    private void initView() {
        listViewPedidos = findViewById(R.id.listview_pedidos);
        radioGroupCategoria = findViewById(R.id.radio_group_categoria);
        radioButtonCategoriaAgua = findViewById(R.id.radio_btn_agua);
    }

    private void setListener() {

        radioGroupCategoria.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_btn_agua)
                displayPedidos(pedidos, CategoriaEnum.ÁGUA, SortTypeEnum.AGUARDANDO);
            else if (checkedId == R.id.radio_btn_gas)
                displayPedidos(pedidos, CategoriaEnum.GÁS, SortTypeEnum.AGUARDANDO);
        });

    }

    /**
     *
     * @param cliente
     */
    private void getPedidos(Cliente cliente) {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Aguarde um momento", "Carregando pedidos ...", true, false);

        mService.getPedidos(cliente.getId()).enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    pedidos = response.body();
                    radioButtonCategoriaAgua.setChecked(true);
                    displayPedidos(pedidos, CategoriaEnum.ÁGUA, SortTypeEnum.AGUARDANDO);
                } else
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param pedidos
     */
    private void displayPedidos(List<Pedido> pedidos, CategoriaEnum categoriaEnum, SortTypeEnum sortTypeEnum) {

        List<Pedido> pedidosByCategoria;
        if (pedidos != null) {

            pedidosByCategoria = getPedidosByCategoria(pedidos, categoriaEnum);
            Collections.reverse(pedidos);

            PedidoAdapter pedidoAdapter = new PedidoAdapter(this, pedidosByCategoria);
            listViewPedidos.setAdapter(pedidoAdapter);
        }
    }

    /**
     *
     * @param pedidos
     * @param categoriaEnum
     * @return
     */
    private List<Pedido> getPedidosByCategoria(List<Pedido> pedidos, CategoriaEnum categoriaEnum) {

        List<Pedido> pedidosByCategoria = new ArrayList<>();

        if (categoriaEnum == CategoriaEnum.ÁGUA) {

            for (Pedido p : pedidos) {
                if (p.getProduto().getCategoria().getNome().equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
                    pedidosByCategoria.add(p);
            }

        } else {

            for (Pedido p : pedidos) {
                if (p.getProduto().getCategoria().getNome().equalsIgnoreCase(CategoriaEnum.GÁS.toString()))
                    pedidosByCategoria.add(p);
            }
        }
        return pedidosByCategoria;
    }

    /**
     *
     * @param query
     * @return
     */
    private List<Pedido> findPedidoByQuery(String query) {

        List<Pedido> pedidosByQuery = new ArrayList<>();

        if (pedidos != null) {

            for (Pedido p: pedidos) {

                if (p.getValor().toString().contains(query)
                        || p.getProduto().getCategoria().getNome().toLowerCase().contains(query.toLowerCase())
                        || p.getRevendedor().getFantasia().toLowerCase().contains(query.toLowerCase())
                        || p.getQuantidade().toString().contains(query)
                        || p.getProduto().getMarca().toString().toLowerCase().contains(query.toLowerCase())
                        || p.getData().toString().contains(query) ) {
                    pedidosByQuery.add(p);
                }
            }
        }
        return pedidosByQuery;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historico_pedido, menu);

        //Pega o Componente.
        SearchView mSearchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        //Define um texto de ajuda:
        mSearchView.setQueryHint("Buscar pedido");

        // exemplos de utilização:
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh)
            getPedidos(cliente);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        List<Pedido> pedidos = findPedidoByQuery(query);

        if (radioButtonCategoriaAgua.isChecked())
            displayPedidos(pedidos, CategoriaEnum.ÁGUA, SortTypeEnum.AGUARDANDO);
         else
            displayPedidos(pedidos, CategoriaEnum.GÁS, SortTypeEnum.AGUARDANDO);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        List<Pedido> pedidos = findPedidoByQuery(newText);

        if (radioButtonCategoriaAgua.isChecked())
            displayPedidos(pedidos, CategoriaEnum.ÁGUA, SortTypeEnum.AGUARDANDO);
        else
            displayPedidos(pedidos, CategoriaEnum.GÁS, SortTypeEnum.AGUARDANDO);

        return false;
    }
}
