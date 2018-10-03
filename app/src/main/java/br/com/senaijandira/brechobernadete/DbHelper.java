package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

//    SETANDO NOME E VERSÃO DO BANCO
    private static String DB_NAME = "brecho.db";
    private static int DB_VERSION = 1;

//    CONSTRUTOR
    public DbHelper (Context ctx){super(ctx, DB_NAME, null,DB_VERSION);}

//    CRIANDO AS TABELAS DO BANCO
    @Override
    public void onCreate(SQLiteDatabase db) {

//        tabela categoria
        db.execSQL("CREATE TABLE categoria(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL);");

//        tabela status
        db.execSQL("CREATE TABLE status (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL);");

//        tabela tags
        db.execSQL("CREATE TABLE tag (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL);");

//        tabela de relacionamento de roupas com tags
        db.execSQL("CREATE TABLE tag_roupa (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "_idTag INTEGER NOT NULL," +
                "_idRoupa INTEGER," +
                "CONSTRAINT fk_tag_roupa" +
                "   FOREIGN KEY (_idTag)" +
                "   REFERENCES tag(_id)," +
                "CONSTRAINT fk_roupa_tag" +
                "   FOREIGN KEY (_idRoupa)" +
                "   REFERENCES roupa(_id));");

//        tabela imagens
        db.execSQL("CREATE TABLE imagem (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "caminho TEXT NOT NULL," +
                "_idRoupa INTEGER," +
                "CONSTRAINT fk_imagem_roupa" +
                "   FOREIGN KEY (_idRoupa)" +
                "   REFERENCES roupa(_id));");

//        tabela roupa
        db.execSQL("CREATE TABLE roupa (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descricao TEXT NOT NULL," +
                "cor TEXT NOT NULL," +
                "tamanho TEXT NOT NULL," +
                "maeca TEXT NOT NULL," +
                "classificacao TEXT NOT NULL," +
                "_idStatus INTEGER NOT NULL," +
                "_idCategoria INTEGER NOT NULL," +
                "CONSTRAINT fk_status_roupa" +
                "   FOREIGN KEY (_idStatus)" +
                "   REFERENCES status(_id)," +
                "CONSTRAINT fk_categoria_roupa" +
                "   FOREIGN KEY (_idCategoria)" +
                "   REFERENCES categoria(_id));");


//        INSERINDO OS DADOS QUE JÁ VÃO SER PADRÕES
//        INSERTS TABELA CATEGORIA
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Camisetas');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Blusas');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Calças');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Bermudas');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Calçados');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Social');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Vestidos');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Acessórios');");
        db.execSQL("INSERT INTO categoria (nome) VALUES ('Roupas íntimas');");

//        INSERTS TABELA STATUS
        db.execSQL("INSERT INTO status (nome) VALUES ('No Guarda-roupas');");
        db.execSQL("INSERT INTO status (nome) VALUES ('Lavando');");
        db.execSQL("INSERT INTO status (nome) VALUES ('Emprestado');");


    }

//    MÉTODO DE ATUALIZAÇÃO DO BANCO
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE categoria;");
        db.execSQL("DROP TABLE status;");
        db.execSQL("DROP TABLE tag;");
        db.execSQL("DROP TABLE tag_roupa;");
        db.execSQL("DROP TABLE imagem;");
        db.execSQL("DROP TABLE roupa;");
        onCreate(db);
    }
}
