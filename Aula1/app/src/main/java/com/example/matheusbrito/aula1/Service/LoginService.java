package com.example.matheusbrito.aula1.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.matheusbrito.aula1.InfraStructure.DbHelper;
import com.example.matheusbrito.aula1.Model.Usuario;

/**
 * Created by Matheus Brito on 23/04/2018.
 */

public class LoginService {
    private Context context;

    public LoginService(Context context) {
        this.context = context;
    }

    public boolean Logar(String login, String senha) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT COUNT(*) FROM Usuario WHERE Login = '" + login + "' AND Senha = '" + senha + "'", null);
        resultado.moveToNext();

        int count = resultado.getInt(0);
        return count > 0 ? true : false;
    }

    public int Insert(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("Login", usuario.Login);
        values.put("Senha", usuario.Senha);
        values.put("Nome", usuario.Nome);
        values.put("Email", usuario.Email);

        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        long idLong = sqlDB.insert("Usuario", null, values);
        sqlDB.close();
        db.close();
        return Integer.parseInt(String.valueOf(idLong));
    }

    public boolean VerificarLogin(String login) {
        DbHelper db = new DbHelper(context);
        SQLiteDatabase sqlDB = db.getReadableDatabase();
        Cursor resultado = sqlDB.rawQuery("SELECT COUNT(*) FROM Usuario WHERE Login = '" + login + "'", null);
        resultado.moveToNext();

        int count = resultado.getInt(0);
        return count > 0 ? true : false;
    }
}
