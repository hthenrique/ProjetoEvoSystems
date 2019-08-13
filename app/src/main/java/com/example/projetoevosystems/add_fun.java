package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

public class add_fun extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fun);
        Toolbar toolbar = findViewById(R.id.toolbar_add_fun);
        setSupportActionBar(toolbar);


        final Spinner spinner = (Spinner) findViewById(R.id.spinnerDepartementos);

        String[] listdeps = new String[]{
                "Selecione o departamento..."
        };

    }


}
