package br.com.senaijandira.brechobernadete;

import android.os.Bundle;
import android.app.Activity;

public class VisualizarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
