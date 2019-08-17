package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoevosystems.Uteis.DepartamentoAdapter;
import com.example.projetoevosystems.Uteis.DepartamentoDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Consultar_dep extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listViewDepartamentos;
    private DepartamentoAdapter departamentoAdapter;
    private ArrayList<DepartamentoDAO> departamentos;
    private DepartamentoDAO departamentoDAOEdicao;
    private FloatingActionButton fab_add_dep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_dep);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //escuta se o departamento foi clicado
        listViewDepartamentos = (ListView)findViewById(R.id.listViewDepartamentos);
        listViewDepartamentos.setOnItemClickListener(this);

        //chama todos os departamentos cadastrados
        departamentos = new DepartamentoDAO(this).getDepartamentos();
        departamentoAdapter = new DepartamentoAdapter(this,departamentos);
        listViewDepartamentos.setAdapter(departamentoAdapter);

        registerForContextMenu(listViewDepartamentos);

        //chama a tela de cadastro de departamentos
        FloatingActionButton fab_cad = findViewById(R.id.fab_add_dep);
        fab_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cadastrardepclasse = new Intent(Consultar_dep.this, add_dep.class);
                startActivity(cadastrardepclasse);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.departamento_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_1:
                Intent con_fun = new Intent(this,Consultar_fun_dep.class);
                startActivity(con_fun);
                return true;
                default:
                    return super.onContextItemSelected(item);
        }
    }

    //fecha a activity depois que tocar em um departamento para edita-los
    @Override
    public void onClick(View view) {
        finish();
    }

    //Ação ao clicar no item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id_dep) {
        DepartamentoDAO departamentoDAO = departamentos.get(position);
        Intent intent = new Intent(this,add_dep.class);
        intent.putExtra("consulta",departamentoDAO.getId_dep());
        departamentoDAOEdicao = departamentoDAO;
        startActivity(intent);
    }

    //chama o metodo que exclui o departamento
    @Override
    protected void onResume() {
        super.onResume();
        if(departamentoDAOEdicao != null){
            departamentoDAOEdicao.carregaDepPeloId(departamentoDAOEdicao.getId_dep());
            if (departamentoDAOEdicao.isExcluir())
                departamentos.remove(departamentoDAOEdicao);
            departamentoAdapter.notifyDataSetChanged();
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
