package br.com.senaijandira.brechobernadete.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.model.DbHelper;
import br.com.senaijandira.brechobernadete.model.Roupas;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;
import br.com.senaijandira.brechobernadete.model.Tag;

public class RoupasDAO {

    private static RoupasDAO instance;
    private String tipoCliente;
    private int idCliente;
    SharedPreferencesConfig preferencesConfig;


//    método que pega a instância da classe
//    caso não exista, ele cria uma nova
    public static RoupasDAO getInstance(){
        if (instance == null){
            instance = new RoupasDAO();
        }
        return instance;
    }

//    MÉTODO QUE RETORNA TODAS AS ROUPAS
    public ArrayList<Roupas> selecionarTodas(Context context){
        ArrayList<Roupas> retorno = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        preferencesConfig = new SharedPreferencesConfig(context);
        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();

        String sql;
        if (tipoCliente.equals("F")){
            sql = "SELECT *, r.nome AS roupa " +
                    "FROM roupa r " +
                    "INNER JOIN status s " +
                    "ON s._id = r._idStatus " +
                    "WHERE r._idClienteF = "+idCliente;
        } else {
            sql = "SELECT *, r.nome AS roupa " +
                    "FROM roupa r " +
                    "INNER JOIN status s " +
                    "ON s._id = r._idStatus " +
                    "WHERE r._idClienteJ = "+idCliente;
        }



        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Roupas r = new Roupas();
            r.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            r.setNome(cursor.getString(cursor.getColumnIndex("roupa")));
            r.setStatus(cursor.getString(cursor.getColumnIndex("nome")));
            r.setFavorito(cursor.getInt(cursor.getColumnIndex("favorito")));
            r.setCor(cursor.getInt(cursor.getColumnIndex("cor")));
            retorno.add(r);
        }
        cursor.close();
        db.close();
        return retorno;
    }

//    MÉTODO QUE RETORNA AS ROUPAS PELA CATEGORIA
    public ArrayList<Roupas> selecionarPorCategoria(Context context, int id){
        ArrayList<Roupas> retorno = new ArrayList<>();

        preferencesConfig = new SharedPreferencesConfig(context);
        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql;
        if (tipoCliente.equals("F")){
            sql = "SELECT *, r.nome AS roupa " +
                    "FROM roupa r " +
                    "INNER JOIN categoria c " +
                    "ON r._idCategoria = c._id " +
                    "INNER JOIN status s " +
                    "ON s._id = r._idStatus " +
                    "WHERE c._id = "+id+" " +
                    "AND r._idClienteF = "+idCliente;
        } else {
            sql = "SELECT *, r.nome AS roupa " +
                    "FROM roupa r " +
                    "INNER JOIN categoria c " +
                    "ON r._idCategoria = c._id " +
                    "INNER JOIN status s " +
                    "ON s._id = r._idStatus " +
                    "WHERE c._id = "+id+" " +
                    "AND r._idClienteJ = "+idCliente;
        }



        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Roupas r = new Roupas();
            r.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            r.setNome(cursor.getString(cursor.getColumnIndex("roupa")));
            r.setStatus(cursor.getString(cursor.getColumnIndex("nome")));
            r.setFavorito(cursor.getInt(cursor.getColumnIndex("favorito")));
            r.setCor(cursor.getInt(cursor.getColumnIndex("cor")));
            retorno.add(r);
        }
        cursor.close();
        db.close();
        return retorno;
    }

//    MÉTODO QUE RETORNA AS ROUPAS FAVORITAS
    public ArrayList<Roupas> selecionarFavoritos(Context context){
        ArrayList<Roupas> retorno = new ArrayList<>();

        preferencesConfig = new SharedPreferencesConfig(context);
        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql;
        if (tipoCliente.equals("F")){
            sql = "SELECT *, r._id AS idRoupa, r.nome AS roupa, s.nome AS status FROM roupa r " +
                    "INNER JOIN status s " +
                    "ON s._id = r._idStatus " +
                    "WHERE r.favorito = 1 " +
                    "AND r._idClienteF = "+idCliente;
        } else {
            sql = "SELECT *, r._id AS idRoupa, r.nome AS roupa, s.nome AS status FROM roupa r " +
                    "INNER JOIN status s " +
                    "ON s._id = r._idStatus " +
                    "WHERE r.favorito = 1 " +
                    "AND r._idClienteJ = "+idCliente;
        }


        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Roupas r = new Roupas();
            r.setId(cursor.getInt(cursor.getColumnIndex("idRoupa")));
            r.setNome(cursor.getString(cursor.getColumnIndex("roupa")));
            r.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            r.setFavorito(cursor.getInt(cursor.getColumnIndex("favorito")));
            r.setCor(cursor.getInt(cursor.getColumnIndex("cor")));
            retorno.add(r);
        }
        cursor.close();
        db.close();
        return retorno;
    }

//    MÉTODOS QUE RETORNA A ROUPA POR TAG
    public ArrayList<Roupas> selecionatPorTag(Context context, int id){
        ArrayList<Roupas> retorno = new ArrayList<>();

        preferencesConfig = new SharedPreferencesConfig(context);
        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql;
        if (tipoCliente.equals("F")) {
            sql = "SELECT *, r._id AS idRoupa, r.nome AS roupa, s.nome AS status " +
                    "FROM roupa r " +
                    "INNER JOIN tag_roupa tr " +
                    "ON r._id = tr._idRoupa " +
                    "INNER JOIN tag t " +
                    "ON t._id = tr._idTag " +
                    "INNER JOIN status s " +
                    "ON r._idStatus = s._id " +
                    "WHERE t._id = " + id + " " +
                    "AND r._idClienteF = "+idCliente;
        } else {
            sql = "SELECT *, r._id AS idRoupa, r.nome AS roupa, s.nome AS status " +
                    "FROM roupa r " +
                    "INNER JOIN tag_roupa tr " +
                    "ON r._id = tr._idRoupa " +
                    "INNER JOIN tag t " +
                    "ON t._id = tr._idTag " +
                    "INNER JOIN status s " +
                    "ON r._idStatus = s._id " +
                    "WHERE t._id = " + id + " " +
                    "AND r._idClienteJ = "+idCliente;
        }

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Roupas r = new Roupas();
            r.setId(cursor.getInt(cursor.getColumnIndex("idRoupa")));
            r.setNome(cursor.getString(cursor.getColumnIndex("roupa")));
            r.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            r.setCor(cursor.getInt(cursor.getColumnIndex("cor")));
            r.setFavorito(cursor.getInt(cursor.getColumnIndex("favorito")));
            retorno.add(r);
        }
        cursor.close();
        db.close();
        return retorno;
    }

//    MÉTODO QUE TRAZ TUDO SOBRE UMA ROUPA ESPECIFICA
    public Roupas selecionarUmaRoupa(Context context, int id){
        Roupas roupa = new Roupas();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT *, r._id AS idRoupa, r.nome AS roupa, r._idStatus AS idStatus, " +
                "r._idCategoria AS idCategoria, s.nome AS status, c.nome AS categoria " +
                "FROM roupa r " +
                "INNER JOIN status s " +
                "ON s._id = r._idStatus " +
                "INNER JOIN categoria c " +
                "ON c._id = r._idCategoria " +
                "WHERE r._id = "+id;

//        String sql = "SELECT * FROM roupa r " +
//                "INNER JOIN status s " +
//                "ON s._id = r._idStatus " +
//                "INNER JOIN tag_roupa tr " +
//                "ON tr._idRoupa = r._id " +
//                "INNER JOIN tag t " +
//                "ON t._id = tr._idTag " +
//                "INNER JOIN categoria c " +
//                "ON c._id = r._idCategoria " +
//                "WHERE r._id = "+id;

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            roupa.setId(cursor.getInt(cursor.getColumnIndex("idRoupa")));
            roupa.setNome(cursor.getString(cursor.getColumnIndex("roupa")));
            roupa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            roupa.setCor(cursor.getInt(cursor.getColumnIndex("cor")));
            roupa.setTamanho(cursor.getString(cursor.getColumnIndex("tamanho")));
            roupa.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
            roupa.setClassificacao(cursor.getString(cursor.getColumnIndex("classificacao")));
            roupa.setFavorito(cursor.getInt(cursor.getColumnIndex("favorito")));
            roupa.setIdStatus(cursor.getInt(cursor.getColumnIndex("idStatus")));
            roupa.setIdCategoria(cursor.getInt(cursor.getColumnIndex("idCategoria")));
            roupa.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            roupa.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
        }
        cursor.close();
        db.close();
        Log.d("roupaInfo", roupa.getId()+"");
        return roupa;
    }

    public Long cadastrarRoupa(Context context, Roupas r, int idCliente, String tipoCliente) {

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", r.getNome());
        valores.put("descricao", r.getDescricao());
        valores.put("cor", r.getCor());
        valores.put("tamanho", r.getTamanho());
        valores.put("marca", r.getMarca());
        valores.put("classificacao", r.getClassificacao());
        valores.put("favorito", true);
        valores.put("_idStatus", r.getIdStatus());
        valores.put("_idCategoria", r.getIdCategoria());
        if (tipoCliente == "F"){
            valores.put("_idClienteF", idCliente);
        } else {
            valores.put("_idClienteJ", idCliente);
        }

        Long id = db.insert("roupa", null, valores);

        if (id != -1){
            return id;
        } else {
            return id;
        }
    }

    public Boolean excluirRoupa(Context context, Integer id){

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        db.delete("imagem", "_idRoupa=?", new String[]{id.toString()});
        db.delete("tag_roupa", "_idRoupa=?", new String[]{id.toString()});
        db.delete("roupa", "_id=?", new String[]{id.toString()});
        return true;
    }

    public Long cadastrarFotos(Context context, Long idRoupa, String pathImagem){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("caminho", pathImagem);
        valores.put("_idRoupa", idRoupa);

        Long id = db.insert("imagem", null, valores);

        return id;
    }

    public ArrayList<String> selecionarFotosByIdRoupa(Context context, int idRoupa){
        ArrayList<String> listaImagens = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM imagem WHERE _idRoupa = "+idRoupa;
        Cursor cursor = db.rawQuery(sql, null);

        int cont = 0;
        while(cursor.moveToNext()){
            listaImagens.add(cursor.getString(cursor.getColumnIndex("caminho")));
        }

        cursor.close();
        db.close();

        return listaImagens;
    }

    public ArrayList<String> selecionarTagsByIdRoupa(Context context, int idRoupa){
        ArrayList<String> listaTags = new ArrayList<>();

        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "SELECT * FROM tag t " +
                "INNER JOIN tag_roupa tr " +
                "ON tr._idTag = t._id " +
                "WHERE tr._idRoupa = "+idRoupa;
        Cursor cursor = db.rawQuery(sql, null);

        Tag tag = new Tag();

        while(cursor.moveToNext()){
//            tag.setNomeTag();
            listaTags.add(cursor.getString(cursor.getColumnIndex("nome")));
        }

        cursor.close();
        db.close();

        return listaTags;
    }

    public Boolean VerificarMinhaCompra(Context context, int idSite){

        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        String sql = "SELECT _idSite FROM roupa WHERE _idSite = "+idSite;

        Cursor cursor  = db.rawQuery(sql, null);

        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public Boolean editarRoupa(Context context, Roupas roupa){
        return true;
    }

    public Boolean atualizarFavorito(Context context, int idRoupa, int favoritoAtual){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        String sql;
        if (favoritoAtual == 1){
            sql = "UPDATE roupa SET favorito = 0 WHERE _id = "+idRoupa;
        } else {
            sql = "UPDATE roupa SET favorito = 1 WHERE _id = "+idRoupa;
        }

        Cursor cursor  = db.rawQuery(sql, null);

        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }
}