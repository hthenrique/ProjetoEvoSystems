package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoevosystems.Uteis.FuncionarioAdapter;
import com.example.projetoevosystems.Uteis.FuncionarioDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Consultar_fun_dep extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listViewFuncionarios;
    private FuncionarioAdapter funcionarioAdapter;
    private ArrayList<FuncionarioDAO> funcionarios;
    private FuncionarioDAO funcionarioDAOEdição;
    private FloatingActionButton fab_add_fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_fun);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //escuta se o funcionario foi clicado
        listViewFuncionarios = (ListView)findViewById(R.id.listViewFuncionarios);
        listViewFuncionarios.setOnItemClickListener(this);

        //chama todos funcionarios cadastrados
        funcionarios = new FuncionarioDAO(this).getFuncionarios();
        funcionarioAdapter = new FuncionarioAdapter(this,funcionarios);
        listViewFuncionarios.setAdapter(funcionarioAdapter);

        //botao para cadastrar um novo funcionario
        FloatingActionButton fab_fun = findViewById(R.id.fab_add_fun);
        fab_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrarfunclasse = new Intent(Consultar_fun_dep.this, add_fun.class);
                startActivity(cadastrarfunclasse);
            }
        });
    }

    //fecha a atual activity depois que selecionar o funcionario para editar
    @Override
    public void onClick(View view) {
        finish();
    }

    //ação ao clicar no funcionario
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id_dep_fk) {
        FuncionarioDAO funcionarioDAO = funcionarios.get(position);
        Intent intent = new Intent(this,add_fun.class);
        intent.putExtra("consulta",funcionarioDAO.getId_dep_fk());
        funcionarioDAOEdição = funcionarioDAO;
        startActivity(intent);
    }

    //exclui o funcionario escolhido
    @Override
    protected void onResume() {
        super.onResume();
        if (funcionarioDAOEdição != null){
            funcionarioDAOEdição.carregaFunPelofk(funcionarioDAOEdição.getId_fun());
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
