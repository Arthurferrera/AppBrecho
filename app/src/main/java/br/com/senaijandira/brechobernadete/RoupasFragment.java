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
public class RoupasFragment extends Fragment {


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

        Bundle bundle = this.getArguments();
        if (bundle != null){
            id = bundle.getInt("id");
            modo = bundle.getString("modo");
        }


        dao = RoupasDAO.getInstance();
        Roupas r = new Roupas();
        r.setId(0);

        listView_roupas = roupaView.findViewById(R.id.list_view_roupas);

        adapter = new RoupasAdapter(getContext(), new ArrayList<Roupas>());
        listView_roupas.setAdapter(adapter);
        listView_roupas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),"funcionou", Toast.LENGTH_LONG);
                RoupaClick();
            }
        });


        return roupaView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Roupas> listaRoupas = new ArrayList<Roupas>();
        if (modo.equals("categoria")){
            listaRoupas = dao.selecionarPorCategoria(getContext(), id);
        } else if (modo.equals("tag")){
            listaRoupas = dao.selecionatPorTag(getContext(), id);
        }
        adapter.clear();
        adapter.addAll(listaRoupas);
    }

    public void RoupaClick(){
        startActivity(new Intent(getContext(), VisualizarActivity.class));
    }

}
