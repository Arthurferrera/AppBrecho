package br.com.senaijandira.brechobernadete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class VisualizarActivity extends AppCompatActivity {

    //TODO: IMAGEM DA ROUPA
    TextView lbl_titulo, lbl_descricao, lbl_categoria, lbl_tamanho, lbl_tags, lbl_status, lbl_marca, lbl_classificacao;
    //TODO: cor da roupa
    RoupasDAO dao;
    Roupas r = new Roupas();
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        Intent intent = getIntent();
        intent.getIntExtra("id", 0);

        dao = RoupasDAO.getInstance();
        r.setId(0);

        lbl_titulo = findViewById(R.id.lbl_titulo_visualizar);
        lbl_descricao = findViewById(R.id.lbl_descricao_visualizar);
        lbl_categoria = findViewById(R.id.lbl_categoria_visualizar);
        lbl_tamanho = findViewById(R.id.lbl_tamanho_visualizar);
        lbl_tags = findViewById(R.id.lbl_tag_visualizar);
        lbl_status = findViewById(R.id.lbl_status_visualizar);
        lbl_marca = findViewById(R.id.lbl_marca_visualizar);
        lbl_classificacao = findViewById(R.id.lbl_classificacao_visualizar);

        r = dao.selecionarUmaRoupa(this, id);

        lbl_titulo.setText(r.getNome().toString());
        lbl_descricao.setText(r.getDescricao().toString());
        lbl_categoria.setText(r.getIdCategoria());
        lbl_tamanho.setText(r.getTamanho().toString());
        //todo: TAGS
        lbl_tags.setText(r.getNome().toString());
        lbl_status.setText(r.getIdStatus());
        lbl_marca.setText(r.getMarca().toString());
        lbl_classificacao.setText(r.getClassificacao().toString());
    }

}
