package br.com.senaijandira.brechobernadete.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.model.Categoria;
import br.com.senaijandira.brechobernadete.model.DbHelper;

public class CategoriaDAO {

    private static CategoriaDAO instance;

//    método que pega a instância da classe
//    caso não exista, ele cria uma nova
    public static CategoriaDAO getInstance(){
        if (instance == null){
            instance = new CategoriaDAO();
        }
        return instance;
    }

//    método seleciona todas as categorias
    public ArrayList<Categoria> selecioanrTodos(Context context) {
        ArrayList<Categoria> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT c._id, c.nome, COUNT(r._idCategoria) " +
                "FROM categoria c " +
                "LEFT JOIN roupa r " +
                "ON c._id = r._idCategoria " +
                "GROUP BY c._id;";

        Cursor cursor = db.rawQuery(sql, null);

//        ArrayList<Integer> listaQuantidades = new ArrayList<>();
//        listaQuantidades = quantidadePecasPorCategoria(context);

        int cont = 0;
        while (cursor.moveToNext()) {
            Categoria c = new Categoria();
            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setTotalPecas(cursor.getInt(2));
            retorno.add(c);
        }
        cursor.close();
        db.close();
        return retorno;
    }

//    método que retorna a quantidade de peças que cada categoria possui
//    public ArrayList<Integer> quantidadePecasPorCategoria(Context context){
//        ArrayList<Integer> retorno = new ArrayList<>();
//eException: no such table: produto (code 1): , while compiling: SELECT c._id, c.nomeCategoria , COUNT(p._idCategoria) FROM categoria c LEFT JOIN produto p ON c._id = p._idCategoria GROUP BY c._id;
//                      at android.database.sqlite.SQLiteConnection.nativePrepareStatement(Na
//        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
//
//        String sql = "SELECT COUNT(_idCategoria) FROM roupa GROUP BY _idCategoria;";
//
//        Cursor cursor = db.rawQuery(sql, null);
//
//        while (cursor.moveToNext()){
////            Categoria c = new Categoria();
////            c.setTotalPecas(cursor.getInt(0));
//            retorno.add(cursor.getInt(0));
//        }
//        cursor.close();
//        db.close();
//        return retorno;
//    }
}
