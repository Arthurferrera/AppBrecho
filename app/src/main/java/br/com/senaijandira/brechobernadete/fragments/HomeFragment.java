package br.com.senaijandira.brechobernadete.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.model.Categoria;
import br.com.senaijandira.brechobernadete.adapter.CategoriaAdapter;
import br.com.senaijandira.brechobernadete.dao.CategoriaDAO;
import br.com.senaijandira.brechobernadete.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

//    declarando as variaveis, elementos...
    ListView list_view_categoria;
    CategoriaDAO dao;
    CategoriaAdapter adapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View viewHome =  inflater.inflate(R.layout.fragment_home, container, false);

//        instancia das classes necessárias
        dao = CategoriaDAO.getInstance();
        Categoria cat = new Categoria();
        cat.setId(0);

//        finds dos elementos
        list_view_categoria = viewHome.findViewById(R.id.list_categorias_home);

//        criando o adapter, setando na lista e setando o click de item da lista
        adapter = new CategoriaAdapter(getContext(),dao, new ArrayList<Categoria>());
        list_view_categoria.setAdapter(adapter);
        list_view_categoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                cria o FragmentManager
                FragmentManager fm = getActivity().getSupportFragmentManager();
//                cria uma categoria pegando sua posição
                Categoria categoria = adapter.getItem(position);
//                cria o fragment
//                chama outro fragment passando parametros
                Fragment fragment = new RoupasFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", categoria.getId());
                bundle.putString("modo", "categoria");
                fragment.setArguments(bundle);
//                substitui o fragment atual pelo solicitado
                fm.beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(null).commit();
                getActivity().setTitle(categoria.getNome());
            }
        });
        return viewHome;
    }

    @Override
    public void onResume() {
        super.onResume();
//        criando um array
        ArrayList<Categoria> categorias;
//        chamando o metodo de selecionar todas as categorias
        categorias = dao.selecioanrTodos(getContext());
//        limpando o adapter
        adapter.clear();
//        adicionando a lista ao adapter
        adapter.addAll(categorias);

        getActivity().setTitle("Guarda-roupas");
    }
}
