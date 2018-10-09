package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RoupasDAO {

    private static RoupasDAO instance;

    public static RoupasDAO getInstance(){
        if (instance == null){
            instance = new RoupasDAO();
        }

        return instance;
    }

    public ArrayList<Roupas> selecionarTodos(Context context){
        ArrayList<Categoria> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM produto"
    }

}

//Terminar a RoupasDAO