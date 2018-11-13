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

    private String url;
    private Activity activity;
    private SharedPreferencesConfig preferencesConfig;
    private AlertDialog alertDialog;
//    private ProgressDialog progess;

    public LoginApi(String url, Activity activity){
        this.url = url;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return HttpConnection.get(url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progess = new ProgressDialog(activity);
//        progess.setMessage("Entrando...");
//        progess.setCancelable(false);
//        progess.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        if (progess.isShowing()){
//            progess.dismiss();
//        }

        preferencesConfig = new SharedPreferencesConfig(activity.getApplicationContext());

        if (s != null){
            try {
                JSONObject jsonObject = new JSONObject(s);
                boolean login = jsonObject.getBoolean("login");
                if (login){
                    JSONObject usuarioJson = jsonObject.getJSONObject("usuario");
                    String nomeUsuario = usuarioJson.getString("nome");
                    String email = usuarioJson.getString("email");

                    preferencesConfig.writeUsuarioNome(nomeUsuario);
                    preferencesConfig.writeUsuarioEmail(email);
                    preferencesConfig.writeLoginStatus(true);

                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                } else {
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
