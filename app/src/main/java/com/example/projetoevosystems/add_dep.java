package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.projetoevosystems.Uteis.DepartamentoDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class add_dep extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDep_nome;
    private EditText editTextDep_sigla;
    private FloatingActionButton delete_btn;
    private FloatingActionButton save_btn;
    private FloatingActionButton cancel_btn;

    private final DepartamentoDAO departamentoDAO = new DepartamentoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dep);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editTextDep_nome = (EditText) findViewById(R.id.editTextDep_name);
        editTextDep_sigla = (EditText) findViewById(R.id.editTextDep_sigla);
        save_btn = (FloatingActionButton) findViewById(R.id.save_btn);
        delete_btn = (FloatingActionButton) findViewById(R.id.delete_btn);
        cancel_btn = (FloatingActionButton) findViewById(R.id.cancel_btn);

        delete_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);


        if (getIntent().getExtras() != null){
            setTitle(getString(R.string.titulo_editando));
            int id_dep = getIntent().getExtras().getInt("consulta");
            departamentoDAO.carregaDepPeloId(id_dep);

            if(departamentoDAO.getNome_dep() != null)
                editTextDep_nome.setText(departamentoDAO.getNome_dep().toString());
                editTextDep_sigla.setText(departamentoDAO.getSigla_dep().toString());

        }else {
            setTitle(getString(R.string.titulo_incluindo));
        }

        delete_btn.setEnabled(true);
        if (departamentoDAO.getId_dep() == -1)
            delete_btn.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_btn:{
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            }
            case R.id.delete_btn:{
                departamentoDAO.excluirDep();
                Toast.makeText(this, "Departamento deletado", Toast.LENGTH_SHORT).show();
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            }
            case R.id.save_btn:{
                boolean valido = true;
                departamentoDAO.setNome_dep(editTextDep_nome.getText().toString().trim());
                departamentoDAO.setSigla_dep(editTextDep_sigla.getText().toString().trim().toUpperCase());

                if (departamentoDAO.getNome_dep().equals("")){
                    editTextDep_nome.setError(getString(R.string.nome_obrigatorio));
                    valido = false;
                }
                if (departamentoDAO.getSigla_dep().equals("")){
                    editTextDep_sigla.setError(getString(R.string.sigla_obrigatoria));
                    valido = false;
                }
                if (valido){
                    departamentoDAO.salvarDep();
                    Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentMainActivity);
                }
                break;
            }
        }
    }

    //Botão de voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            default:break;
        }
        return true;
    }

}
