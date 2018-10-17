package br.com.senaijandira.brechobernadete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class VisualizarActivity extends AppCompatActivity {

//    declarando as variaveis, elementos...
    //TODO: IMAGEM DA ROUPA
    TextView lbl_titulo, lbl_descricao, lbl_categoria, lbl_tamanho, lbl_tags, lbl_status, lbl_marca, lbl_classificacao, lbl_cor;
    //TODO: cor da roupa
    RoupasDAO dao;
    Roupas r = new Roupas();
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

//        pegando os parametros passado pelo intent na chamada da tela
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);

//        pegando a instancia da classe dao
        dao = RoupasDAO.getInstance();

//        find's dos elementos
        lbl_titulo = findViewById(R.id.lbl_titulo_visualizar);
        lbl_descricao = findViewById(R.id.lbl_descricao_visualizar);
        lbl_categoria = findViewById(R.id.lbl_categoria_visualizar);
        lbl_tamanho = findViewById(R.id.lbl_tamanho_visualizar);
        lbl_tags = findViewById(R.id.lbl_tag_visualizar);
        lbl_status = findViewById(R.id.lbl_status_visualizar);
        lbl_marca = findViewById(R.id.lbl_marca_visualizar);
        lbl_classificacao = findViewById(R.id.lbl_classificacao_visualizar);
        lbl_cor = findViewById(R.id.lbl_cor_visualizar);

//        chamando o método que traz todas as informações da roupa
        r = dao.selecionarUmaRoupa(this, id);

//        setando os valores dos elementos com as informações vindas do banco
        lbl_titulo.setText(r.getNome());
        lbl_descricao.setText(r.getDescricao());
        lbl_categoria.setText(r.getCategoria());
        lbl_tamanho.setText(r.getTamanho());
        lbl_cor.setBackgroundColor(r.getCor());
        lbl_tags.setText(r.getTag());
        lbl_status.setText(r.getStatus());
        lbl_marca.setText(r.getMarca());
        lbl_classificacao.setText(r.getClassificacao());
    }

}
