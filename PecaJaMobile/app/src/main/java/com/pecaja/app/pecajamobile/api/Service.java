package com.pecaja.app.pecajamobile.api;

import com.pecaja.app.pecajamobile.models.Cidade;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.models.Estado;
import com.pecaja.app.pecajamobile.models.Pedido;
import com.pecaja.app.pecajamobile.models.Revendedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @POST("api/cliente/login")
    Call<Cliente> login(@Query("username") String username, @Query("password") String password);

    @POST("api/cliente")
    Call<Cliente> cadastrarCliente(@Body Cliente cliente);

    @GET("api/cliente/pedidos/{id}")
    Call<List<Pedido>> getPedidos(@Path("id") Long id);

    @GET("api/estados")
    Call<List<Estado>> getEstados();

    @GET("api/cidades")
    Call<List<Cidade>> getCidades();

    @GET("api/revendedor/listProximos/{origem}")
    Call<List<Revendedor>> getRevendedoresPoximos(@Path("origem") String origem);

    @POST("api/pedido")
    Call<Pedido> cadastrarPedido(@Body Pedido pedido);

    @PUT("api/cliente/{id}")
    Call<Cliente> atualizarCliente(@Path("id") Long id, @Body Cliente cliente);

    @DELETE("api/pedido/{id}")
    Call<Void> deletePedidoById(@Path("id") Long id);

}
