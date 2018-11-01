package br.com.senaijandira.brechobernadete;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    Button btn_salvar;
    TagDAO daoTag;
    RoupasDAO daoRoupa;
    CategoriaDAO daoCategoria;
    StatusDAO daoStatus;
    String API_URL, nome, descricao, tamanho, marca, classificacao;
    int idCategoria, idStatus;
    Long idTag, idTagRoupa, idRoupa;
    int COD_GALERIA = 1;
    ImageView img_foto1, img_foto2, img_foto3, img_foto4, img_foto5;
    Bitmap foto1, foto2, foto3, foto4, foto5;
    ArrayAdapter<Status> adapterStatus;
    ArrayAdapter<Categoria> adapterCategoria;
    ArrayAdapter<Tag> adapterTag;
    StringBuffer nomeFoto;

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
        daoTag = TagDAO.getInstance();

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
        txt_tag1 = view.findViewById(R.id.txt_tag1);
        img_foto1 = view.findViewById(R.id.foto1);
        img_foto2 = view.findViewById(R.id.foto2);
        img_foto3 = view.findViewById(R.id.foto3);
        img_foto4 = view.findViewById(R.id.foto4);
        img_foto5 = view.findViewById(R.id.foto5);

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
        img_foto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirGaleria1();
            }
        });
//        img_foto2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AbrirGaleria2();
//            }
//        });
//        img_foto3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AbrirGaleria3();
//            }
//        });
//        img_foto4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AbrirGaleria4();
//            }
//        });
//        img_foto5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AbrirGaleria5();
//            }
//        });

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

    private void AbrirGaleria1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                galleryAddPic();
                setPic();
            } catch (IOException ex) {
                Toast.makeText(getContext(), "Erro: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "br.com.senaijandira.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, COD_GALERIA);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COD_GALERIA && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null){
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                img_foto1.setImageBitmap(imageBitmap);
            } else {
                Toast.makeText(getContext(), extras+"", Toast.LENGTH_SHORT).show();
            }

        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "BB_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = img_foto1.getWidth();
        int targetH = img_foto1.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        img_foto1.setImageBitmap(bitmap);
    }

//    public void AbrirGaleria2(){
//        COD_GALERIA = 2;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//
//        startActivityForResult(intent, COD_GALERIA);
//    }
//
//    public void AbrirGaleria3(){
//        COD_GALERIA = 3;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//
//        startActivityForResult(intent, COD_GALERIA);
//    }
//
//    public void AbrirGaleria4(){
//        COD_GALERIA = 4;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//
//        startActivityForResult(intent, COD_GALERIA);
//    }
//
//    public void AbrirGaleria5(){
//        COD_GALERIA = 5;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//
//        startActivityForResult(intent, COD_GALERIA);
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.gc(); // garbage colector
//        if (requestCode == COD_GALERIA) {
//            if (resultCode == getActivity().RESULT_OK) {
//                try {
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 3;
//                    Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
//                    byte[] fotoBinario = outputStream.toByteArray();
//
//                    String encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);
//
//                    img_foto1.setImageBitmap(imageBitmap); // ImageButton, seto a foto como imagem do botão
//
//                    boolean isImageTaken = true;
//                } catch (Exception e) {
//                    Toast.makeText(getContext(), "Picture Not taken",Toast.LENGTH_LONG).show();e.printStackTrace();
//                }
//            } else if (resultCode == getActivity().RESULT_CANCELED) {
//                Toast.makeText(getContext(), "Picture was not taken 1 ", Toast.LENGTH_SHORT);
//            } else {
//                Toast.makeText(getContext(), "Picture was not taken 2 ", Toast.LENGTH_SHORT);
//            }
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (COD_GALERIA){
//            case 1:
//                if (resultCode == getActivity().RESULT_OK){
//                    try {
//                        InputStream inp = getContext().getContentResolver().openInputStream(data.getData());
//
//                        foto1 = BitmapFactory.decodeStream(inp);
//
//                        img_foto1.setImageBitmap(foto1);
//                    } catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//                break;
//            case 2:
//                if (resultCode == getActivity().RESULT_OK){
//                    try {
//                        InputStream inp = getContext().getContentResolver().openInputStream(data.getData());
//
//                        foto2 = BitmapFactory.decodeStream(inp);
//
//                        img_foto2.setImageBitmap(foto2);
//                    } catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//                break;
//            case 3:
//                if (resultCode == getActivity().RESULT_OK){
//                    try {
//                        InputStream inp = getContext().getContentResolver().openInputStream(data.getData());
//
//                        foto3 = BitmapFactory.decodeStream(inp);
//
//                        img_foto3.setImageBitmap(foto3);
//                    } catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//                break;
//            case 4:
//                if (resultCode == getActivity().RESULT_OK){
//                    try {
//                        InputStream inp = getContext().getContentResolver().openInputStream(data.getData());
//
//                        foto4 = BitmapFactory.decodeStream(inp);
//
//                        img_foto4.setImageBitmap(foto4);
//                    } catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//                break;
//            case 5:
//                if (resultCode == getActivity().RESULT_OK){
//                    try {
//                        InputStream inp = getContext().getContentResolver().openInputStream(data.getData());
//
//                        foto5 = BitmapFactory.decodeStream(inp);
//
//                        img_foto5.setImageBitmap(foto5);
//                    } catch (Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//                break;
//        }
//    }

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

//        if (ValidarCampos()){
//            Roupas r = new Roupas();
//            r.setNome(txt_nome.getText().toString());
//            r.setDescricao(txt_descricao.getText().toString());
//            r.setTamanho(String.valueOf(sp_tamanho.getSelectedItem()));
//            //TODO: GRAVAR COR
//            r.setMarca(txt_marca.getText().toString());
//
////            Resgatando as tags do editText
//            String tags = txt_tag1.getText().toString();
//            String[] listaTags = tags.split(" ");
//
////        PEGANDO O ID DO ITEM SELECIONADO
//            Categoria catSelecionada = adapterCategoria.getItem(sp_categoria.getSelectedItemPosition());
//            idCategoria = catSelecionada.getId();
//            r.setIdCategoria(idCategoria);
////        PEGANDO O ID DO ITEM SELECIONADO
//            Status stSelecionado = adapterStatus.getItem(sp_status.getSelectedItemPosition());
//            idStatus = stSelecionado.getId();
//            r.setIdStatus(idStatus);
//
//            if (rd_class_a.isChecked()){
//                classificacao = "A";
//            } else if (rd_class_b.isChecked()){
//                classificacao = "B";
//            } else if (rd_class_c.isChecked()){
//                classificacao = "C";
//            }
//            r.setClassificacao(classificacao);
////            TODO: SALVAR A FOTO
//
////        CHAMANDO O MÉTODO DE SALVAR NO DAO, E CASO SALVE, mostra-se uma mensagem
//            idRoupa = daoRoupa.cadastrarRoupa(getContext(), r);
//            if (idRoupa != -1){
//                for(int i = 0; i < listaTags.length; i++){
//                    int idTagE = daoTag.verificarTag(getContext(), listaTags[i]);
//                    if (idTagE != 0){
//                        idTag = Long.valueOf(idTagE);
//                    } else {
//                        idTag = daoTag.inserirTag(getContext(), listaTags[i]);
//                    }
//                    if (idTag != -1){
//                        idTagRoupa = daoTag.inserirTagRoupa(getContext(), idTag, idRoupa);
//                    }
//                }
//            } else {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//                alertDialog.setTitle("Erro !");
//                alertDialog.setIcon(R.drawable.ic_report);
//                alertDialog.setMessage("Erro ao tentar adicionar uma roupa ao seu guarda-roupas.");
//                alertDialog.setPositiveButton("Ok", null);
//                AlertDialog alert = alertDialog.create();
//                alert.show();
//            }
//
//            if (idTagRoupa != -1){
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//                alertDialog.setTitle("Sucesso !");
//                alertDialog.setIcon(R.drawable.ic_check);
//                alertDialog.setMessage("Roupa adicionada ao seu guarda-roupas.");
//                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ZerarTela();
//                    }
//                });
//                AlertDialog alert = alertDialog.create();
//                alert.show();
//            } else {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
//                alertDialog.setTitle("Erro !");
//                alertDialog.setIcon(R.drawable.ic_report);
//                alertDialog.setMessage("Erro ao tentar adicionar as tags de uma roupa");
//                alertDialog.setPositiveButton("Ok", null);
//                AlertDialog alert = alertDialog.create();
//                alert.show();
//            }
//        }
    }
}
