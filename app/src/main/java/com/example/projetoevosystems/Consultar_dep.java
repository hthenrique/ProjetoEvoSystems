package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetoevosystems.Uteis.DepartamentoAdapter;
import com.example.projetoevosystems.Uteis.DepartamentoDAO;

import java.util.ArrayList;

public class Consultar_dep extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView listViewDepartamentos;
    private DepartamentoAdapter departamentoAdapter;
    private ArrayList<DepartamentoDAO> departamentos;
    private DepartamentoDAO departamentoDAOEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_dep);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listViewDepartamentos = (ListView)findViewById(R.id.listViewDepartamentos);
        listViewDepartamentos.setOnItemClickListener(this);

        departamentos = new DepartamentoDAO(this).getDepartamentos();
        departamentoAdapter = new DepartamentoAdapter(this,departamentos);
        listViewDepartamentos.setAdapter(departamentoAdapter);
    }

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
