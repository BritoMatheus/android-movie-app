package com.example.matheusbrito.aula1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Service.FilmeService;
import com.squareup.picasso.Picasso;

public class FilmeVisualizarActivity extends AppCompatActivity {
    private TextView txtTitulo, txtImagem, txtNota, txtAno, txtGeneros;
    private ImageView img;
    private FilmeService _filmeService;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme_visualizar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtImagem = findViewById(R.id.txtImagem);
        txtNota = findViewById(R.id.txtNota);
        txtAno = findViewById(R.id.txtAno);
        txtGeneros = findViewById(R.id.txtGeneros);
        img = findViewById(R.id.img);

        id = getIntent().getLongExtra("id", 0);
        _filmeService = new FilmeService(this);
        Filme filme = _filmeService.GetById(Integer.parseInt(String.valueOf(id)));

        Picasso.get().load(filme.Imagem).into(img);
        txtTitulo.setText(filme.Titulo);
        txtNota.setText(String.valueOf(filme.Nota));
        Picasso.get().load(filme.Imagem).into(img);
        String stringGenero = "";
        for (int j = 0; j < filme.Generos.size(); j++) {
            stringGenero += filme.Generos.get(j).Nome;
            stringGenero += (j + 1 == filme.Generos.size() ? "" : ", ");
        }
        txtGeneros.setText(stringGenero);

        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndShowAlertDialog();
            }
        });

        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FilmeCadastroActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void createAndShowAlertDialog() {
        final long filmeId = this.id;
        final Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tem certeza que deseja excluir o registro?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                _filmeService.Delete(Integer.parseInt(String.valueOf(filmeId)));
                dialog.dismiss();
                Intent intent = new Intent(context, HomeActivity.class);
                // Closing all the Activities
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
