package com.example.projetoevosystems.Uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BdDepartamento extends SQLiteOpenHelper {


    private static final String DATABASE = "BDDEP.db";
    private static final int DATABASE_VERSION = 1;


    public BdDepartamento(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String stringBuilderCreateTableDep = " CREATE TABLE Tabela_dep ( " +
                "    id_dep    INTEGER  PRIMARY KEY AUTOINCREMENT " +
                "                       NOT NULL, " +
                "    nome_dep  TEXT     NOT NULL, " +
                "    sigla_dep CHAR (3) NOT NULL  " +
                ")";
        db.execSQL(stringBuilderCreateTableDep);

        String stringBuilderCreateTableFun = " CREATE TABLE Tabela_fun ( " +
                "    id_fun    INTEGER  PRIMARY KEY AUTOINCREMENT " +
                "                       NOT NULL, " +
                "    nome_fun  TEXT     NOT NULL, " +
                "    rg_fun    CHAR (9) NOT NULL " +
                "                       UNIQUE, " +
                "    id_dep_fk INTEGER  REFERENCES Tabela_dep (id_dep) " +
                "                       NOT NULL " +
                ")";
        db.execSQL(stringBuilderCreateTableFun);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS Tabela_dep ");
        db.execSQL(" DROP TABLE IF EXISTS Tabela_fun ");
        onCreate(db);
    }

    public void inserirLabel(String label){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_dep", label);

        db.insert("Tabela_dep",null,values);
        db.close();
    }

    public List<String> getTodasLabels(){
        List<String> labels = new ArrayList<String>();

        String selecionarQuery = " SELECT * FROM Tabela_dep";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selecionarQuery,null);

        if (cursor.moveToFirst()){
            do {
                labels.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

}
