package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetoevosystems.Uteis.FuncionarioAdapter;
import com.example.projetoevosystems.Uteis.FuncionarioDAO;

import java.util.ArrayList;

public class Consultar_fun extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listViewFuncionarios;
    private FuncionarioAdapter funcionarioAdapter;
    private ArrayList<FuncionarioDAO> funcionarios;
    private FuncionarioDAO funcionarioDAOEdição;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_fun);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewFuncionarios = (ListView)findViewById(R.id.listViewFuncionarios);
        listViewFuncionarios.setOnItemClickListener(this);

        funcionarios = new FuncionarioDAO(this).getFuncionarios();
        funcionarioAdapter = new FuncionarioAdapter(this,funcionarios);
        listViewFuncionarios.setAdapter(funcionarioAdapter);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id_fun) {
        FuncionarioDAO funcionarioDAO = funcionarios.get(position);
        Intent intent = new Intent(this,add_fun.class);
        intent.putExtra("consulta",funcionarioDAO.getId_fun());
        funcionarioDAOEdição = funcionarioDAO;
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (funcionarioDAOEdição != null){
            funcionarioDAOEdição.carregaFunPeloId(funcionarioDAOEdição.getId_fun());
            if (funcionarioDAOEdição.isExcluir());
                funcionarios.remove(funcionarioDAOEdição);
            funcionarioAdapter.notifyDataSetChanged();
        }
    }

    //Botão de voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }
}
