package com.pecaja.app.pecajamobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    //UI References
    private MaterialEditText editTextNome;
    private MaterialEditText editTextCpf;
    private MaterialEditText editTextTelefone;
    private MaterialEditText editTextEmail;
    private MaterialEditText editTextUsername;
    private MaterialEditText editTextPassword;
    private MaterialEditText editTextConfirmPassword;
    private MaterialEditText editTextRua;
    private MaterialEditText editTextNumero;
    private MaterialEditText editTextBairro;
    private MaterialSpinner spinnerCidades;
    private MaterialSpinner spinnerEstados;
    private Button buttonRegister;

    private Service mService;

    public RegisterActivity() {
        mService = Api.getAPIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        initView();
        setListener();
        getCidades();
        getEstados();
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
        editTextPassword = findViewById(R.id.et_password);
        editTextConfirmPassword = findViewById(R.id.et_confirm_password);
        editTextRua = findViewById(R.id.et_rua);
        editTextNumero = findViewById(R.id.et_numero);
        editTextBairro = findViewById(R.id.et_bairro);
        spinnerCidades = findViewById(R.id.spinner_cidades);
        spinnerEstados = findViewById(R.id.spinner_estados);
        buttonRegister = findViewById(R.id.btn_register);
    }

    /**
     *
     */
    private void setListener() {
        buttonRegister.setOnClickListener(v -> ValidClient());
    }

    /**
     *
     */
    private void getCidades() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Aguarde um momento", "Carregando cidades ...", true, false);

        mService.getCidades().enqueue(new Callback<List<Cidade>>() {
            @Override
            public void onResponse(Call<List<Cidade>> call, Response<List<Cidade>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    List<Cidade> cidades = response.body();
                    displayCidades(cidades);

                } else
                    Toast.makeText(RegisterActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Cidade>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     */
    private void getEstados() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Aguarde um momento", "Carregando estados ...", true, false);

        mService.getEstados().enqueue(new Callback<List<Estado>>() {
            @Override
            public void onResponse(Call<List<Estado>> call, Response<List<Estado>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    List<Estado> estados = response.body();
                    displayEstados(estados);

                } else
                    Toast.makeText(RegisterActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<List<Estado>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param cidades
     */
    private void displayCidades(List<Cidade> cidades) {

        // Creating adapter for spinner
        ArrayAdapter<Cidade> cidadeArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cidades);

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
        ArrayAdapter<Estado> estadoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);

        // Drop down layout style - list view with radio button
        estadoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerEstados.setAdapter(estadoArrayAdapter);
    }

    /**
     *
     */
    private void ValidClient() {

        if (isValidFields()) {

            // Get Data Endereço
            Estado estado = (Estado) spinnerEstados.getSelectedItem();
            Cidade cidade = (Cidade) spinnerCidades.getSelectedItem();
            String rua = editTextRua.getText().toString().trim();
            String bairro = editTextBairro.getText().toString().trim();
            int numero = Integer.parseInt(editTextNumero.getText().toString().trim());

            Endereco endereco = new Endereco(rua, bairro, numero, estado, cidade);

            // Get Data User
            String nome = editTextNome.getText().toString().trim();
            String cpf = editTextCpf.getText().toString().trim();
            String telefone = editTextTelefone.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String usuario = editTextUsername.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();

            Cliente cliente = new Cliente(nome,cpf, telefone, usuario, senha, email, endereco);

            registerClient(cliente);
        }

    }

    /**
     *
     * @param cliente
     */
    private void registerClient(Cliente cliente) {

        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Aguarde um momento", "Criando conta ...", true, false);

        mService.cadastrarCliente(cliente).enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    Toast.makeText(RegisterActivity.this, "Conta criada com sucesso. Faça Login agora mesmo!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                } else
                    Toast.makeText(RegisterActivity.this, "Falha ao realizar cadastro. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())
                || !Common.isValidPassword(editTextPassword.getText().toString().trim())) {

            editTextPassword.setError("Informe sua senha. No mínimo 4 caracteres");
            editTextPassword.setFocusable(true);
            editTextPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(editTextConfirmPassword.getText().toString().trim())
                || !editTextConfirmPassword.getText().toString().equalsIgnoreCase(editTextPassword.getText().toString().trim())) {

            editTextConfirmPassword.setError("A senha deve ser a mesma informa acima. Certifique-se de que é a mesma");
            editTextConfirmPassword.setFocusable(true);
            editTextConfirmPassword.requestFocus();
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

}
