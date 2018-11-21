package br.com.senaijandira.brechobernadete.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.model.Roupas;
import br.com.senaijandira.brechobernadete.adapter.RoupasAdapter;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.activity.VisualizarActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoupasFragment extends Fragment {

//    declarando as variaveis, elementos...
    ListView listView_roupas;
    RoupasAdapter adapter;
    RoupasDAO dao;
    String modo;
    int id;


    public RoupasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View roupaView = inflater.inflate(R.layout.fragment_roupas, container, false);

//        PEGANDO OS ARGUMENTOS PASSADOS PELO BUNDLE
        Bundle bundle = this.getArguments();
        if (bundle != null){
            id = bundle.getInt("id");
            modo = bundle.getString("modo");
        }

//        intancia das classes necessárias
        dao = RoupasDAO.getInstance();
        Roupas r = new Roupas();
        r.setId(0);

//        find's dos elementos
        listView_roupas = roupaView.findViewById(R.id.list_view_roupas);

//        criando o adapter, setando na lista e setando click do item da lista
        adapter = new RoupasAdapter(getContext(), new ArrayList<Roupas>());
        listView_roupas.setAdapter(adapter);
        listView_roupas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Roupas roupa =  adapter.getItem(position);
                Intent intencao = new Intent(getContext(), VisualizarActivity.class);
                intencao.putExtra("id", roupa.getId());
                startActivity(intencao);
            }
        });
        return roupaView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        CRIANDO A LISTA DE ROUPA QUE SERÁ EXIBIDA
        ArrayList<Roupas> listaRoupas = new ArrayList<Roupas>();
//        VERIFICANDO QUAL O MODO FOI REQUISITADO
//        PARA PODER CHAMAR O MÉTODO CERTO
        if (modo.equals("categoria")){
            listaRoupas = dao.selecionarPorCategoria(getContext(), id);
        } else if (modo.equals("tag")){
            listaRoupas = dao.selecionatPorTag(getContext(), id);
        } else if (modo.equals("favoritos")){
            listaRoupas = dao.selecionarFavoritos(getContext());
        }
//        LIMPANDO O ADAPTER PARA NÃO DUPLICAR
        adapter.clear();
//        ADICIONANDO A LISTA AO ADAPTER

       adapter.addAll(listaRoupas);
    }


}
