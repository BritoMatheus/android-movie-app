package com.example.matheusbrito.aula1;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheusbrito.aula1.Adapter.GeneroCadastroAdapter;
import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Model.Genero;
import com.example.matheusbrito.aula1.Model.GeneroCadastro;
import com.example.matheusbrito.aula1.Service.FilmeService;
import com.example.matheusbrito.aula1.Service.GeneroService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class FilmeCadastroActivity extends AppCompatActivity {
    private EditText txtTitulo, txtImagem, txtNota, txtAno;
    private ListView listViewGenero;

    private FilmeService _filmeService;
    private GeneroService _generoService;
    private GeneroCadastroAdapter myItemsListAdapter;

    private long id;
    private ArrayList<GeneroCadastro> lstGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_cadastro);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtImagem = findViewById(R.id.txtImagem);
        txtNota = findViewById(R.id.txtNota);
        txtAno = findViewById(R.id.txtAno);

        _filmeService = new FilmeService(this);
        _generoService = new GeneroService(this);
        id = getIntent().getLongExtra("id", 0);

        lstGenero = _generoService.GetAllCadastro();
        if (id > 0) {
            Filme filme = _filmeService.GetById(Integer.parseInt(String.valueOf(id)));
            txtTitulo.setText(filme.Titulo);
            txtImagem.setText(filme.Imagem);
            txtNota.setText(String.valueOf(filme.Nota));
            txtAno.setText(String.valueOf(filme.Ano));

            for (GeneroCadastro generoCadastro : lstGenero)
                for (Genero genero : filme.Generos)
                    if (generoCadastro.Id == genero.Id)
                        generoCadastro.checked = true;
        }

        myItemsListAdapter = new GeneroCadastroAdapter(this, lstGenero);
        listViewGenero = (ListView) findViewById(R.id.listViewGenero);
        listViewGenero.setAdapter(myItemsListAdapter);

        listViewGenero.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(FilmeCadastroActivity.this,
                        ((GeneroCadastro) (parent.getItemAtPosition(position))).ItemString,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Salvar(View view) {
        Filme filme = new Filme();
        filme.Generos = new ArrayList<>();

        for (int i = 0; i < lstGenero.size(); i++)
            if (lstGenero.get(i).isChecked())
                filme.Generos.add(new Genero(lstGenero.get(i).Id, lstGenero.get(i).Nome));

        if (!VerificarCampos(txtTitulo.getText().toString(),
                txtImagem.getText().toString(),
                txtNota.getText().toString(),
                txtAno.getText().toString(),
                filme.Generos.size()))
            return;

        filme = new Filme(txtTitulo.getText().toString(),
                txtImagem.getText().toString(),
                txtNota.getText().toString(),
                txtAno.getText().toString(),
                filme.Generos);

        if (id > 0) {
            filme.Id = Integer.parseInt(String.valueOf(id));
            _filmeService.Update(filme);
            Toast.makeText(this, "Filme atualizado com sucesso!", Toast.LENGTH_LONG).show();
            GoHome(view);
        } else {
            int id = _filmeService.Create(filme);
            if (id > 0) {
                Toast.makeText(this, "Filme criado com sucesso!", Toast.LENGTH_LONG).show();
                GoHome(view);
            } else {
                ShowMensagem("Atenção", "Ocorreu um erro ao criar o filme");
            }
        }

    }

    private boolean VerificarCampos(String titulo, String imagem, String nota, String ano, int qtdGenero) {
        View focusView = null;
        boolean valido = true;

        if (qtdGenero == 0) {
            Toast.makeText(this, "Obrigatório selecionar um gênero!", Toast.LENGTH_LONG).show();
            valido = false;
        }

        if (TextUtils.isEmpty(ano)) {
            txtAno.setError("Campo obrigatório");
            focusView = txtAno;
            focusView.requestFocus();
            valido = false;
        }
        if (!TextUtils.isEmpty(ano)) {
            try {
                int i = Integer.parseInt(ano);
            } catch (Exception ex) {
                txtAno.setError("Campo inválido");
                focusView = txtAno;
                focusView.requestFocus();
                valido = false;
            }
        }
        if (TextUtils.isEmpty(nota)) {
            txtNota.setError("Campo obrigatório");
            focusView = txtNota;
            focusView.requestFocus();
            valido = false;
        }
        if (!TextUtils.isEmpty(nota)) {
            try {
                float n = Float.parseFloat(nota);
            } catch (Exception ex) {
                txtNota.setError("Campo inválido");
                focusView = txtNota;
                focusView.requestFocus();
                valido = false;
            }
        }
        if (TextUtils.isEmpty(imagem)) {
            txtImagem.setError("Campo obrigatório");
            focusView = txtImagem;
            focusView.requestFocus();
            valido = false;
        }
        if (TextUtils.isEmpty(titulo)) {
            txtTitulo.setError("Campo obrigatório");
            focusView = txtTitulo;
            focusView.requestFocus();
            valido = false;
        }

        return valido;
    }

    public void GoHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
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
