package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_consultadep;
    private Button btn_consultarfun;
    private FloatingActionButton fab_cad;
    private FloatingActionButton fab_fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab_cad = findViewById(R.id.fab_cad);
        fab_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrardepclasse = new Intent(MainActivity.this, add_dep.class);
                startActivity(cadastrardepclasse);
            }
        });

        FloatingActionButton fab_fun = findViewById(R.id.fab_fun);
        fab_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrarfunclasse = new Intent(MainActivity.this, add_fun.class);
                startActivity(cadastrarfunclasse);
            }
        });

        Button btn_consultadep = findViewById(R.id.btn_consultadep);
        btn_consultadep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultardepclasse = new Intent(MainActivity.this, Consultar_dep.class);
                startActivity(consultardepclasse);
            }
        });

        Button btn_consultarfun = findViewById(R.id.btn_consultarfun);
        btn_consultarfun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultarfunclasse = new Intent(MainActivity.this, Consultar_fun.class);
                startActivity(consultarfunclasse);
            }
        });
    }


}
