package br.com.senaijandira.brechobernadete;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

//TODO: Sempre Importar o Fragment do pacote import android.support.v4.app.Fragment; quando criar um novo fragment
//TODO: https://pt.stackoverflow.com/questions/137644/slideshow-de-imagens-com-android

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferencesConfig preferencesConfig;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FavoritosFragment()).commit();
            toolbar.setTitle("Favoritos");
        } else if (id == R.id.nav_notificações) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NotificacoesFragment()).commit();
            toolbar.setTitle("Notificações");
        } else if (id == R.id.nav_promocoes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new PromocoesFragment()).commit();
            toolbar.setTitle("Promoções");
        } else if (id == R.id.nav_sobre) {
            Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_visualizar) {
            startActivity(new Intent(this, VisualizarActivity.class));
        } else if (id == R.id.nav_sair) {
            preferencesConfig.writeLoginStatus(false);
            Intent intencao = new Intent(this, LoginActivity.class);
            startActivity(intencao);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
