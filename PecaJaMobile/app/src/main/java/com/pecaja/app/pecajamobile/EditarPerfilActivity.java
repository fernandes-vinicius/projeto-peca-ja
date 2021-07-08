package com.pecaja.app.pecajamobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.pecaja.app.pecajamobile.models.Cidade;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.models.Endereco;
import com.pecaja.app.pecajamobile.models.Estado;
import com.pecaja.app.pecajamobile.api.Service;
import com.pecaja.app.pecajamobile.api.Api;
import com.pecaja.app.pecajamobile.utils.Common;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {

    private Cliente cliente;

    //UI References
    private MaterialEditText editTextNome;
    private MaterialEditText editTextCpf;
    private MaterialEditText editTextTelefone;
    private MaterialEditText editTextEmail;
    private MaterialEditText editTextUsername;
    private MaterialEditText editTextRua;
    private MaterialEditText editTextNumero;
    private MaterialEditText editTextBairro;
    private MaterialSpinner spinnerCidades;
    private MaterialSpinner spinnerEstados;
    private Button buttonSave;

    private Service mService;

    public EditarPerfilActivity() {
        mService = Api.getAPIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
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
        editTextNome = findViewById(R.id.et_nome);
        editTextCpf = findViewById(R.id.et_cpf);
        editTextTelefone = findViewById(R.id.et_telefone);
        editTextEmail = findViewById(R.id.et_email);
        editTextUsername = findViewById(R.id.et_username);
        editTextRua = findViewById(R.id.et_rua);
        editTextNumero = findViewById(R.id.et_numero);
        editTextBairro = findViewById(R.id.et_bairro);
        spinnerCidades = findViewById(R.id.spinner_cidades);
        spinnerEstados = findViewById(R.id.spinner_estados);
        buttonSave = findViewById(R.id.btn_mudar_endereco);
    }

    /**
     *
     */
    private void setListener() {
        buttonSave.setOnClickListener(v -> validClient());
    }

    /**
     *
     * @param cliente
     */
    private void displayCliente(Cliente cliente) {

        List<Cidade> cidades = new ArrayList<>();
        cidades.add(cliente.getEndereco().getCidade());

        List<Estado> estados = new ArrayList<>();
        estados.add(cliente.getEndereco().getEstado());

        displayCidades(cidades);
        displayEstados(estados);

        spinnerCidades.setSelection(1, true);
        spinnerEstados.setSelection(1, true);

        editTextNome.setText( cliente != null ?
                cliente.getNome() : "");

        editTextCpf.setText( cliente != null ?
                cliente.getCpf() : "");

        editTextTelefone.setText( cliente != null ?
                cliente.getTelefone() : "");

        editTextEmail.setText( cliente != null ?
                cliente.getEmail() : "");

        editTextUsername.setText( cliente != null ?
                cliente.getUsername() : "");

        editTextRua.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getRua() : "");

        editTextNumero.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getNumero().toString() : "");

        editTextBairro.setText( cliente.getEndereco() != null ?
                cliente.getEndereco().getBairro() : "");
    }

    /**
     *
     * @param cidades
     */
    private void displayCidades(List<Cidade> cidades) {

        // Creating adapter for spinner
        ArrayAdapter<Cidade> cidadeArrayAdapter = new ArrayAdapter<Cidade>(this, android.R.layout.simple_spinner_item, cidades);

        // Drop down layout style - list view with radio button
        cidadeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCidades.setAdapter(cidadeArrayAdapter);
    }

    /**
     *
     * @param estados
     */
    private void displayEstados(List<Estado> estados) {

        // Creating adapter for spinner
        ArrayAdapter<Estado> estadoArrayAdapter = new ArrayAdapter<Estado>(this, android.R.layout.simple_spinner_item, estados);

        // Drop down layout style - list view with radio button
        estadoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerEstados.setAdapter(estadoArrayAdapter);
    }

    /**
     *
     */
    private void validClient() {

        if (isValidFields()) {

            // Get Data Endereço
            Estado estado = (Estado) spinnerEstados.getSelectedItem();
            Cidade cidade = (Cidade) spinnerCidades.getSelectedItem();
            String rua = editTextRua.getText().toString().trim();
            String bairro = editTextBairro.getText().toString().trim();
            int numero = Integer.parseInt(editTextNumero.getText().toString().trim());

            Endereco endereco = cliente.getEndereco();
            endereco.setEstado(estado);
            endereco.setCidade(cidade);
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setBairro(bairro);

            // Get Data User
            String nome = editTextNome.getText().toString().trim();
            String cpf = editTextCpf.getText().toString().trim();
            String telefone = editTextTelefone.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String usuario = editTextUsername.getText().toString().trim();
            String senha = cliente.getPassword();

            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setTelefone(telefone);
            cliente.setEmail(email);
            cliente.setUsername(usuario);
            cliente.setPassword(senha);
            cliente.setEndereco(endereco);

            updateClient(cliente);
        }
    }

    /**
     *
     * @param cliente
     */
    private void updateClient(Cliente cliente) {

        final ProgressDialog progressDialog = ProgressDialog.show(EditarPerfilActivity.this, "Aguarde um momento", "Atualizando seu perfil ...", true, false);

        mService.atualizarCliente(cliente.getId(), cliente).enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(EditarPerfilActivity.this, "Seus dados foram atualizados com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("cliente", cliente);
                    finish();
                    startActivity(intent);

                } else
                    Toast.makeText(EditarPerfilActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditarPerfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     *
     * @return
     */
    private boolean isValidFields() {

        if (TextUtils.isEmpty(editTextNome.getText().toString().trim()) || editTextNome.getText().length() < 3 ) {
            editTextNome.setError("Informe seu nome completo. No mínimo 3 caracteres");
            editTextNome.setFocusable(true);
            editTextNome.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextCpf.getText().toString().trim()) || editTextCpf.getText().length() < 11) {
            editTextCpf.setError("Informe seu CPF. ex; 01677890350");
            editTextCpf.setFocusable(true);
            editTextCpf.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextTelefone.getText().toString().trim()) || editTextTelefone.getText().length() < 11) {
            editTextTelefone.setError("Informe seu Telefone. ex; 84999954301");
            editTextTelefone.setFocusable(true);
            editTextTelefone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            editTextEmail.setError("Informe seu email");
            editTextEmail.setFocusable(true);
            editTextEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextUsername.getText().toString().trim())
                || !Common.isValidUsername(editTextUsername.getText().toString().trim().toLowerCase())) {

            editTextUsername.setError("Informe seu nome de usuário. No mínimo 3 caracteres");
            editTextUsername.setFocusable(true);
            editTextUsername.requestFocus();
            return false;
        }

        if (spinnerCidades.getSelectedItem() == null) {
            spinnerCidades.setError("Selecione uma cidade");
            spinnerCidades.setFocusable(true);
            spinnerCidades.requestFocus();
            return false;
        }

        if (spinnerEstados.getSelectedItem() == null) {
            spinnerEstados.setError("Selecione um estado");
            spinnerEstados.setFocusable(true);
            spinnerEstados.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextRua.getText().toString().trim()) || editTextRua.getText().length() < 10) {
            editTextRua.setError("Informe sua rua. No mínimo 10 caracteres");
            editTextRua.setFocusable(true);
            editTextRua.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextNumero.getText().toString().trim()) || editTextNumero.getText().length() <= 0) {
            editTextNumero.setError("");
            editTextNumero.setFocusable(true);
            editTextNumero.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextBairro.getText().toString().trim()) || editTextBairro.getText().length() < 3) {
            editTextBairro.setError("Informe o seu bairro. No mínimo 3 caracteres");
            editTextBairro.setFocusable(true);
            editTextBairro.requestFocus();
            return false;
        }
        return true;
    }

    /**
     *
     */
    private void logout() {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(EditarPerfilActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(EditarPerfilActivity.this);

        builder.setTitle("Sair?");
        builder.setMessage("Clique em OK para sair da sua conta !");
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {

            SharedPreferences sp = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.clear();
            e.commit();
            Toast.makeText(EditarPerfilActivity.this, "Sessão encerrada", Toast.LENGTH_LONG).show();
            finish();   //finish current activity
            startActivity(new Intent(EditarPerfilActivity.this, LoginActivity.class));
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
