package com.pecaja.app.pecajamobile.utils;

import com.pecaja.app.pecajamobile.enums.SortTypeEnum;
import com.pecaja.app.pecajamobile.models.Pedido;
import com.pecaja.app.pecajamobile.models.Produto;
import com.pecaja.app.pecajamobile.models.Revendedor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CollectionsSortList {

    public static void sortProdutoByMaisRapido(List<Produto> produtos) {
        Collections.sort(produtos, (Object o1, Object o2) -> {
            Produto p1 = (Produto) o1;
            Produto p2 = (Produto) o2;
            return Integer.compare(p1.getEstimativa_entrega(), p2.getEstimativa_entrega());
        });
    }

    public static void sortProdutoByMaisBarato(List<Produto> produtos) {

        Collections.sort(produtos, (Object o1, Object o2) -> {
            Produto p1 = (Produto) o1;
            Produto p2 = (Produto) o2;
            return Double.compare(p1.getPreco(), p2.getPreco());
        });
    }

    public static void sortProdutoByMelhorAvaliacao(List<Produto> produtos) {
        // TODO
    }

}
