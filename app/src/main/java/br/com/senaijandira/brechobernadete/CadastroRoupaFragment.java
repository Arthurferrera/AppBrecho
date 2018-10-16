package br.com.senaijandira.brechobernadete;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroRoupaFragment extends Fragment {

    FloatingActionButton fb;
    Button btn_salvar_roupa;
    Spinner sp_status;
    TagDAO daoTag;
    CategoriaDAO daoCategoria;


    public CadastroRoupaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_roupa, container, false);

//        find's dos elementos
        fb = view.findViewById(R.id.fb_cor);
        btn_salvar_roupa = view.findViewById(R.id.btn_salvar_roupa);
        sp_status = view.findViewById(R.id.sp_status);

        //a        ArrayList<Tag> status = daoTag.selecionatTodas(getContext());
        ////        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, status);
        ////        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ////        sp_status.setAdapter(adapter);ndroid.R.layout.simple_dropdown_item_1line
//


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirCor();
            }
        });

        return view;
    }

    public void abrirCor(){
        final AmbilWarnaDialog colorDialog = new AmbilWarnaDialog(getActivity(), Color.WHITE, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                fb.setBackgroundTintList(ColorStateList.valueOf(color));
                ColorStateList cor;
                cor = ColorStateList.valueOf(color);


                Log.d("onOk", cor+"");

//                Log.d("onOk", cor.toArgb()+"");
//                Toast.makeText(getActivity(), color, Toast.LENGTH_SHORT).show();
            }

        });
        colorDialog.show();
    }
}
