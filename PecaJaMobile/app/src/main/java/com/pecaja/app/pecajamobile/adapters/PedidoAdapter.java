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
import com.pecaja.app.pecajamobile.models.Pedido;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PedidoAdapter extends BaseAdapter {

    private Context context;
    private List<Pedido> pedidos;

    // UI References
    LinearLayout layoutCategoria;
    TextView tvContador;
    TextView tvCategoria;
    TextView tvFantasia;
    TextView tvData;
    TextView tvEstimativa;
    TextView tvEntrega;
    TextView tvMarca;
    TextView tvTotal;

    public PedidoAdapter(Context context, List<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.card_pedido, null);

        layoutCategoria = v.findViewById(R.id.layout_categoria);
        tvContador = v.findViewById(R.id.tv_contador);
        tvCategoria = v.findViewById(R.id.tv_categoria);
        tvFantasia = v.findViewById(R.id.tv_fantasia);
        tvData = v.findViewById(R.id.tv_data);
        tvEstimativa = v.findViewById(R.id.tv_estimativa);
        tvEntrega = v.findViewById(R.id.tv_entrega);
        tvMarca = v.findViewById(R.id.tv_marca);
        tvTotal = v.findViewById(R.id.tv_total);

        String categoria = pedidos.get(position).getProduto().getCategoria().getNome();
        if (categoria.equalsIgnoreCase(CategoriaEnum.ÁGUA.toString()))
            layoutCategoria.setBackgroundColor(Color.parseColor("#439AB5"));
        else
            layoutCategoria.setBackgroundColor(Color.parseColor("#00923F"));

        tvContador.setText(Integer.toString(position+1));

        tvCategoria.setText( (pedidos.get(position).getProduto().getCategoria() != null) ?
                pedidos.get(position).getProduto().getCategoria().getNome() :
                "INDEFINIDO");

        tvFantasia.setText( (pedidos.get(position).getProduto().getUser() != null) ?
                pedidos.get(position).getProduto().getUser().getFantasia() : "SEM NOME FANTASIA");

        tvMarca.setText(pedidos.get(position).getProduto() != null ?
                pedidos.get(position).getProduto().getMarca().getNome() :
                "INDEFINIDO");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(pedidos.get(position).getData());
        tvData.setText(date);

        tvEstimativa.setText(pedidos.get(position).getEstimativaEntrega() + " min");

        tvEntrega.setText(pedidos.get(position).getStatusPedido());

        tvTotal.setText("R$ " + new DecimalFormat("###,##0.00").format(pedidos.get(position).getValor()).toString());

        v.setTag(pedidos.get(position));

        return v;
    }

}
