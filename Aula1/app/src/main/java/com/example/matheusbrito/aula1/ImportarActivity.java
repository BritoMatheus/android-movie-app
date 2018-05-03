package com.example.matheusbrito.aula1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Model.Genero;
import com.example.matheusbrito.aula1.Model.Movie;
import com.example.matheusbrito.aula1.Service.FilmeService;
import com.example.matheusbrito.aula1.Service.GeneroService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ImportarActivity extends AppCompatActivity {
    private String url = "https://api.androidhive.info/json/movies.json";
    private FilmeService _filmeService;
    private GeneroService _generoService;
    private TextView txtErro, txtSucesso, txtCarregando;
    private ProgressBar pgrImportar;
    private Button btnImportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar);

        txtErro = findViewById(R.id.txtErro);
        txtSucesso = findViewById(R.id.txtSucesso);
        txtCarregando = findViewById(R.id.txtCarregando);
        pgrImportar = findViewById(R.id.pgrImportar);
        btnImportar = findViewById(R.id.btnImportar);

        _filmeService = new FilmeService(this);
        _generoService = new GeneroService(this);
    }

    public void Importar(View view) {
        txtCarregando.setText("Iniciando...!");
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {
                txtCarregando.setText("");
                Log.d("MEUAPP", response.toString());
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<ArrayList<Movie>>() {
                }.getType();
                ArrayList<Movie> lstMovies = gson.fromJson(response.toString(), listType);
                if(lstMovies != null)
                    ProcessarImportados(lstMovies);
                else
                    txtErro.setText("Nenhum registro encontrado!");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable
                    throwable, JSONArray errorResponse) {
                txtCarregando.setText("");
                txtErro.setText("Ocorreu um erro : " + statusCode + errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String
                    responseString, Throwable throwable) {
                txtCarregando.setText("");
                txtErro.setText("Ocorreu um erro : " + statusCode + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable
                    throwable, JSONObject errorResponse) {
                txtCarregando.setText("");
                txtErro.setText("Ocorreu um erro : " + statusCode + errorResponse.toString());
            }
        });
    }

    private void ProcessarImportados(ArrayList<Movie> lstMovies) {
        Filme filme;
        Genero genero;
        int count = 0;
        pgrImportar.setMax(lstMovies.size());
        for (Movie movie : lstMovies) {
            filme = new Filme(movie.title, movie.image, movie.rating, movie.releaseYear);
            for (String stringGenero: movie.genre) {
                genero = _generoService.GetByNome(stringGenero);
                filme.Generos.add(genero);
            }
            _filmeService.Create(filme);
            pgrImportar.setProgress(count);
            count++;
        }
        pgrImportar.setProgress(count);
        txtSucesso.setText("Importado com sucesso!");
    }
}
