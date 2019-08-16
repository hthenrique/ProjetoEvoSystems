package com.example.projetoevosystems.Uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BdDepartamento extends SQLiteOpenHelper {

    //nome do banco de dados
    private static final String DATABASE = "BDDEP.db";
    //versão do banco de dados
    private static final int DATABASE_VERSION = 1;


    public BdDepartamento(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //cria a tabela de departamentos
        String stringBuilderCreateTableDep = " CREATE TABLE Tabela_dep ( " +
                "    id_dep    INTEGER  PRIMARY KEY AUTOINCREMENT " +
                "                       NOT NULL, " +
                "    nome_dep  TEXT     NOT NULL, " +
                "    sigla_dep CHAR (3) NOT NULL  " +
                ")";
        db.execSQL(stringBuilderCreateTableDep);

        //cria a tabela de departamentos
        String stringBuilderCreateTableFun = " CREATE TABLE Tabela_fun ( " +
                "    id_fun    INTEGER  PRIMARY KEY AUTOINCREMENT " +
                "                       NOT NULL, " +
                "    nome_fun  TEXT     NOT NULL, " +
                "    rg_fun    CHAR (9) NOT NULL " +
                "                       UNIQUE, " +
                "    id_dep_fk TEXT  REFERENCES Tabela_dep (id_dep) " +
                "                       NOT NULL " +
                ")";
        db.execSQL(stringBuilderCreateTableFun);

    }

    //exclui a tabela se ela ja existe
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS Tabela_dep ");
        db.execSQL(" DROP TABLE IF EXISTS Tabela_fun ");
        onCreate(db);
    }

}
