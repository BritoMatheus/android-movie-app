package com.example.matheusbrito.aula1.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by Matheus Brito on 30/04/2018.
 */

public class GeneroCadastro extends Genero {
    public boolean checked;
    public Drawable ItemDrawable;
    public String ItemString;

    public GeneroCadastro(){

    }

    GeneroCadastro(Drawable drawable, String t, boolean b){
        ItemDrawable = drawable;
        ItemString = t;
        checked = b;
    }

    public boolean isChecked(){
        return checked;
    }
}
