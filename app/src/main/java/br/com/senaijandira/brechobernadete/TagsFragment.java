package br.com.senaijandira.brechobernadete;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagsFragment extends Fragment {

//    declarando as variaveis, elementos...
    ListView listView_tag;
    TagDAO dao;
    TagAdapter adapter;

    public TagsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View tagView = inflater.inflate(R.layout.fragment_tags, container, false);

//        instancia das classes necessárias
        dao = TagDAO.getInstance();
        Tag tag = new Tag();
        tag.setId(0);

//        find's dos elementos
        listView_tag = tagView.findViewById(R.id.list_view_tags);

//        criando o adapter, setando na lista e setando click do item da lista
        adapter = new TagAdapter(getContext(), new ArrayList<Tag>());
        listView_tag.setAdapter(adapter);
        listView_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                cria o FragmentManager
                FragmentManager fm = getActivity().getSupportFragmentManager();
//                cria uma tag pegando sua posição
                Tag tag = adapter.getItem(position);
//                cria o fragment
//                chama outro fragment passando parametros
                Fragment fragment = new RoupasFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", tag.getId());
                bundle.putString("modo", "tag");
                fragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(null).commit();
                getActivity().setTitle(tag.getNomeTag());
            }
        });
        return tagView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        limpando o adapter, para não uplicar os registros
        adapter.clear();
//        cria a lista de tags que armazena todas as tags
        ArrayList<Tag> tags;
//        chama o método que retorna todas as tags
        tags = dao.selecionatTodas(getContext());
//        adicionando a lista ao adapter
        adapter.addAll(tags);
        getActivity().setTitle("#tags");

    }


}
