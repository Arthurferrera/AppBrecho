package br.com.senaijandira.brechobernadete;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoupasFragment extends Fragment {


    ListView listView_roupas;
    RoupasAdapter adapter;
    String API_URL;


    public RoupasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View roupaView = inflater.inflate(R.layout.fragment_roupas, container, false);



        return inflater.inflate(R.layout.fragment_roupas, container, false);
    }

}
