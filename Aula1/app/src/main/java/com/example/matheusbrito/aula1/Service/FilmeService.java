package com.example.matheusbrito.aula1.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matheusbrito.aula1.InfraStructure.DbHelper;
import com.example.matheusbrito.aula1.Model.Filme;
import com.example.matheusbrito.aula1.Model.Genero;

import java.util.ArrayList;

/**
 * Created by Matheus Brito on 29/04/2018.
 */

public class FilmeService {
    private Context context;

    public FilmeService(Context context) {
        this.context = context;
    }

    public int Create(Filme filme) {
        ContentValues values = new ContentValues();
        values.put("Titulo", filme.Titulo);
        values.put("Imagem", filme.Imagem);
        values.put("Nota", filme.Nota);
        values.put("Ano", filme.Ano);

        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        long idLong = sqlDB.insert("Filme", null, values);
        sqlDB.close();
        db.close();

        //Insere os generos
        if (idLong > 0) {
            CreateGenero(filme.Generos, Integer.parseInt(String.valueOf(idLong)));
        }
        return Integer.parseInt(String.valueOf(idLong));
    }

    public void Update(Filme filme) {
        ContentValues values = new ContentValues();
        values.put("Titulo", filme.Titulo);
        values.put("Imagem", filme.Imagem);
        values.put("Nota", filme.Nota);
        values.put("Ano", filme.Ano);

        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.update("Filme", values, "Id = ?", new String[]{String.valueOf(filme.Id)});
        sqlDB.close();
        db.close();

        UpdateGenero(filme.Generos, filme.Id);
    }

    public ArrayList<Filme> GetAll() {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Titulo, Imagem, Ano, Nota FROM Filme", null);
        Filme filme;
        ArrayList<Filme> lstFilme = new ArrayList<>();
        if (resultado.moveToFirst()) {
            do {
                filme = new Filme();
                filme.Id = resultado.getInt(0);
                filme.Titulo = resultado.getString(1);
                filme.Imagem = resultado.getString(2);
                filme.Ano = resultado.getInt(3);
                filme.Nota = resultado.getFloat(4);
                filme.Generos = GetGenero(filme.Id);
                lstFilme.add(filme);
            } while (resultado.moveToNext());
        }
        sqlDB.close();
        db.close();
        return lstFilme;
    }

    public Filme GetById(int id) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Titulo, Imagem, Ano, Nota FROM Filme WHERE Id = " + id, null);
        resultado.moveToNext();

        Filme filme = new Filme();
        filme.Id = resultado.getInt(0);
        filme.Titulo = resultado.getString(1);
        filme.Imagem = resultado.getString(2);
        filme.Ano = resultado.getInt(3);
        filme.Nota = resultado.getFloat(4);
        filme.Generos = GetGenero(id);

        sqlDB.close();
        db.close();
        return filme;
    }

    public Object GetAdapter(int index) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT Id, Titulo, Imagem, Ano, Nota FROM Filme", null);
        resultado.moveToPosition(index);

        Filme filme;
        filme = new Filme();
        filme.Id = resultado.getInt(0);
        filme.Titulo = resultado.getString(1);
        filme.Imagem = resultado.getString(2);
        filme.Ano = resultado.getInt(3);
        filme.Nota = resultado.getFloat(4);
        filme.Generos = GetGenero(filme.Id);

        sqlDB.close();
        db.close();
        return filme;
    }

    public ArrayList<Genero> GetGenero(int filmeId) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT g.Id, g.Nome FROM FilmeGenero fg JOIN Genero g ON g.Id = fg.GeneroId AND FilmeId = " + filmeId, null);

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

    public void CreateGenero(ArrayList<Genero> lstGenero, int filmeId) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        ContentValues values;

        for (Genero genero : lstGenero) {
            values = new ContentValues();
            values.put("FilmeId", filmeId);
            values.put("GeneroId", genero.Id);
            sqlDB.insert("FilmeGenero", null, values);
        }

        sqlDB.close();
        db.close();
    }

    public void UpdateGenero(ArrayList<Genero> lstGenero, int filmeId) {
        DeleteGenero(filmeId);
        CreateGenero(lstGenero, filmeId);
    }

    public void DeleteGenero(int filmeId) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete("FilmeGenero", "FilmeId = ?", new String[]{String.valueOf(filmeId)});
        sqlDB.close();
        db.close();
    }

    public void Delete(int id) {
        DeleteGenero(id);
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        sqlDB.delete("Filme", "Id = ?", new String[]{String.valueOf(id)});
        sqlDB.close();
        db.close();
    }
}
