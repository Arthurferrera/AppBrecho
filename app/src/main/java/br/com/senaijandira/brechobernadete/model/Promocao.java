package br.com.senaijandira.brechobernadete.model;

public class Promocao {

//    declarando os atributos da classe
    private int id;
    private Double precoNovo;
    private Double precoAntigo;
    private String nomeProduto;
    private String foto;
    private int idProduto;


//    Métodos getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrecoNovo() {
        return precoNovo;
    }

    public void setPrecoNovo(Double precoNovo) {
        this.precoNovo = precoNovo;
    }

    public Double getPrecoAntigo() {
        return precoAntigo;
    }

    public void setPrecoAntigo(Double precoAntigo) {
        this.precoAntigo = precoAntigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getFoto() { return foto; }

    public void setFoto(String foto) { this.foto = foto; }

    public int getIdProduto() { return idProduto; }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
}
