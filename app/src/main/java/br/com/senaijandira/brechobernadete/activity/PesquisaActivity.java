package br.com.senaijandira.brechobernadete.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.adapter.RoupasAdapter;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.model.Roupas;

public class PesquisaActivity extends Activity {

//    DECLARANDO ATRIBUTOS, VARIAVEIS, ELEMENTOS...
    ListView list_view_roupas_pesquisa;
    TextView txt_pesquisa;
    RoupasDAO dao;
    Roupas roupa;
    RoupasAdapter adapter;
    ArrayList<Roupas> listaRoupas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);
//        getActionBar().setDisplayHomeAsUpEnabled(true);


//        FIND'S DOS ELEMENTOS
        list_view_roupas_pesquisa = findViewById(R.id.list_view_roupas_filtro);
        txt_pesquisa = findViewById(R.id.txt_pesquisar);

//        INSTANCIA DAO E ROUPA
        dao = RoupasDAO.getInstance();
        roupa = new Roupas();
        roupa.setId(0);

//        CRIA O ADAPTER, SETA-O NA LISTA E SETA O CLICK DO ITEM DA LISTA
        adapter = new RoupasAdapter(this, new ArrayList<Roupas>());
        list_view_roupas_pesquisa.setAdapter(adapter);
        list_view_roupas_pesquisa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                roupa = adapter.getItem(position);
                Intent intencao = new Intent(getApplicationContext(), VisualizarActivity.class);
                intencao.putExtra("id", roupa.getId());
                startActivity(intencao);
            }
        });

//        MÉTODOS QUE AO DIGITAR ALGO NO CAMPO, FAZEM ALGO
//        NESTE CASO ELE FILTRA UMA LISTA A CADA LETRA DIGITADA NO CAMPO
        txt_pesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Toast.makeText(PesquisaActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FiltrarLista(s.toString().toLowerCase());
//                Toast.makeText(PesquisaActivity.this, s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {
//                Toast.makeText(PesquisaActivity.this, s, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //CHAMA O METODO QUE LISTA TODAS AS ROUPAS DO USUARIO
        listaRoupas = dao.selecionarTodas(this);

//        LIMPA O ADAPTER E ADICIONA A LISTA
        adapter.clear();
        adapter.addAll(listaRoupas);
    }

//    MÉTODO QUE FAZ O FILTRO DA LISTA
    public void FiltrarLista(String s){
        ArrayList<Roupas> listaFiltrada = new ArrayList<>();
        for (int i = 0; i < listaRoupas.size(); i++){
            if (listaRoupas.get(i).getNome().toLowerCase().contains(s)){
                listaFiltrada.add(listaRoupas.get(i));
            }
        }
//        LIMPA O ADAPTER E ADICIONA A LISTA
        adapter.clear();
        adapter.addAll(listaFiltrada);
    }
}
