package com.example.projetoevosystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetoevosystems.Uteis.DbBackend;
import com.example.projetoevosystems.Uteis.DepartamentoDAO;
import com.example.projetoevosystems.Uteis.DepartamentoSpinnerAdapter;
import com.example.projetoevosystems.Uteis.FuncionarioDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class add_fun extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFunNome;
    private EditText editTextRg;
    private Spinner spinnerDepartamentos;
    private FloatingActionButton deletefun_btn;
    private FloatingActionButton savefun_btn;
    private FloatingActionButton cancelfun_btn;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<DepartamentoDAO> departamentos;
    private DepartamentoSpinnerAdapter departamentoSpinnerAdapter;
    String selectedDepId = "";

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fun);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Spinner spinnerDepartamentos = (Spinner) findViewById(R.id.spinnerDepartementos);
        editTextFunNome = (EditText) findViewById(R.id.editTextFunNome);
        editTextRg = (EditText) findViewById(R.id.editTextRg);
        savefun_btn = (FloatingActionButton) findViewById(R.id.savefun_btn);
        deletefun_btn = (FloatingActionButton) findViewById(R.id.deletefun_btn);
        cancelfun_btn = (FloatingActionButton) findViewById(R.id.cancelfun_btn);

        deletefun_btn.setOnClickListener(this);
        savefun_btn.setOnClickListener(this);
        cancelfun_btn.setOnClickListener(this);

        //esconder teclado ao clicar fora dessas caixas
        editTextFunNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    esconderTeclado(view);
                }
            }
        });
        editTextRg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                    esconderTeclado(view);
                }
            }
        });

        DbBackend dbBackend = new DbBackend(add_fun.this);
        String[] spinnerLista = dbBackend.getTodosSpinner();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(add_fun.this,android.R.layout.simple_spinner_item, spinnerLista);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamentos.setAdapter(spinnerAdapter);

        spinnerDepartamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence dep = (CharSequence) parent.getItemAtPosition(position);
                selectedDepId = dep.toString().split(" ")[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        if (getIntent().getExtras() != null){
            setTitle(getString(R.string.titulo_editandofun));
            int id_fun = getIntent().getExtras().getInt("consulta");
            funcionarioDAO.carregaFunPeloId(id_fun);

            if (funcionarioDAO.getNome_fun() != null)
                editTextFunNome.setText(funcionarioDAO.getNome_fun().toString());
                editTextRg.setText(funcionarioDAO.getRg_fun().toString());
        }else {
            setTitle(getString(R.string.titulo_incluindofun));
        }

        deletefun_btn.setEnabled(true);
        if (funcionarioDAO.getId_fun() == -1)
            deletefun_btn.setEnabled(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancelfun_btn:{
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            }
            case R.id.deletefun_btn:{
                funcionarioDAO.excluirFun();
                Toast.makeText(this, "Funcionário deletado", Toast.LENGTH_SHORT).show();
                Intent intentMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMainActivity);
                break;
            }
            case R.id.savefun_btn:{
                boolean valido = true;
                funcionarioDAO.setNome_fun(editTextFunNome.getText().toString().trim());
                funcionarioDAO.setRg_fun(editTextRg.getText().toString().trim());
                funcionarioDAO.setId_dep_fk(selectedDepId);

                if (funcionarioDAO.getNome_fun().equals("")){
                    editTextFunNome.setError(getString(R.string.nome_obrigatorio));
                    valido = false;
                }
                if (funcionarioDAO.getRg_fun().equals("")){
                    editTextRg.setError(getString(R.string.documento_obrigatorio));
                    valido = false;
                }
                if (editTextRg.getText().length() < 9){
                    editTextRg.setError(getString(R.string.documento_invalido));
                    valido = false;
                }
                if (valido){
                    funcionarioDAO.salvarFun();
                    Toast.makeText(this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
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

    //Esconder teclado
    public void esconderTeclado(View v){
        if (v != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);

        }
    }


}
