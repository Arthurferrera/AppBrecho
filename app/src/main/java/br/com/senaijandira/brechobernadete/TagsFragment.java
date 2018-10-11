package br.com.senaijandira.brechobernadete;


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
public class TagsFragment extends Fragment {

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

        dao = TagDAO.getInstance();
        Tag tag = new Tag();
        tag.setId(0);

        listView_tag = tagView.findViewById(R.id.list_view_tags);

        adapter = new TagAdapter(getContext(), new ArrayList<Tag>());
        listView_tag.setAdapter(adapter);
        listView_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemTagClick();
            }
        });

        return tagView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Tag> tags;
        tags = dao.selecionatTodas(getContext());
        adapter.clear();
        adapter.addAll(tags);
    }

    public void ItemTagClick(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new RoupasFragment()).commit();
        getActivity().setTitle("Roupas");
    }
}
