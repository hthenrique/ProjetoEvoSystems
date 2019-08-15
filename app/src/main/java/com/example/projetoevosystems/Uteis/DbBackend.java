package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends BdObjeto {
    public DbBackend(Context context) {
        super(context);
    }
    public String[] getTodosSpinner(){
        String query = "SELECT * FROM Tabela_dep";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> spinnerDep = new ArrayList<String>();
        if (cursor.moveToFirst()){
            do{
                String codigodep = cursor.getString(cursor.getColumnIndex("id_dep"));
                String nomedep = cursor.getString(cursor.getColumnIndex("nome_dep"));
                String sigladep = cursor.getString(cursor.getColumnIndex("sigla_dep"));
                spinnerDep.add(codigodep + " - " + sigladep + " - " + nomedep);
            }while (cursor.moveToNext());
        }
        cursor.close();

        String[] todosSpinner = new String[spinnerDep.size()];
        todosSpinner = spinnerDep.toArray(todosSpinner);

        return todosSpinner;
    }
}
