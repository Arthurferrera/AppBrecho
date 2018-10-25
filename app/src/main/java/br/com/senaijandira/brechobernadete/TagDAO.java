package br.com.senaijandira.brechobernadete;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TagDAO {

    private static TagDAO instance;

//    método que pega a instância da classe
//    caso não exista, ele cria uma nova
    public static TagDAO getInstance() {
        if (instance == null){
            instance = new TagDAO();
        }
        return instance;
    }

//    método que retorna todas as tags
    public ArrayList<Tag> selecionatTodas(Context context){
        ArrayList<Tag> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tag;";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Tag tg = new Tag();
            tg.setId(cursor.getInt(0));
            tg.setNomeTag(cursor.getString(1));
            retorno.add(tg);
        }
        cursor.close();
        db.close();
        return retorno;
    }

    public Long inserirTag(Context context, String tag) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", tag);

//        TODO: GRAVAR A ROUPA S/ FOTO E COR E TAG

        Long idTag = db.insert("tag", null, valores);

        if (idTag != -1){
            return idTag;
        } else {
            return idTag;
        }
    }
}
