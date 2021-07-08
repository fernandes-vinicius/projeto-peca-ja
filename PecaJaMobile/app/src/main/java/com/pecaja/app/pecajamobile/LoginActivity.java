package com.pecaja.app.pecajamobile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pecaja.app.pecajamobile.models.Cliente;
import com.pecaja.app.pecajamobile.utils.Common;
import com.pecaja.app.pecajamobile.api.Service;
import com.pecaja.app.pecajamobile.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // UI References
    private EditText etUsername;
    private EditText etPassword;
    private Button btEntrar;
    private TextView tvLinkRegister;

    private SharedPreferences sharedPreferences;

    private Service mService = Api.getAPIService();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("KEY_LOGIN", MODE_PRIVATE);
        if(sharedPreferences.contains("cliente")){

            Cliente cliente = new Gson().fromJson(sharedPreferences.getString("cliente", ""), Cliente.class);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("cliente", cliente);
            finish();
            startActivity(intent);
        }

        initView();
        setListener();
    }

    /**
     *
     */
    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btEntrar = findViewById(R.id.btn_login);
        tvLinkRegister = findViewById(R.id.tv_link_register);
    }

    /**
     *
     */
    private void setListener() {

        btEntrar.setOnClickListener(v -> logar());

        tvLinkRegister.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });
    }

    /**
     *
     */
    private void logar() {

        if (isValidFields()) {

            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Aguarde um momento", "Autenticando ...", true, false);

            mService.login(username, password).enqueue(new Callback<Cliente>() {
                @Override
                public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {

                        Cliente cliente = response.body();

                        SharedPreferences.Editor e = sharedPreferences.edit();
                        e.putString("cliente", new Gson().toJson(cliente));
                        e.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("cliente", cliente);
                        finish();
                        startActivity(intent);

                    } else
                        Toast.makeText(LoginActivity.this, "Usuário e/ou senha inválidos. Tente Novamente", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Cliente> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     *
     * @return
     */
    private boolean isValidFields() {

        if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
            etUsername.setError("Informe seu usuário");
            etUsername.setFocusable(true);
            etUsername.requestFocus();
            return false;
        }

        if (!Common.isValidUsername(etUsername.getText().toString().trim().toLowerCase())) {
            etUsername.setError("Seu usuário deve ter menos 3 caracteres e no máximo 45");
            etUsername.setFocusable(true);
            etUsername.requestFocus();
            return false;
        }

        if (!Common.isValidPassword(etPassword.getText().toString().trim())){
            etPassword.setError("Sua senha deve ter pelo menos 4 caracteres");
            etPassword.setFocusable(true);
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

}
