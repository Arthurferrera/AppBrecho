package br.com.senaijandira.brechobernadete.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.api.LoginApi;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;

public class LoginActivity extends AppCompatActivity {

//    declarando as variaveis, elementos...
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

//        String do caminho padrão da api
        API_URL = getString(R.string.API_URL);

//        instância do SharedPreferencesConfig
        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

//        find's dos elementos
        txt_login = findViewById(R.id.txt_login);
        txt_senha = findViewById(R.id.txt_senha);
        btn_entrar = findViewById(R.id.btn_entrar);
        lbl_cadastreSe = findViewById(R.id.lbl_cadastre_se);

//        CLICK DO LINK QUE REDIRECIONA PARA A PAGINA DE CADASTRO DO SITE
        lbl_cadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String endereco = "www.brechobernadete.com.br/view/cadastro_usuario.php";
                String endereco = "http://192.168.1.34/brecho/view/cadastro_usuario.php";
                Uri uri = Uri.parse(endereco);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

//        iniciando a tela principal, caso o usuário esteja com o login ativo
        if (preferencesConfig.readLoginStatus()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

//    função que faz a validação dos campos
//    verificando se estão vazios
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

//    função que realiza o login
    public void Login(View view) {
//        chamando a validação
        if (ValidatInputs()){
//            resgatando os valores dos inputs
            usuario = txt_login.getText().toString();
            senha = txt_senha.getText().toString();

            try {
//                deixando os valores no padrao UTF-8
                usuario = URLEncoder.encode(usuario, "UTF-8");
                senha= URLEncoder.encode(senha, "UTF-8");

//                url
                String url = API_URL + "login.php?";
//                parametros
                String parametros = "login="+usuario+"&senha="+senha;
//                concatenando
                url += parametros;
//                chamando api
                new LoginApi(url, this).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            txt_senha.setText("");
        }
    }
}
