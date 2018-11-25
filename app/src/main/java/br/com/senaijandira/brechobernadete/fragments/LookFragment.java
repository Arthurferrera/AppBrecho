package br.com.senaijandira.brechobernadete.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.senaijandira.brechobernadete.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LookFragment extends Fragment {

    public LookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lookView = inflater.inflate(R.layout.fragment_look, container, false);

        return lookView;
    }

}
