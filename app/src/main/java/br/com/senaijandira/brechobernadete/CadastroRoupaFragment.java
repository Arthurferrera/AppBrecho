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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroRoupaFragment extends Fragment {

//    declarando as variaveis, elementos...
    FloatingActionButton fb;
    Button btn_salvar_roupa;
    Spinner sp_status;
    Spinner sp_categoria;
    TagDAO daoTag;
    CategoriaDAO daoCategoria;
    StatusDAO daoStatus;


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
        sp_categoria = view.findViewById(R.id.sp_categoria);

//        ArrayList<Tag> status = daoTag.selecionatTodas(getContext());
//        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, status);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        sp_status.setAdapter(adapter);
//        android.R.layout.simple_dropdown_item_1line

        //puxar status do banco
        daoStatus = StatusDAO.getInstance();
        List<Status> lstStatus = daoStatus.selecioanrTodos(getContext());

        ArrayAdapter<Status> adapterStatus = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstStatus );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapterStatus);


//        ação de clique do botão
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chama método que abre o dialog da cor
               abrirCor();
            }
        });


        //puxar status do banco
        daoCategoria = CategoriaDAO.getInstance();
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        categoriaList = daoCategoria.selecioanrTodos(getContext());

        ArrayAdapter<Categoria> categoriaArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriaList );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_categoria.setAdapter(categoriaArrayAdapter);
        categoriaArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return view;
    }




//    método que abre o dialog da cor
    public void abrirCor(){
        final AmbilWarnaDialog colorDialog = new AmbilWarnaDialog(getActivity(), Color.WHITE, new AmbilWarnaDialog.OnAmbilWarnaListener() {
//            cancelando o dialog
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {}

//            ação do botão ok do dialog
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
