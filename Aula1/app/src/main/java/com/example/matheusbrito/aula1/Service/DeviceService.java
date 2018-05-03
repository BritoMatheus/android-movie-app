package com.example.matheusbrito.aula1.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Matheus Brito on 01/05/2018.
 */

public class DeviceService {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DeviceService(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPref", 0);
        editor = sharedPreferences.edit();
    }

    public boolean IsLoged() {
        if (sharedPreferences.getString("user_name", null) == null)
            return false;
        else
            return true;
    }

    public void SetUsuarioLogado(String nome) {
        editor.putString("user_name", nome);
        editor.commit();
    }

    public void Logout(){
        editor.clear();
        editor.commit();
    }
}
