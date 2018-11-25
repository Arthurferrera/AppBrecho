package br.com.senaijandira.brechobernadete.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.fragments.CadastroRoupaFragment;
import br.com.senaijandira.brechobernadete.fragments.ComprasFragment;
import br.com.senaijandira.brechobernadete.fragments.HomeFragment;
import br.com.senaijandira.brechobernadete.fragments.LookFragment;
import br.com.senaijandira.brechobernadete.fragments.NotificacoesFragment;
import br.com.senaijandira.brechobernadete.fragments.PromocoesFragment;
import br.com.senaijandira.brechobernadete.fragments.RoupasFragment;
import br.com.senaijandira.brechobernadete.fragments.TagsFragment;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;

//TODO: Sempre Importar o Fragment do pacote import android.support.v4.app.Fragment; quando criar um novo fragment
//TODO: https://pt.stackoverflow.com/questions/137644/slideshow-de-imagens-com-android

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    declarando as variaveis, elementos...
    private SharedPreferencesConfig preferencesConfig;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black);
        setSupportActionBar(toolbar);

//        find's dos elementos
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        instância do SharedPreferencesConfig
        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        infla o header do menu lateral, e seta os valores dos texts views com as informações do usuário
        View viewHeader = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView lbl_nomeUsuario_menu = viewHeader.findViewById(R.id.lbl_nomeUsuario_menu);
        TextView lbl_emailUsuario_menu = viewHeader.findViewById(R.id.lbl_emailUsuario_menu);
        lbl_nomeUsuario_menu.setText(preferencesConfig.readUsuarioNome());
        lbl_emailUsuario_menu.setText(preferencesConfig.readUsuarioEmail());

//        adicionando um fragment
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
        }
    }

//    ação do botão voltar do android, neste caso, fecha o menu lateral
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    método que define e executa a ação de cada item do menu lateral
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//        bloco que decide o que cada opção do menu vai realizar
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
            toolbar.setTitle("Guarda-roupas");
        } else if (id == R.id.nav_minhas_compras) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ComprasFragment()).commit();
            toolbar.setTitle("Minhas Compras");
        } else if (id == R.id.nav_cadastro_roupa) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new CadastroRoupaFragment()).commit();
            toolbar.setTitle("Cadastro de roupas");
        } else if (id == R.id.nav_look_do_dia) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new LookFragment()).commit();
            toolbar.setTitle("Look do dia");
        } else if (id == R.id.nav_tags) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new TagsFragment()).commit();
            toolbar.setTitle("#Tags");
        } else if (id == R.id.nav_favoritos) {
//            cria o FragmentManager
            FragmentManager fm = this.getSupportFragmentManager();
//            cria o fragment
            Fragment fragment = new RoupasFragment();
//            chama outro fragment passando parametros
            Bundle bundle = new Bundle();
            bundle.putInt("id",  0);
            bundle.putString("modo", "favoritos");
            fragment.setArguments(bundle);

            fm.beginTransaction().replace(R.id.frame_content, fragment).commit();
            toolbar.setTitle("Favoritos");
        } else if (id == R.id.nav_notificações) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NotificacoesFragment()).commit();
            toolbar.setTitle("Notificações");
        } else if (id == R.id.nav_promocoes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new PromocoesFragment()).commit();
            toolbar.setTitle("Promoções");
        } else if (id == R.id.nav_sobre) {
            Intent intencao = new Intent(this, SobreActivity.class);
            startActivity(intencao);
        } else if (id == R.id.nav_sair) {
//            AQUI DESLOGAMOS O CARA DO APLICATIVO, E ZERAMOS TODAS AS INFORMAÇÕES
//            DELE, QUE ESTÃO GRAVADAS NO CELULAR
            preferencesConfig.writeLoginStatus(false);
            preferencesConfig.writeUsuarioId(0);
            preferencesConfig.writeUsuarioTipo("");
            preferencesConfig.writeUsuarioNome("");
            preferencesConfig.writeUsuarioEmail("");
//            FINALIZA A TELA ATUAL E INICIA A DE LOGIN
            Intent intencao = new Intent(this, LoginActivity.class);
            startActivity(intencao);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    CRIA O MENU DA TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    IDENTIFICA O ITEM SELECIONADO E DEFINE UMA AÇÃO PARA CADA UM
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filtro:
                startActivity(new Intent(this, PesquisaActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
