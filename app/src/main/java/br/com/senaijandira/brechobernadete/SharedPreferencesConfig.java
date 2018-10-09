package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preferences), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status), status);
        editor.commit();
    }

    public boolean readLoginStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status), false);
        return status;
    }

    public void writeUsuarioNome(String nome){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.usuario_nome), nome);
        editor.commit();
    }

    public String readUsuarioNome(){
        String nome = "";
        nome = sharedPreferences.getString(context.getResources().getString(R.string.usuario_nome), "");
        return nome;
    }

    public void writeUsuarioEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.usuario_email), email);
        editor.commit();
    }

    public String readUsuarioEmail(){
        String email = "";
        email = sharedPreferences.getString(context.getResources().getString(R.string.usuario_email), "");
        return email;
    }
}
