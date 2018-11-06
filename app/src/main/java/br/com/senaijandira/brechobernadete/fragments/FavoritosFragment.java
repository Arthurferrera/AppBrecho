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
import br.com.senaijandira.brechobernadete.model.Roupas;
import br.com.senaijandira.brechobernadete.adapter.RoupasAdapter;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.activity.VisualizarActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {

//    declarando as variaveis, elementos...
    ListView listView_favoritos;
    RoupasAdapter adapter;
    RoupasDAO dao;

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View favoritosView = inflater.inflate(R.layout.fragment_favoritos, container, false);

//        intancia das classes necessárias
        dao = RoupasDAO.getInstance();
        Roupas r = new Roupas();
        r.setId(0);;

//        find's dos elementos
        listView_favoritos = favoritosView.findViewById(R.id.list_view_favoritos);

//        criando o adapter, setando na lista e setando click do item da lista
        adapter = new RoupasAdapter(getContext(), new ArrayList<Roupas>());
        listView_favoritos.setAdapter(adapter);
        listView_favoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                criando um objeto roupa e pegando a posição
                Roupas roupa =  adapter.getItem(position);
//                chamando a tela requerida
                Intent intencao = new Intent(getContext(), VisualizarActivity.class);
                intencao.putExtra("id", roupa.getId());
                startActivity(intencao);
            }
        });
        return favoritosView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        limpando o adapter, para não uplicar os registros
        adapter.clear();
//        criando a lista de roupa
        ArrayList<Roupas> listaRoupas = new ArrayList<Roupas>();
//        chamando o método que retorna as roupas requerids, e guarda na lista
        listaRoupas = dao.selecionarFavoritos(getContext());
//        adicionando a lista ao adapter
        adapter.addAll(listaRoupas);
    }
}
