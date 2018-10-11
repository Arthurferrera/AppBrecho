package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.database.Cursor;
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
        ArrayList<Roupas> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM roupa";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Roupas r = new Roupas();
            r.setId(cursor.getInt(0));
            r.setNome(cursor.getString(1));
            r.setIdStatus(cursor.getInt(2));
            retorno.add(r);
        }
        return retorno;
    }

    public ArrayList<Roupas> selecionarFavoritos(Context context){
        ArrayList<Roupas> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM roupa WHERE favorito = 1";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Roupas r = new Roupas();
            r.setId(cursor.getInt(0));
            r.setNome(cursor.getString(1));
            r.setIdStatus(cursor.getInt(2));
            retorno.add(r);
        }
        return retorno;
    }


}

//Terminar a RoupasDAO