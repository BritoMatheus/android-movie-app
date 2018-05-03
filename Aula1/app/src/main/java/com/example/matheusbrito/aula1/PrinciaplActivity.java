package com.example.matheusbrito.aula1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matheusbrito.aula1.Service.DeviceService;
import com.example.matheusbrito.aula1.Service.LoginService;

public class PrinciaplActivity extends AppCompatActivity {
    private EditText txtLogin, txtSenha;
    private LoginService _loginService;
    private DeviceService _deviceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_princiapl);

        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        _loginService = new LoginService(this);
        _deviceService = new DeviceService(this);
        if (_deviceService.IsLoged())
            GoHome();
    }

    public void Logar(View view) {
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();

        if (!VerificarCampos(login, senha))
            return;

        boolean autenticado = _loginService.Logar(login, senha);
        if (autenticado) {
            Toast.makeText(this, "Logado!", Toast.LENGTH_LONG).show();
            _deviceService.SetUsuarioLogado(login);
            GoHome();
        } else {
            ShowMensagem("Atenção", "Usuário e Senha inválidos");
        }
    }

    private boolean VerificarCampos(String login, String senha) {
        View focusView = null;
        boolean valido = true;

        if (TextUtils.isEmpty(login)) {
            txtLogin.setError("Campo obrigatório");
            focusView = txtLogin;
            focusView.requestFocus();
            valido = false;
        }

        if (TextUtils.isEmpty(senha)) {
            txtSenha.setError("Campo obrigatório");
            valido = false;
        }
        return valido;
    }

    public void GoCadastro(View view) {
        Intent intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
    }

    public void GoHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
