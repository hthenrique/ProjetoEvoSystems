package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BdObjeto {

    private static BdDepartamento dbHelper;
    private SQLiteDatabase db;

    public BdObjeto (Context context){
        dbHelper = new BdDepartamento(context);
        this.db = dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getDbConnection(){
        return this.db;
    }
    public void closeDbConnection(){
        if (this.db != null){
            this.db.close();
        }
    }
}
