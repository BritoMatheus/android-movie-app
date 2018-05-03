package com.example.matheusbrito.aula1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Model.Genero;
import com.example.matheusbrito.aula1.R;
import com.example.matheusbrito.aula1.Service.FilmeService;
import com.squareup.picasso.Picasso;

/**
 * Created by Matheus Brito on 29/04/2018.
 */

public class FilmeAdapter extends BaseAdapter {
    private FilmeService _filmeService;
    private Context context;

    public FilmeAdapter(Context context) {
        this.context = context;
        _filmeService = new FilmeService(context);
    }

    @Override
    public int getCount() {
        return _filmeService.GetAll().size();
    }

    @Override
    public Object getItem(int i) {
        return _filmeService.GetAdapter(i);
    }

    @Override
    public long getItemId(int i) {
        return ((Filme) _filmeService.GetAdapter(i)).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_filme_layout, null);
        }

        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtNota = view.findViewById(R.id.txtNota);
        TextView txtAno = view.findViewById(R.id.txtAno);
        TextView txtGeneros = view.findViewById(R.id.txtGeneros);
        ImageView img = view.findViewById(R.id.img);

        Filme filme = (Filme) getItem(i);

        txtTitulo.setText(filme.Titulo);
        txtNota.setText(String.valueOf(filme.Nota));
        txtAno.setText(String.valueOf(filme.Ano));
        Picasso.get().load(filme.Imagem).into(img);
        String stringGenero = "";
        for (int j = 0; j < filme.Generos.size(); j++) {
            stringGenero += filme.Generos.get(j).Nome;
            stringGenero += (j + 1 == filme.Generos.size() ? "" : ", ");
        }
        txtGeneros.setText(stringGenero);

        return view;
    }
}
