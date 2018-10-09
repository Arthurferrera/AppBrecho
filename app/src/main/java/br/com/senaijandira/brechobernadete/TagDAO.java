package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TagDAO {

    private static TagDAO instance;

    public static TagDAO getInstance() {
        if (instance == null){
            instance = new TagDAO();
        }
        return instance;
    }

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
        return retorno;
    }
}
