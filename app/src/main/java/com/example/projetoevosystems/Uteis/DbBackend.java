package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends BdObjeto {
    //conecta essa classe com a classe de conexão com o banco de dados
    public DbBackend(Context context) {
        super(context);
    }

    //metodo responsavel por buscar departamentos no banco de dados e construir lista
    public String[] getTodosSpinner(){
        String query = "SELECT * FROM Tabela_dep";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);

        //lista os departamentos para a spinner
        ArrayList<String> spinnerDep = new ArrayList<String>();
        if (cursor.moveToFirst()){
            do{
                //mostra os dados da tabela de departamentos em cada linha do spinner
                String codigodep = cursor.getString(cursor.getColumnIndex("id_dep"));
                String nomedep = cursor.getString(cursor.getColumnIndex("nome_dep"));
                String sigladep = cursor.getString(cursor.getColumnIndex("sigla_dep"));
                spinnerDep.add("");
                spinnerDep.add(codigodep + " - " + sigladep + " - " + nomedep);
            }while (cursor.moveToNext());
        }
        //encerra o cursor
        cursor.close();

        //os dados do spinner
        String[] todosSpinner = new String[spinnerDep.size()];
        todosSpinner = spinnerDep.toArray(todosSpinner);

        // retorna os dados para o spinner
        return todosSpinner;
    }
}
