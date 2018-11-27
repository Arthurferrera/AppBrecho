package br.com.senaijandira.brechobernadete.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.model.Categoria;
import br.com.senaijandira.brechobernadete.model.DbHelper;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;

public class CategoriaDAO {

//    DECLRANDO VARIAVEIS, CLASSES...
    private static CategoriaDAO instance;
    private SharedPreferencesConfig preferencesConfig;
    private int idCliente;
    private String tipoCliente;

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

//        INSTANCIA DO SharedPreferencesConfig
        preferencesConfig = new SharedPreferencesConfig(context);

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();

        String sql;
        if (tipoCliente.equals("F")){
            sql = "SELECT c._id, c.nome, COUNT(r._idCategoria) AS totalPecas " +
                    "FROM categoria c " +
                    "LEFT JOIN roupa r " +
                    "ON c._id = r._idCategoria " +
//                    "AND r._idClienteF = "+idCliente+ " " +
                    "GROUP BY c._id;";
        } else {
            sql = "SELECT c._id, c.nome, COUNT(r._idCategoria) AS totalPecas " +
                    "FROM categoria c " +
                    "LEFT JOIN roupa r " +
                    "ON c._id = r._idCategoria " +
//                    "AND r._idClienteJ = "+idCliente + " " +
                    "GROUP BY c._id;";
        }

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Categoria c = new Categoria();
            c.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            c.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            c.setTotalPecas(cursor.getInt(cursor.getColumnIndex("totalPecas")));
            retorno.add(c);
        }
        cursor.close();
        db.close();
        return retorno;
    }
}
