package com.example.matheusbrito.aula1.Model;

import java.util.ArrayList;

/**
 * Created by Matheus Brito on 29/04/2018.
 */

public class Filme {
    public int Id;
    public String Titulo;
    public String Imagem;
    public float Nota;
    public int Ano;
    public ArrayList<Genero> Generos;

    public Filme() {

    }

    public Filme(String titulo, String imagem, float nota, int ano) {
        Titulo = titulo;
        Imagem = imagem;
        Nota = nota;
        Ano = ano;
    }

    public Filme(String titulo, String imagem, String nota, String ano){
        Titulo = titulo;
        Imagem = imagem;
        Nota = Float.parseFloat(nota);
        Ano = Integer.parseInt(ano);
        Generos = new ArrayList<>();
    }

    public Filme(String titulo, String imagem, String nota, String ano, ArrayList<Genero> generos){
        Titulo = titulo;
        Imagem = imagem;
        Nota = Float.parseFloat(nota);
        Ano = Integer.parseInt(ano);
        Generos = generos;
    }
}
