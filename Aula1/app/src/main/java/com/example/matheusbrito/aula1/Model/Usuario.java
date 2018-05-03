package com.example.matheusbrito.aula1.Model;

/**
 * Created by Matheus Brito on 23/04/2018.
 */

public class Usuario {
    public int Id;
    public String Login;
    public String Senha;
    public String Nome;
    public String Email;

    public Usuario() {
    }

    public Usuario(String login, String senha, String nome, String email) {
        Login = login;
        Senha = senha;
        Nome = nome;
        Email = email;
    }
}
