package com.example.matheusbrito.aula1.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matheusbrito.aula1.InfraStructure.DbHelper;
import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Model.Genero;
import com.example.matheusbrito.aula1.Model.GeneroCadastro;

import java.util.ArrayList;

/**
 * Created by Matheus Brito on 30/04/2018.
 */

public class GeneroService {
    private Context context;

    public GeneroService(Context context) {
        this.context = context;
    }

    public ArrayList<Genero> GetAll() {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Nome FROM Genero", null);
        Genero genero;
        ArrayList<Genero> lstGenero = new ArrayList<>();
        if (resultado.moveToFirst()) {
            do {
                genero = new Genero();
                genero.Id = resultado.getInt(0);
                genero.Nome = resultado.getString(1);
                lstGenero.add(genero);
            } while (resultado.moveToNext());
        }
        sqlDB.close();
        db.close();
        return lstGenero;
    }

    public ArrayList<GeneroCadastro> GetAllCadastro() {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Nome FROM Genero", null);
        GeneroCadastro genero;
        ArrayList<GeneroCadastro> lstGenero = new ArrayList<>();
        if (resultado.moveToFirst()) {
            do {
                genero = new GeneroCadastro();
                genero.Id = resultado.getInt(0);
                genero.Nome = resultado.getString(1);
                lstGenero.add(genero);
            } while (resultado.moveToNext());
        }
        sqlDB.close();
        db.close();
        return lstGenero;
    }

    public Object GetAdapter(int index) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Nome FROM Genero", null);
        resultado.moveToPosition(index);

        Genero genero;
        genero = new Genero();
        genero.Id = resultado.getInt(0);
        genero.Nome = resultado.getString(1);

        sqlDB.close();
        db.close();
        return genero;
    }

    public Genero GetByNome(String nome) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Nome FROM Genero WHERE Nome = '" + nome + "'", null);
        Genero genero;
        genero = new Genero();
        if (resultado.moveToFirst()) {
            genero.Id = resultado.getInt(0);
            genero.Nome = resultado.getString(1);
            return genero;
        }else{
            genero.Id = Create(nome);
            genero.Nome = nome;
        }

        sqlDB.close();
        db.close();
        return genero;
    }

    private int Create(String nome) {
        ContentValues values = new ContentValues();
        values.put("Nome", nome);

        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        long idLong = sqlDB.insert("Genero", null, values);
        sqlDB.close();
        db.close();
        return Integer.parseInt(String.valueOf(idLong));
    }
}
