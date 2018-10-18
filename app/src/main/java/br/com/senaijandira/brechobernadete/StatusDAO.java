package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class StatusDAO {

    private static StatusDAO instance;

//    método que pega a instância da classe
//    caso não exista, ele cria uma nova
    public static StatusDAO getInstance(){
        if (instance == null){
            instance = new StatusDAO();
        }
        return instance;
    }

//    método seleciona todas as categorias
    public ArrayList<Status> selecioanrTodos(Context context) {
        ArrayList<Status> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM status;";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Status s = new Status();
            s.setId(cursor.getInt(0));
            s.setNome(cursor.getString(1));
            retorno.add(s);
        }
        return retorno;
    }


}
