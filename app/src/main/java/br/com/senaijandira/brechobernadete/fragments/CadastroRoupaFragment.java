package br.com.senaijandira.brechobernadete.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.dao.CategoriaDAO;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.dao.StatusDAO;
import br.com.senaijandira.brechobernadete.dao.TagDAO;
import br.com.senaijandira.brechobernadete.model.Categoria;
import br.com.senaijandira.brechobernadete.model.HttpConnection;
import br.com.senaijandira.brechobernadete.model.ImageFilePath;
import br.com.senaijandira.brechobernadete.model.Roupas;
import br.com.senaijandira.brechobernadete.model.Status;
import br.com.senaijandira.brechobernadete.model.Tag;
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
    Button btn_salvar;
    TagDAO daoTag;
    RoupasDAO daoRoupa;
    CategoriaDAO daoCategoria;
    StatusDAO daoStatus;
    String API_URL, nome, descricao, tamanho, marca, classificacao;
    int idCategoria, idStatus;
    Long idTag, idTagRoupa, idRoupa;
    int COD_GALERIA = 1;
    int PERMISSAO_REQUEST = 2;
    int TIRAR_FOTO = 3;
    int CAMERA = 4;
    File fotoArquivo = null;
    Bitmap foto1, foto2, foto3, foto4, foto5;
    ArrayAdapter<Status> adapterStatus;
    ArrayAdapter<Categoria> adapterCategoria;
    ArrayAdapter<Tag> adapterTag;


    final int REQUEST_PERMISSION = 101;
    final int SELECT_PICTURE = 1;
    String caminhoFoto = "";

    ImageView img_foto1, img_foto2, img_foto3, img_foto4, img_foto5;

    ImageView[] vetorImg;
    int posicaoImg = 0;

    View.OnClickListener clickImageView = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(verificarPermissoes()) {
                posicaoImg = Integer.parseInt (v.getTag().toString());
                capturarImagem();
            } else {
                solicitarPermissoes();
            }
        }
    };

    public CadastroRoupaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         inflando o layout do fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_roupa, container, false);

//        instancias dos daos
        daoStatus = StatusDAO.getInstance();
        daoCategoria = CategoriaDAO.getInstance();
        daoRoupa = RoupasDAO.getInstance();
        daoTag = TagDAO.getInstance();

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
        txt_tag1 = view.findViewById(R.id.txt_tag1);
        img_foto1 = view.findViewById(R.id.foto1);
        img_foto2 = view.findViewById(R.id.foto2);
        img_foto3 = view.findViewById(R.id.foto3);
        img_foto4 = view.findViewById(R.id.foto4);
        img_foto5 = view.findViewById(R.id.foto5);

        vetorImg = new ImageView[] {img_foto1, img_foto2, img_foto3, img_foto4, img_foto5};

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
                abrirCor();
            }
        });

//        click dos image view
        img_foto1.setOnClickListener(clickImageView);
        img_foto2.setOnClickListener(clickImageView);
        img_foto3.setOnClickListener(clickImageView);
        img_foto4.setOnClickListener(clickImageView);
        img_foto5.setOnClickListener(clickImageView);

//        puxar status do banco e carregando no spinner
        List<Status> lstStatus = daoStatus.selecioanrTodos(getContext());
        adapterStatus = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstStatus );
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_status.setAdapter(adapterStatus);

//        puxar categoria do banco, e carregando as no spinner
        ArrayList<Categoria> categoriaList = new ArrayList<>();
        categoriaList = daoCategoria.selecioanrTodos(getContext());
        adapterCategoria = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriaList );
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_categoria.setAdapter(adapterCategoria);

        API_URL = getString(R.string.API_URL);
        BuscarTamanho(1);

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION){
//            verificar se a pesmissao foi dada
            if(grantResults.length>0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                capturarImagem();
            }else{
//                permissao negada
            }

        }
    }

    void solicitarPermissoes(){
        String[] permissoes = new String[1];
        permissoes[0] = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        ActivityCompat.requestPermissions(getActivity(),
                permissoes, REQUEST_PERMISSION);
    }

    boolean verificarPermissoes(){
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }

    private void capturarImagem(){

        Intent pickGaleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        String pickTitle = "Selecione uma imagem";

        Intent chosserIntent = Intent.
                createChooser(pickGaleryIntent, pickTitle);

        if(chosserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(chosserIntent, SELECT_PICTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == SELECT_PICTURE ){
                Uri imgUri = data.getData();
                String realPath = ImageFilePath.getPath(getContext(),
                        data.getData());
                Picasso.get().load(new File(realPath))
                        .into(vetorImg[posicaoImg]);
            }
        }
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

        if (txt_tag1.getText().toString().length() == 0){
            if (campoComFoco == null){
                campoComFoco = txt_tag1;
            }
            txt_tag1.setError("Tag obrigatória");
            isValid = false;
        }

        if (campoComFoco != null){
            campoComFoco.requestFocus();
        }

        return isValid;
    }

//    método que abre a camera do celular, e resgata ela
    private void AbrirGaleria1() {
        Intent tirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (tirarFoto.resolveActivity(getActivity().getPackageManager()) != null) {
//            CRIANDO O ARQUIVO DA FOTO
            try {
                fotoArquivo = criarArquivoFoto();
            } catch (IOException ex) {
                Toast.makeText(getContext(), "Erro: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (fotoArquivo != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "br.com.senaijandira.asd.fileprovider",
                        fotoArquivo);
                tirarFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(tirarFoto, COD_GALERIA);
            }
        }
    }

    private File criarArquivoFoto() throws IOException {
        // Cria o nome do arquivo da imagem
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "BB_" + timeStamp + "_";
        File pasta = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                pasta
        );

        // SALVANDO O PATH DA FOTO
        caminhoFoto = image.getAbsolutePath();
        return image;
    }

//    VERIFICA SE O SDCARD ESTÁ DISPONIVEL
    private File getDirFromSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File sdcard = Environment.getExternalStorageDirectory()
                    .getAbsoluteFile();
            File dir = new File(sdcard, "BrechoBernadete" + File.separator + "imagens");
            if (!dir.exists())
                dir.mkdirs();
            return dir;
        } else {
            return null;
        }
    }

//    SALVA A IMAGEM EM UMA PASTA
//    private void SalvarImagens() throws IOException {
//        String FILENAME = "foto";
//        String string = "foto teste salvar foto";
//
//        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//        fos.write(string.getBytes());
//        fos.close();
//    }

//    MÉTODO QUE RESGATA AS INFORMAÇÕES DOS CAMPOS PARA SALVAR A ROUPA NO BANCO
    public void SalvarRoupa(){

        String retorno = String.valueOf(getDirFromSDCard());
        Toast.makeText(getContext(), retorno+"/"+foto1, Toast.LENGTH_SHORT).show();

        if (ValidarCampos()){
            Roupas r = new Roupas();
            r.setNome(txt_nome.getText().toString());
            r.setDescricao(txt_descricao.getText().toString());
            r.setTamanho(String.valueOf(sp_tamanho.getSelectedItem()));
            //TODO: GRAVAR COR
            r.setMarca(txt_marca.getText().toString());

//            Resgatando as tags do editText
            String tags = txt_tag1.getText().toString();
            String[] listaTags = tags.split(" ");

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
//            TODO: SALVAR A FOTO

//        CHAMANDO O MÉTODO DE SALVAR NO DAO, E CASO SALVE, mostra-se uma mensagem
            idRoupa = daoRoupa.cadastrarRoupa(getContext(), r);
            if (idRoupa != -1){
                for(int i = 0; i < listaTags.length; i++){
                    int idTagE = daoTag.verificarTag(getContext(), listaTags[i]);
                    if (idTagE != 0){
                        idTag = Long.valueOf(idTagE);
                    } else {
                        idTag = daoTag.inserirTag(getContext(), listaTags[i]);
                    }
                    if (idTag != -1){
                        idTagRoupa = daoTag.inserirTagRoupa(getContext(), idTag, idRoupa);
                    }
                }
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Erro !");
                alertDialog.setIcon(R.drawable.ic_report);
                alertDialog.setMessage("Erro ao tentar adicionar uma roupa ao seu guarda-roupas.");
                alertDialog.setPositiveButton("Ok", null);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }

            if (idTagRoupa != -1){
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
                alertDialog.setMessage("Erro ao tentar adicionar as tags de uma roupa");
                alertDialog.setPositiveButton("Ok", null);
                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        }
    }
}
