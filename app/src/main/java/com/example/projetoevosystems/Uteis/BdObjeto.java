package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BdObjeto {

    //classe responsavel apenas para conectar o banco de dados
    private static BdDepartamento dbHelper;
    private SQLiteDatabase db;

    public BdObjeto (Context context){
        dbHelper = new BdDepartamento(context);
        this.db = dbHelper.getReadableDatabase();
    }

    //começa conexão com o banco de dados
    public SQLiteDatabase getDbConnection(){
        return this.db;
    }

    //fecha conexão com o banco de dados
    public void closeDbConnection(){
        if (this.db != null){
            this.db.close();
        }
    }
}
