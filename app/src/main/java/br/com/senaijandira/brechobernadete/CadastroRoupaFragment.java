package br.com.senaijandira.brechobernadete;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    Spinner sp_status, sp_categoria, sp_tamanho;
    TextView txt_nome, txt_descricao, txt_marca;
    RadioButton rd_medida, rd_tamanho, rd_class_a, rd_class_b, rd_class_c;
    Button btn_salvar;
    TagDAO daoTag;
    RoupasDAO daoRoupa;
    CategoriaDAO daoCategoria;
    StatusDAO daoStatus;
    String API_URL, nome, descricao, tamanho, marca, classificacao;
    int idCategoria, idStatus;

    ArrayAdapter<Status> adapterStatus;
    ArrayAdapter<Categoria> adapterCategoria;

    public CadastroRoupaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_roupa, container, false);

//        find's dos elementos
        txt_nome = view.findViewById(R.id.txt_nome);
        txt_descricao = view.findViewById(R.id.txt_descricao);
        sp_categoria = view.findViewById(R.id.sp_categoria);
        rd_medida = view.findViewById(R.id.rd_medida);
        rd_tamanho = view.findViewById(R.id.rd_tamanho);
        sp_tamanho = view.findViewById(R.id.sp_tamanho);
        fb = view.findViewById(R.id.fb_cor);
        sp_status = view.findViewById(R.id.sp_status);
        btn_salvar_roupa = view.findViewById(R.id.btn_salvar_roupa);
        txt_marca = view.findViewById(R.id.txt_marca);
        rd_class_a = view.findViewById(R.id.rd_a);
        rd_class_b = view.findViewById(R.id.rd_b);
        rd_class_c = view.findViewById(R.id.rd_c);

//        ação de clique dos elementos
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chama método que abre o dialog da cor
                abrirCor();
            }
        });

        rd_medida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarTamanho(1);
            }
        });

        rd_tamanho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarTamanho(2);
            }
        });

        btn_salvar_roupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarRoupa();
            }
        });

        //puxar status do banco e carregando no spinner
        daoStatus = StatusDAO.getInstance();
        List<Status> lstStatus = daoStatus.selecioanrTodos(getContext());
        adapterStatus = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstStatus );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapterStatus);

        //puxar categoria do banco, e carregando as no spinner
        daoCategoria = CategoriaDAO.getInstance();
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        categoriaList = daoCategoria.selecioanrTodos(getContext());
        adapterCategoria = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriaList );
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_categoria.setAdapter(adapterCategoria);

        API_URL = getString(R.string.API_URL);
        BuscarTamanho(1);

        return view;
    }

//    método que traz os tamanhos de acordo com o radio selecionado, Medida ou Tamanho
    public void BuscarTamanho(final int tipoTamanho){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                String url = API_URL + "buscarTamanho.php?tipo="+tipoTamanho;
                return HttpConnection.get(url);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s == null) s = "Sem dados";

                ArrayList<Roupas> roupas = new ArrayList<>();
                if (s != null){
                    try {
                        JSONArray listaTamanhos = new JSONArray(s);
                        for (int i = 0; i < listaTamanhos.length(); i++){
                            JSONObject tamanhoJson = listaTamanhos.getJSONObject(i);

                            Roupas r = new Roupas();
                            r.setTamanho(tamanhoJson.getString("tamanho"));
                            roupas.add(r);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayAdapter<Roupas> tamanhosArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, roupas );
                    tamanhosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_tamanho.setAdapter(tamanhosArrayAdapter);

                }
            }
        }.execute();
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

    public void SalvarRoupa(){

        Roupas r = new Roupas();
        r.setNome(txt_nome.getText().toString());
        r.setDescricao(txt_descricao.getText().toString());

        Categoria catSelecionada = adapterCategoria.getItem(sp_categoria.getSelectedItemPosition());
        idCategoria = catSelecionada.getId();
        r.setIdCategoria(idCategoria);
        Status stSelecionado = adapterStatus.getItem(sp_status.getSelectedItemPosition());
        idStatus = stSelecionado.getId();
        r.setIdStatus(idStatus);
        r.setTamanho(String.valueOf(sp_tamanho.getSelectedItem()));
        //TODO: GRAVAR COR
        r.setMarca(txt_marca.getText().toString());
        if (rd_class_a.isChecked()){
            classificacao = "A";
        } else if (rd_class_b.isChecked()){
            classificacao = "B";
        } else if (rd_class_c.isChecked()){
            classificacao = "C";
        }
        r.setClassificacao(classificacao);
        //TODO: tags

        daoRoupa = RoupasDAO.getInstance();
        daoRoupa.cadastrarRoupa(getContext(), r);
    }
}
