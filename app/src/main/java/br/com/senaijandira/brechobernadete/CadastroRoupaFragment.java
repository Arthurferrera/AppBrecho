package br.com.senaijandira.brechobernadete;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

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
    EditText txt_nome, txt_descricao, txt_marca, txt_tag1, txt_tag2;
    RadioButton rd_medida, rd_tamanho, rd_class_a, rd_class_b, rd_class_c;
//    AutoCompleteTextView autoCompleteTags;
    Button btn_salvar;
    TagDAO daoTag;
    RoupasDAO daoRoupa;
    CategoriaDAO daoCategoria;
    StatusDAO daoStatus;
    String API_URL, nome, descricao, tamanho, marca, classificacao;
    int idCategoria, idStatus;

    ArrayAdapter<Status> adapterStatus;
    ArrayAdapter<Categoria> adapterCategoria;
    ArrayAdapter<Tag> adapterTag;

    public CadastroRoupaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        instancias dos daos
        daoStatus = StatusDAO.getInstance();
        daoCategoria = CategoriaDAO.getInstance();
        daoRoupa = RoupasDAO.getInstance();

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
//        autoCompleteTags = view.findViewById(R.id.autoCompleteTags);
        txt_tag1 = view.findViewById(R.id.txt_tag1);

//        setando o click dos elementos
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
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                chama método que abre o dialog da cor
                abrirCor();
            }
        });

        //puxar status do banco e carregando no spinner
        List<Status> lstStatus = daoStatus.selecioanrTodos(getContext());
        adapterStatus = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstStatus );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapterStatus);

        //puxar categoria do banco, e carregando as no spinner
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        categoriaList = daoCategoria.selecioanrTodos(getContext());
        adapterCategoria = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriaList );
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_categoria.setAdapter(adapterCategoria);

        //puxar categoria do banco, e carregando as no spinner
//        ArrayList<Tag> tagList = new ArrayList<>();
//        tagList = daoTag.selecionatTodas(getContext());
//        adapterTag = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, tagList );
//        adapterTag.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        autoCompleteTags.setThreshold(3);
//        autoCompleteTags.setAdapter(adapterTag);


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

//    MÉTODO QUE RESGATA AS INFORMAÇÕES DOS CAMPOS PARA SALVAR A ROUPA NO BANCO
    public void SalvarRoupa(){

        if (ValidarCampos()){
            Roupas r = new Roupas();
            r.setNome(txt_nome.getText().toString());
            r.setDescricao(txt_descricao.getText().toString());
            r.setTamanho(String.valueOf(sp_tamanho.getSelectedItem()));
            //TODO: GRAVAR COR
            r.setMarca(txt_marca.getText().toString());
            r.setTag(txt_tag1.getTag().toString());

//        PEGANDO O ID DO ITEM SELECIONADO
            Categoria catSelecionada = adapterCategoria.getItem(sp_categoria.getSelectedItemPosition());
            idCategoria = catSelecionada.getId();
            r.setIdCategoria(idCategoria);
//        PEGANDO O ID DO ITEM SELECIONADO
            Status stSelecionado = adapterStatus.getItem(sp_status.getSelectedItemPosition());
            idStatus = stSelecionado.getId();
            r.setIdStatus(idStatus);

            if (rd_class_a.isChecked()){
                classificacao = "A";
            } else if (rd_class_b.isChecked()){
                classificacao = "B";
            } else if (rd_class_c.isChecked()){
                classificacao = "C";
            }
            r.setClassificacao(classificacao);

            //TODO: tags

//        CHAMANDO O MÉTODO DE SALVAR NO DAO, E CASO RETORNE TRUE(SALVOU)
//        MOSTRA UMA MENSAGEM
            Long idRoupa = daoRoupa.cadastrarRoupa(getContext(), r);
            Long idTag = daoTag.inserirTag(getContext(), r.getTag());


            if (idRoupa != -1 ){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Sucesso !");
                alertDialog.setIcon(R.drawable.ic_check);
                alertDialog.setMessage("Roupa adicionada ao seu guarda-roupas.");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ZerarTela();
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.show();
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Erro !");
                alertDialog.setIcon(R.drawable.ic_report);
                alertDialog.setMessage("Erro ao tentar adicionar uma roupa ao seu guarda-roupas.");
                alertDialog.setPositiveButton("Ok", null);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        }
    }

//    MÉTODO QUE LIMPA TODOS OS CAMPOS AO SALVAR A ROUPA
    public void ZerarTela(){
        txt_nome.setText("");
        txt_descricao.setText("");
        txt_marca.setText("");
        sp_categoria.setSelection(0);
        sp_status.setSelection(0);
        sp_tamanho.setSelection(0);
        rd_medida.setChecked(true);
        rd_class_a.setChecked(true);
        txt_tag1.setText("");
        //TODO: ZERAR COR DO BOTAO DE COR
    }

    public Boolean ValidarCampos(){

        EditText campoComFoco = null;
        boolean isValid = true;

        if (txt_nome.getText().toString().length() == 0){
            campoComFoco = txt_nome;
            txt_nome.setError("Nome obrigatório");
            isValid = false;
        }
        if (txt_descricao.getText().toString().length() == 0){
            if (campoComFoco == null){
                campoComFoco = txt_descricao;
            }
            txt_descricao.setError("Descrição obrigatória");
            isValid = false;
        }
        if (txt_marca.getText().toString().length() == 0){
            if (campoComFoco == null){
                campoComFoco = txt_marca;
            }
            txt_marca.setError("Marca obrigatória");
            isValid = false;
        }

        if (campoComFoco != null){
            campoComFoco.requestFocus();
        }

        return isValid;
    }
}
