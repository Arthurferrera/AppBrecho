package br.com.senaijandira.brechobernadete.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.adapter.ViewPagerAdapter;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.model.Roupas;

public class VisualizarActivity extends AppCompatActivity {

//    declarando as variaveis, elementos...
    //TODO: IMAGEM DA ROUPA
    TextView lbl_titulo, lbl_descricao, lbl_categoria, lbl_tamanho, lbl_tags, lbl_status, lbl_marca, lbl_classificacao, lbl_cor;
    RoupasDAO dao;
    Roupas r = new Roupas();
    ArrayList<String> tag = new ArrayList<>();
    int id;
//    ViewPager viewPager;
    private  AlertDialog alerta;
    LinearLayout linear_loja;
    ImageView img_roupa;
    private ArrayList<String> imagens = new ArrayList<>();


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
//        criando a toolbar
        final Toolbar toolbar = (Toolbar) Objects.requireNonNull(this).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Detalhes da roupa");
        toolbar.setNavigationIcon(R.drawable.ic_voltar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
//        viewPager = findViewById(R.id.viewPager);
        linear_loja = findViewById(R.id.linear_loja);
        img_roupa = findViewById(R.id.img_slide);

//        chamando o método que traz todas as informações da roupa
        r = dao.selecionarUmaRoupa(this, id);
//        chamando o método que traz todas as TAGS da roupa
        tag = dao.selecionarTagsByIdRoupa(this, id);

//        busca todas as fotos de uma roupa
        imagens = dao.selecionarFotosByIdRoupa(this, id);
        String foto = dao.buscarUmaFoto(this, id);
        StringBuilder imgs = new StringBuilder();
        for (String img : imagens) {
            imgs.append(img);
            imgs.append(" - ");
        }
        Log.d("listaImagens", String.valueOf(imgs));

//        esconde o linear que indica que a roupa foi comprada na loja
        int idSite = r.getIdSite();
        if (idSite == 0){
            linear_loja.setVisibility(View.GONE);
        }

//        setando os valores dos elementos com as informações vindas do banco
        lbl_titulo.setText(r.getNome());
        lbl_descricao.setText(r.getDescricao());
        lbl_categoria.setText(r.getCategoria());
        lbl_tamanho.setText(r.getTamanho());
        lbl_cor.setBackgroundTintList(ColorStateList.valueOf(r.getCor()));
        lbl_tags.setText(r.getTag());
        lbl_status.setText(r.getStatus());
        lbl_marca.setText(r.getMarca());
        lbl_classificacao.setText(r.getClassificacao());
        Bitmap imgBit = BitmapFactory.decodeFile(foto);
        img_roupa.setImageBitmap(imgBit);

//        Toast.makeText(this, r.getIdSite()+"", Toast.LENGTH_SHORT).show();
//        busca todas as tags de uma roupa
        StringBuilder TAGS = new StringBuilder();
        for(String tags : tag){
            TAGS.append(tags);
            TAGS.append(" - ");
        }
        lbl_tags.setText(TAGS.toString().trim());

//        adapter do view pager, elemento que mostra as imagens das roupas
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, id);
//        viewPager.setAdapter(viewPagerAdapter);
    }

//    cria o menu que fica na toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.visualizar, menu);
        return true;
    }

//    identifica qual botao foi seleciocnado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_editar:
                Intent intencao = new Intent(getApplicationContext(), MainActivity.class);
                intencao.putExtra("id", id);
                startActivity(intencao);
                finish();
//                FragmentManager fragmentManager = this.getSupportFragmentManager();
//                CadastroRoupaFragment fragment = new CadastroRoupaFragment();
//                Bundle bundle = new Bundle();
//
//                //Passando o id para cadastro para efetuar a edição
//                bundle.putInt("id", id);
//                fragment.setArguments(bundle);
//                fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).commit();
                break;
            case R.id.action_excluir:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Excluir");
                builder.setMessage("Deseja realmente excluir esta roupa?");
                builder.setNegativeButton("Não", null);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Boolean sucesso = dao.excluirRoupa(VisualizarActivity.this, id);
                       if (sucesso){
                           finish();
                       } else {
                           AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                           builder.setTitle("Erro!");
                           builder.setIcon(R.drawable.ic_report);
                           builder.setMessage("Ocorreu un erro ao tentar excluir uma roupa. Tente novamente mais tarde");
                           builder.setPositiveButton("Sim", null);
                           alerta = builder.create();
                           alerta.show();
                       }

                    }
                });
                alerta = builder.create();
                alerta.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
