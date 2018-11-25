package br.com.senaijandira.brechobernadete.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.senaijandira.brechobernadete.activity.MainActivity;
import br.com.senaijandira.brechobernadete.model.HttpConnection;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;

public class LoginApi extends AsyncTask<Void, Void, String> {

//    DECLARANDO ATRIBUTOS, VARIAVEIS...
    private String url;
    private Activity activity;
    private SharedPreferencesConfig preferencesConfig;
    private AlertDialog alertDialog;
//    private ProgressDialog progess;

//    CONSTRUTOR
    public LoginApi(String url, Activity activity){
        this.url = url;
        this.activity = activity;
    }

//    CHAMADA DA API
    @Override
    protected String doInBackground(Void... voids) {
        return HttpConnection.get(url);
    }

//    ANTES DE EXECUTAR INICIA A BARRA DE PROGRESSO
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progess = new ProgressDialog(activity);
//        progess.setMessage("Entrando...");
//        progess.setCancelable(false);
//        progess.show();
    }

//    PEGA OS DADOS DE RETORNO DA API
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        if (progess.isShowing()){
//            progess.dismiss();
//        }

//        INSTANCIA DO SHAREDPREFERENCES
        preferencesConfig = new SharedPreferencesConfig(activity.getApplicationContext());

//        VERIFICA SE ESTÁ VAZIO
        if (s != null){
            try {
                JSONObject jsonObject = new JSONObject(s);
                boolean login = jsonObject.getBoolean("login");
                if (login){
//                    LOGIN EFETUADO, RESGATA AS INFORMAÇÕES NECESSÁRIAS
                    JSONObject usuarioJson = jsonObject.getJSONObject("usuario");
                    String nomeUsuario = usuarioJson.getString("nome");
                    String email = usuarioJson.getString("email");
                    int idUsuario = usuarioJson.getInt("idCliente");
                    String tipoCliente = jsonObject.getString("tipo");

//                    GRAVAMOS ELAS NO SHARED PREFERENCES,
//                    PARA TER ACESSO DE QUALQUER LUGAR DO APP
                    preferencesConfig.writeUsuarioNome(nomeUsuario);
                    preferencesConfig.writeUsuarioEmail(email);
                    preferencesConfig.writeLoginStatus(true);
                    preferencesConfig.writeUsuarioId(idUsuario);
                    preferencesConfig.writeUsuarioTipo(tipoCliente);

//                    INICIA A TELA PRINCIPAL
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                } else {
//                    CASO RETORNE O LOGIN COMO FALSE, MOSTRA MENSAGEM DE ERRO
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Erro!");
                    builder.setMessage("Usuário e/ou senha estão incorretos.");
                    builder.setPositiveButton("Ok", null);
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
