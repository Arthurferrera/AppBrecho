package br.com.senaijandira.brechobernadete.model;

public class Roupas {

//    DECLARANDO OS ATRIBUTOS DA CLASSES
    private int id;
    private String nome;
    private String descricao;
    private String cor;
    private String tamanho;
    private String marca;
    private String classificacao;
    private int idStatus;
    private int idCategoria;
    private Boolean favorito;
    private String categoria;
    private String status;
    private String tag;
    private int idSite;

//    MÉTODOS GETTERS E SETTERS
    public int getId() { return id;}

    public void setId(int id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao;}

    public String getCor() {return cor;}

    public void setCor(String cor) {this.cor = cor;}

    public String getTamanho() {return tamanho;}

    public void setTamanho(String tamanho) {this.tamanho = tamanho;}

    public String getMarca() {return marca;}

    public void setMarca(String marca) {this.marca = marca;}

    public String getClassificacao() {return classificacao;}

    public void setClassificacao(String classificacao) {this.classificacao = classificacao;}

    public int getIdStatus() {return idStatus;}

    public void setIdStatus(int idStatus) {this.idStatus = idStatus;}

    public int getIdCategoria() {return idCategoria;}

    public void setIdCategoria(int idCategoria) {this.idCategoria = idCategoria;}

    public Boolean getFavorito() { return favorito; }

    public void setFavorito(Boolean favorito) { this.favorito = favorito; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    @Override
    public String toString() {
        return this.tamanho;
    }

    public int getIdSite() { return idSite; }

    public void setIdSite(int idSite) { this.idSite = idSite; }
}