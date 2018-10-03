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

        String sql = "SELECT c._id, c.nome, COUNT(*) " +
                "FROM categoria c" +
                "INNER JOIN roupa r" +
                "ON r._id = c._idCategoria group by c.nome;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTotalPecas(cursor.getInt(2));
            retorno.add(c);
        }
        return retorno;
    }

//    public ArrayList<Categoria> quantidadePecasPorCategoria(Context context){
//        ArrayList<Categoria> retorno = new ArrayList<>();
//
//        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
//
//        String sql = "SELECT COUNT(_idCategoria) roupa GROUP BY "
//    }
//}
