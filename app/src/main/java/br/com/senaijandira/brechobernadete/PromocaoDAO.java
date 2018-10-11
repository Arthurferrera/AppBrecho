package br.com.senaijandira.brechobernadete;

import android.content.Context;

import java.util.ArrayList;

public class PromocaoDAO {

    private static PromocaoDAO instanac;

    public static PromocaoDAO getInstanac() {
        if (instanac == null){
            instanac = new PromocaoDAO();
        }
        return instanac;
    }

    public ArrayList<Promocao> selecionarTodas(Context context) {
        ArrayList<Promocao> retorno = new ArrayList<>();

        return retorno;
    }



}
