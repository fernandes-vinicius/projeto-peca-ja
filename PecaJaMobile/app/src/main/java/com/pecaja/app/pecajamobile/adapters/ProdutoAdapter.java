package com.pecaja.app.pecajamobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pecaja.app.pecajamobile.R;
import com.pecaja.app.pecajamobile.enums.CategoriaEnum;
import com.pecaja.app.pecajamobile.models.Produto;

import java.text.DecimalFormat;
import java.util.List;

public class ProdutoAdapter extends BaseAdapter {

    private Context context;
    private List<Produto> produtos;

    // UI References
    LinearLayout layoutCategoria;
    TextView tvContador;
    TextView tvCategoria;
    TextView tvFantasia;
    TextView tvNota;
    TextView tvPrevisao;
    TextView tvPreco;
    TextView tvMarca;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.card_produto, null);
        initView(v);

        String categoria = produtos.get(position).getCategoria().getNome();
        if (categoria.equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
            layoutCategoria.setBackgroundColor(Color.parseColor("#439AB5"));
        else
            layoutCategoria.setBackgroundColor(Color.parseColor("#00923F"));

        tvContador.setText(Integer.toString(position+1));

        tvCategoria.setText( (produtos.get(position).getCategoria() != null) ?
                produtos.get(position).getCategoria().getNome() :
                "INDEFINIDO");

        tvFantasia.setText( (produtos.get(position).getUser() != null) ?
                produtos.get(position).getUser().getFantasia() : "INDEFINIDO");

        tvNota.setText("4,7 ✩");

        tvPrevisao.setText( (produtos.get(position) != null) ?
                produtos.get(position).getEstimativa_entrega()
                        + "-" + ((produtos.get(position).getEstimativa_entrega())+1) + " min" : "INDEFINIDO");

        tvPreco.setText( (produtos != null) ?
                "R$ " + new DecimalFormat("###,##0.00").format(produtos.get(position).getPreco()).toString() :
                "INDEFINIDO");

        tvMarca.setText( (produtos.get(position).getMarca() != null) ?
                produtos.get(position).getMarca().getNome() :
                "INEFINIDO");

        v.setTag(produtos.get(position));

        return v;
    }

    private void initView(View v) {
        layoutCategoria = v.findViewById(R.id.layout_categoria);
        tvContador = v.findViewById(R.id.tv_contador);
        tvCategoria = v.findViewById(R.id.tv_categoria);
        tvFantasia = v.findViewById(R.id.tv_fantasia);
        tvNota = v.findViewById(R.id.tv_nota);
        tvPrevisao = v.findViewById(R.id.tv_previsao);
        tvPreco = v.findViewById(R.id.tv_preco);
        tvMarca = v.findViewById(R.id.tv_marca);
    }

}
