package com.example.matheusbrito.aula1.InfraStructure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matheus Brito on 23/04/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String Base = "appTrabalho.db";
    private static final int Versao = 1;

    public DbHelper(Context context) {
        super(context, Base, null, Versao);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUsuario = "CREATE TABLE Usuario " +
                "(" +
                "     Id integer primary key autoincrement," +
                "       Login varchar(10)," +
                "       Nome varchar(100)," +
                "       Senha varchar(25)," +
                "       Email varchar(100)" +
                ")";
        String createTableGenero = "CREATE TABLE Genero " +
                "(" +
                "       Id integer primary key autoincrement," +
                "       Nome varchar(30)" +
                ")";
        String createTableFilme = "CREATE TABLE Filme " +
                "(" +
                "       Id integer primary key autoincrement," +
                "       Titulo varchar(300)," +
                "       Imagem varchar(300)," +
                "       Nota decimal," +
                "       Ano int" +
                ")";
        String createTableFilmeGenero = "CREATE TABLE FilmeGenero " +
                "(" +
                "       Id integer primary key autoincrement," +
                "       FilmeId int NOT NULL," +
                "       GeneroId int NOT NULL," +
                "       FOREIGN KEY(FilmeId) REFERENCES Filme(Id)," +
                "       FOREIGN KEY(GeneroId) REFERENCES Genero(Id)" +
                ")";

        sqLiteDatabase.execSQL(createTableUsuario);
        sqLiteDatabase.execSQL(createTableGenero);
        sqLiteDatabase.execSQL(createTableFilme);
        sqLiteDatabase.execSQL(createTableFilmeGenero);

        sqLiteDatabase.execSQL("INSERT INTO Genero (Nome) VALUES ('Action'), ('Adventure'), ('Comedy'), ('Crime'), ('Drama'), ('Family'), ('Fantasy'), ('History'), ('Horror'), ('Sci-Fi'), ('Thriller'), ('Outro')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
