package com.example.matheusbrito.aula1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.matheusbrito.aula1.Adapter.FilmeAdapter;

public class HomeActivity extends AppCompatActivity {
    private FloatingActionButton btnImportar;
    private FloatingActionButton btnNew;
    private FilmeAdapter filmeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNew = (FloatingActionButton) findViewById(R.id.btnNew);
        btnImportar = (FloatingActionButton) findViewById(R.id.btnImportar);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoCadastrar();
            }
        });
        btnImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoImportar();
            }
        });

        ListView lvFilme = (ListView) findViewById(R.id.lvFilme);
        filmeAdapter = new FilmeAdapter(this);
        lvFilme.setAdapter(filmeAdapter);
        lvFilme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), FilmeVisualizarActivity.class);
                intent.putExtra("id", l);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        filmeAdapter.notifyDataSetChanged();
    }

    public void GoImportar() {
        Intent intent = new Intent(this, ImportarActivity.class);
        startActivity(intent);
    }

    public void GoCadastrar() {
        Intent intent = new Intent(this, FilmeCadastroActivity.class);
        startActivity(intent);
    }
}
