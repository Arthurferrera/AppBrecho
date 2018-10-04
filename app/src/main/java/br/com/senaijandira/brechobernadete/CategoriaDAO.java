package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CategoriaDAO {

    private static CategoriaDAO instance;

    public static CategoriaDAO getInstance(){
        if (instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

    public ArrayList<Categoria> selecioanrTodos(Context context) {
        ArrayList<Categoria> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM categoria;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            retorno.add(c);
        }
        return retorno;
    }


    public int quantidadePecasPorIdCategoria(Context context, int id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT COUNT(_idCategoria) FROM roupa WHERE _idCategoria = " +id+" GROUP BY _idCategoria ;" ;

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToNext()){

            int r = cursor.getInt(0);
            cursor.close();

           return r;

        }

        return 0;
    }

    public ArrayList<Categoria> quantidadePecasPorCategoria(Context context){
        ArrayList<Categoria> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT COUNT(_idCategoria) FROM roupa GROUP BY _idCategoria;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Categoria c = new Categoria();
            c.setTotalPecas(cursor.getInt(0));
            retorno.add(c);
        }
        return retorno;
    }
}
