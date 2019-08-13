package com.example.projetoevosystems.Uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BdDepartamento extends SQLiteOpenHelper {

    public static final String TABELA_DEP = "Tabela_dep";
    public static final String ID_DEP = "id_dep";
    public static final String NOME_DEP = "nome_dep";
    public static final String SIGLA_DEP = "sigla_dep";

    //Campo Funcionarios

    public static final String TABELA_FUN = "Funcionario";
    public static final String ID_FUN = "id_fun";
    public static final String NOME_FUN = "nome";
    public static final String FUN_RG = "rg";
    public static final String ID_DEP_FK = "id_dep";


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
                "    sigla_dep CHAR (3) NOT NULL " +
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


}
