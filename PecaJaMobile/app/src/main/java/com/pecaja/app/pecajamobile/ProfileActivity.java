package com.pecaja.app.pecajamobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pecaja.app.pecajamobile.models.Cliente;

public class ProfileActivity extends AppCompatActivity {

    private Cliente cliente;

    //UI References
    private TextView tvNome;
    private TextView tvCpf;
    private TextView tvTelefone;
    private TextView tvEmail;
    private TextView tvUsuario;
    private TextView tvCidade;
    private TextView tvEstado;
    private TextView tvRua;
    private TextView tvNumero;
    private TextView tvBairro;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        Bundle bundle = getIntent().getExtras();
        cliente = (Cliente) bundle.get("cliente");

        initView();
        setListener();
        displayCliente(cliente);
    }

    /**
     *
     */
    private void initView() {
        tvNome = findViewById(R.id.tv_nome);
        tvCpf = findViewById(R.id.tv_cpf);
        tvTelefone = findViewById(R.id.tv_telefone);
        tvEmail = findViewById(R.id.tv_email);
        tvUsuario = findViewById(R.id.tv_username);
        tvCidade = findViewById(R.id.tv_cidade);
        tvEstado = findViewById(R.id.tv_estado);
        tvRua = findViewById(R.id.tv_rua);
        tvNumero = findViewById(R.id.tv_numero);
        tvBairro = findViewById(R.id.tv_bairro);
        fab = findViewById(R.id.fab_edit_profile);
    }

    /**
     *
     */
    private void setListener () {
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditarPerfilActivity.class);
            intent.putExtra("cliente", cliente);
            finish();
            startActivity(intent);
        });
    }

    /**
     *
     * @param cliente
     */
    private void displayCliente(Cliente cliente) {

        tvNome.setText( cliente != null ?
            cliente.getNome() : "");

        tvCpf.setText( cliente != null ?
                cliente.getCpf() : "");

        tvTelefone.setText( cliente != null ?
                cliente.getTelefone() : "");

        tvEmail.setText( cliente != null ?
                cliente.getEmail() : "");

        tvUsuario.setText( cliente != null ?
                cliente.getUsername() : "");

        tvCidade.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getCidade().getNome() : "");

        tvEstado.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getEstado().getUf() : "");

        tvRua.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getRua() : "");

        tvNumero.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getNumero().toString() : "");

        tvBairro.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getBairro() : "");
    }

    /**
     *
     */
    private void logout() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(ProfileActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(ProfileActivity.this);

        builder.setTitle("Sair?");
        builder.setMessage("Clique em OK para sair da sua conta !");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            SharedPreferences sp = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();
            Toast.makeText(ProfileActivity.this, "Sessão encerrada", Toast.LENGTH_LONG).show();
            finish();   //finish current activity
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
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
