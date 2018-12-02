package br.com.senaijandira.brechobernadete.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

import br.com.senaijandira.brechobernadete.R;

public class SobreActivity extends AppCompatActivity {

//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        //getActionBar().setDisplayHomeAsUpEnabled(false);
        final Toolbar toolbar = (Toolbar) Objects.requireNonNull(this).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Sobre");
        toolbar.setNavigationIcon(R.drawable.ic_voltar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
