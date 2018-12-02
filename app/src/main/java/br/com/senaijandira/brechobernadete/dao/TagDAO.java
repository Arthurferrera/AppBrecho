package br.com.senaijandira.brechobernadete.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.model.DbHelper;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;
import br.com.senaijandira.brechobernadete.model.Tag;

public class TagDAO {

//    DECLARANDO VARIAVEIS
    private static TagDAO instance;
    private SharedPreferencesConfig preferencesConfig;
    private int idCliente;
    private String tipoCliente;



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

        preferencesConfig = new SharedPreferencesConfig(context);

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();

        String sql;
        if (tipoCliente.equals("F")) {
            sql = "SELECT t._id, t.nome " +
                    "FROM tag t " +
                    "INNER JOIN tag_roupa tr " +
                    "ON tr._idtag = t._id " +
                    "INNER JOIN roupa r " +
                    "ON r._id = tr._idRoupa " +
                    "ORDER BY t.nome ASC";// +
//                "WHERE r._idClienteF = "+idCliente;
        } else {
            sql = "SELECT t._id, t.nome " +
                    "FROM tag t " +
                    "INNER JOIN tag_roupa tr " +
                    "ON tr._idtag = t._id " +
                    "INNER JOIN roupa r " +
                    "ON r._id = tr._idRoupa " +
                    "ORDER BY t.nome ASC";// +
//                "WHERE r._idClienteJ = "+idCliente;
        }

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Tag tg = new Tag();
            tg.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            tg.setNomeTag(cursor.getString(cursor.getColumnIndex("nome")));
            retorno.add(tg);
        }
        cursor.close();
        db.close();
        return retorno;
    }

//    MÉTODO QUE INSERE AS TAGS NO BANCO
    public Long inserirTag(Context context, String tag) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", tag);

        Long idTag = db.insert("tag", null, valores);

        return idTag;
    }

//    MÉTODO QUE INSERE O ID DA ROUPA E DA TAG
//    NA TABELA QUE RELACIONA OS DOIS
    public Long inserirTagRoupa(Context context, Long idTag, Long idRoupa){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("_idTag", idTag);
        valores.put("_idRoupa", idRoupa);

        Long idTagRoupa = db.insert("tag_roupa", null, valores);

        return idTagRoupa;
    }

//    MÉTODO QUE VERIFICA SE A TAG JA EXISTE
    public int verificarTag(Context context, String tag){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        String sql = "SELECT * FROM tag WHERE nome = '"+tag+"'";

        Cursor cursor = db.rawQuery(sql, null);

        int idTag = 0;
        if (cursor.moveToFirst()){
            idTag = cursor.getInt(cursor.getColumnIndex("_id"));
        }

        return idTag;
    }
}
