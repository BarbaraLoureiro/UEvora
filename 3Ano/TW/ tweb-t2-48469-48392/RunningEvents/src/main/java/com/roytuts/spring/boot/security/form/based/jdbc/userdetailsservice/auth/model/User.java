package com.roytuts.spring.boot.security.form.based.jdbc.userdetailsservice.auth.model;

public class User {

    private String user_name;
    private String user_pass;
    private String nome;
    private String genero;
    private String escalao;
    private String role;

    public User() {
    }

    public User(String user_name, String user_pass, String nome, String genero, String escalao, String role) {
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.nome = nome;
        this.genero = genero;
        this.escalao = escalao;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User:[" + user_name + ", " + user_pass + ", " + nome + ", " + genero + ", " + escalao + " " + role + "]";
    }

    public String getUsername() {
        return user_name;
    }
   
    public void setUsername(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return user_pass;
    }

    public void setPassword(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEscalao() {
        return escalao;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
