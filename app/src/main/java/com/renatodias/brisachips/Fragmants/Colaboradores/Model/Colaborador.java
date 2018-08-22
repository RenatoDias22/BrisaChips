package com.renatodias.brisachips.Fragmants.Colaboradores.Model;

public class Colaborador {

    private long id;
    private String nome;
    private int fotoPerfil;
    private byte[] foto;
    private String estoque;
    private String quantidade;

    public Colaborador(Colaborador colaborador) {
        this.id = colaborador.id;
        this.nome = colaborador.nome;
        this.fotoPerfil = colaborador.fotoPerfil;
        this.foto = colaborador.getFoto();
        this.estoque = colaborador.estoque;
        this.quantidade = colaborador.quantidade;
    }

    public Colaborador(long id, String nome, int fotoPerfil, String estoque, String quantidade) {
        this.id = id;
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.estoque = estoque;
        this.quantidade = quantidade;
    }

    public Colaborador() {

    }

    public long getId(){
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFotoPerfil(){
        return this.fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }


    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getQuantidade() {
        return quantidade;
    }
}
