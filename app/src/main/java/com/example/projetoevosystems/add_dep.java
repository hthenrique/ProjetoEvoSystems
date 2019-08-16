package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        //Escuta se os botões foram clicados
        delete_btn.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        //verifica se a foco no editText, se não tiver foco o teclado fecha
        editTextDep_nome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    esconderTeclado(view);
                }
            }
        });
        editTextDep_sigla.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    esconderTeclado(view);
                }
            }
        });

        //Verifica se a acitivity atual foi chamada para adicionar ou editar departamento
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
        //habilita o botao de excluir funcionario se vindo da tela de consulta
        delete_btn.setEnabled(true);
        if (departamentoDAO.getId_dep() == -1)
            delete_btn.setEnabled(false);
    }

    //Ações dos botões. cancelar, excluir e salvar
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel_btn:{
                //Cancela e volta para a tela inicial
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            }
            case R.id.delete_btn:{
                //exclui o departamento escolhido na lista de consulta
                departamentoDAO.excluirDep();
                Toast.makeText(this, "Departamento deletado", Toast.LENGTH_SHORT).show();
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            }
            case R.id.save_btn:{
                //verifica se todos os campos foram preenchidos
                boolean valido = true;
                departamentoDAO.setNome_dep(editTextDep_nome.getText().toString().trim());
                departamentoDAO.setSigla_dep(editTextDep_sigla.getText().toString().trim().toUpperCase());
                //verifica se o nome esta vazio
                if (departamentoDAO.getNome_dep().equals("")){
                    editTextDep_nome.setError(getString(R.string.nome_obrigatorio));
                    valido = false;
                }
                //verifica se a sigla esta vazia
                if (departamentoDAO.getSigla_dep().equals("")){
                    editTextDep_sigla.setError(getString(R.string.sigla_obrigatoria));
                    valido = false;
                }
                //chama o metodo de salvar os dados no banco de dados
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
                //chama a tela principal
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            default:break;
        }
        return true;
    }

    //Metodo de sconder teclado
    public void esconderTeclado(View v){
        if (v != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);

        }
    }

}
