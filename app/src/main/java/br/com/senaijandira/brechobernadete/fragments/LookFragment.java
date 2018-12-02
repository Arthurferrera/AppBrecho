package br.com.senaijandira.brechobernadete.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.activity.VisualizarActivity;
import br.com.senaijandira.brechobernadete.adapter.LookAdapter;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.model.Roupas;


/**
 * A simple {@link Fragment} subclass.
 */
public class LookFragment extends Fragment {

    ListView listView_look;
    RoupasDAO dao;
    LookAdapter adapter;

    public LookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lookView = inflater.inflate(R.layout.fragment_look, container, false);

        dao = RoupasDAO.getInstance();
        Roupas roupa = new Roupas();
        roupa.setId(0);

        listView_look = lookView.findViewById(R.id.list_view_look);

        adapter = new LookAdapter(getContext(), new ArrayList<Roupas>());
        listView_look.setAdapter(adapter);
        listView_look.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Roupas roupa =  adapter.getItem(position);
                Intent intencao = new Intent(getContext(), VisualizarActivity.class);
                intencao.putExtra("id", roupa.getId());
                startActivity(intencao);
            }
        });
        return lookView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        CRIANDO A LISTA DE ROUPA QUE SERÁ EXIBIDA
        ArrayList<Roupas> listaRoupas = new ArrayList<Roupas>();
//        VERIFICANDO QUAL O MODO FOI REQUISITADO
//        PARA PODER CHAMAR O MÉTODO CERTO
        listaRoupas = dao.selecionarLook(getContext());

//        LIMPANDO O ADAPTER PARA NÃO DUPLICAR
        adapter.clear();
//        ADICIONANDO A LISTA AO ADAPTER
        adapter.addAll(listaRoupas);
    }
}
