package com.example.matheusbrito.aula1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheusbrito.aula1.Model.Usuario;
import com.example.matheusbrito.aula1.Service.LoginService;

/**
 * Created by Matheus Brito on 16/04/2018.
 */

public class CadastrarActivity extends AppCompatActivity {
    private EditText txtLogin, txtSenha, txtNome, txtEmail;
    private LoginService _loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        txtEmail = findViewById(R.id.txtEmail);
        txtNome = findViewById(R.id.txtNome);

        _loginService = new LoginService(this);
    }

    public void Cadastrar(View view) {
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();
        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();

        if (!VerificarCampos(login, senha, nome, email))
            return;

        boolean existente = _loginService.VerificarLogin(login);
        if (existente) {
            ShowMensagem("Atenção", "Login já cadastrado!");
            return;
        }
        Usuario usuario = new Usuario(login, senha, nome, email);
        int id = 0;

        id = _loginService.Insert(usuario);


        if (id > 0) {
            Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();
            GoPrincipal(view);
        } else {
            ShowMensagem("Atenção", "Ocorreu um erro ao criar o usuário");
        }
    }

    private boolean VerificarCampos(String login, String senha, String nome, String email) {
        View focusView = null;
        boolean valido = true;

        if (TextUtils.isEmpty(login)) {
            txtLogin.setError("Campo obrigatório");
            focusView = txtLogin;
            valido = false;
        }
        if (TextUtils.isEmpty(senha)) {
            txtSenha.setError("Campo obrigatório");
            focusView = txtSenha;
            valido = false;
        }
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Campo obrigatório");
            focusView = txtEmail;
            valido = false;
        }
        if (TextUtils.isEmpty(nome)) {
            txtNome.setError("Campo obrigatório");
            focusView = txtNome;
            valido = false;
        }
        if (!valido)
            focusView.requestFocus();
        return valido;
    }

    public void GoPrincipal(View view) {
        Intent intent = new Intent(this, PrinciaplActivity.class);
        startActivity(intent);
    }

    private void ShowMensagem(String titulo, String mensagem) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show();
    }
}
