package br.com.senaijandira.brechobernadete;

import android.content.Context;

import java.util.ArrayList;

public class PromocaoDAO {

    private static PromocaoDAO instanac;

    //    método que pega a instância da classe
//    caso não exista, ele cria uma nova
    public static PromocaoDAO getInstanac() {
        if (instanac == null){
            instanac = new PromocaoDAO();
        }
        return instanac;
    }

//    método que seleciona todas as promoções
    public ArrayList<Promocao> selecionarTodas(Context context) {
        ArrayList<Promocao> retorno = new ArrayList<>();

        return retorno;
    }



}
