package br.com.senaijandira.brechobernadete;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {

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

        dao = RoupasDAO.getInstance();
        Roupas r = new Roupas();
        r.setId(0);;

        listView_favoritos = favoritosView.findViewById(R.id.list_view_favoritos);

        adapter = new RoupasAdapter(getContext(), new ArrayList<Roupas>());
        listView_favoritos.setAdapter(adapter);
        listView_favoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Roupas roupa =  adapter.getItem(position);
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

        adapter.clear();
        ArrayList<Roupas> listaRoupas = new ArrayList<Roupas>();
        listaRoupas = dao.selecionarFavoritos(getContext());
        adapter.addAll(listaRoupas);
    }


}
