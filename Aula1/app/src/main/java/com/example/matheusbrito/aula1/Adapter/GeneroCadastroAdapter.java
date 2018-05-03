package com.example.matheusbrito.aula1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Model.Genero;
import com.example.matheusbrito.aula1.Model.GeneroCadastro;
import com.example.matheusbrito.aula1.R;
import com.example.matheusbrito.aula1.Service.GeneroService;

import java.util.ArrayList;

/**
 * Created by Matheus Brito on 30/04/2018.
 */

public class GeneroCadastroAdapter extends BaseAdapter {
    private GeneroService _generoService;
    private Context context;
    private ArrayList<GeneroCadastro> list;

    public GeneroCadastroAdapter(Context context, ArrayList<GeneroCadastro> list) {
        this.context = context;
        _generoService = new GeneroService(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).Id;
    }

    public boolean isChecked(int position) {
        return list.get(position).checked;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View rowView = view;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_genero_cadastro_layout, null);

            viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.rowCheckBox);
            viewHolder.text = (TextView) rowView.findViewById(R.id.rowTextView);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.checkBox.setChecked(list.get(i).checked);
        viewHolder.text.setText(list.get(i).Nome);

        final String itemStr = list.get(i).Nome;
        viewHolder.text.setText(list.get(i).Nome);

        viewHolder.checkBox.setTag(i);

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean newState = !list.get(i).isChecked();
                list.get(i).checked = newState;
            }
        });

        viewHolder.checkBox.setChecked(isChecked(i));

        return rowView;
    }

    static class ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView text;
    }
}

