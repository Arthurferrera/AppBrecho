package br.com.senaijandira.brechobernadete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import br.com.senaijandira.brechobernadete.api.LoginApi;

public class LoginActivity extends AppCompatActivity {

    TextView lbl_cadastreSe;
    EditText txt_login, txt_senha;
    Button btn_entrar;
    String usuario, senha, API_URL;
    private SharedPreferencesConfig preferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        essa linha bloqueia a aparição do teclado ao iniciar a tela
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        API_URL = getString(R.string.API_URL);

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        txt_login = findViewById(R.id.txt_login);
        txt_senha = findViewById(R.id.txt_senha);
        btn_entrar = findViewById(R.id.btn_entrar);
        lbl_cadastreSe = findViewById(R.id.lbl_cadastre_se);

        if (preferencesConfig.readLoginStatus()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        txt_senha.setText("");
    }

    public boolean ValidatInputs(){
        EditText campoComFoco = null;
        boolean isValid = true;

        if (txt_login.getText().toString().length() == 0){
            campoComFoco = txt_login;
            txt_login.setError("Login obrigatório");
            isValid = false;
        }
        if (txt_senha.getText().toString().length() == 0){
            if (campoComFoco == null){
                campoComFoco = txt_senha;
            }
            txt_senha.setError("Senha obrigatória");
            isValid = false;
        }
        if (campoComFoco != null){
            campoComFoco.requestFocus();
        }
        return isValid;
    }

    public void Login(View view) {
        if (ValidatInputs()){
            usuario = txt_login.getText().toString();
            senha = txt_senha.getText().toString();

            try {
                usuario = URLEncoder.encode(usuario, "UTF-8");
                senha= URLEncoder.encode(senha, "UTF-8");

                String url = API_URL + "login.php?";
                String parametros = "login="+usuario+"&senha="+senha;
                url += parametros;
                new LoginApi(url, this).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            txt_senha.setText("");
        }
    }

    public void AbrirCadastroUsuario(View view) {

    }
}
