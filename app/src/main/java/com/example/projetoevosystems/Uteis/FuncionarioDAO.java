package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

public class FuncionarioDAO {


    private int id_fun;
    private String nome_fun;
    private String rg_fun;
    private int id_dep_fk;
    private Context context;
    private boolean excluir;

    public FuncionarioDAO(Context context){
        this.context = context;
        id_fun = -1;
    }

    public int getId_fun() {
        return id_fun;
    }

    public String getNome_fun() {
        return nome_fun;
    }

    public void setNome_fun(String nome_fun) {
        this.nome_fun = nome_fun;
    }

    public String getRg_fun() {
        return rg_fun;
    }

    public void setRg_fun(String rg_fun) {
        this.rg_fun = rg_fun;
    }

    public int getId_dep_fk() {
        return id_dep_fk;
    }

    public void setId_dep_fk(int id_dep_fk) {
        this.id_dep_fk = id_dep_fk;
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public boolean excluirFun(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;

        try {
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.delete("Tabela_fun", "id_fun = ?",new String[]{String.valueOf(id_fun)});
            excluir = true;

            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return true;
        }finally {
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }
    }

    public boolean salvarFun(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;

        try {
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getWritableDatabase();
            String sql = "";
            if (id_fun == -1){
                sql = "INSERT INTO Tabela_fun (nome_fun,rg_fun,id_dep_fk) VALUES (?,?,?)";
            }else {
                sql = "UPDATE Tabela_fun SET nome_fun = ?,rg_fun = ?,id_dep_fk = ? WHERE id_fun = ?";
            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1,nome_fun);
            sqLiteStatement.bindString(2,rg_fun);
            sqLiteStatement.bindString(3, String.valueOf(id_dep_fk));
            if (id_fun != -1)
            sqLiteStatement.bindString(4, String.valueOf(id_fun));
            sqLiteStatement.executeInsert();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }
    }

    public ArrayList<FuncionarioDAO> getFuncionarios(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<FuncionarioDAO> funcionarios = new ArrayList<>();

        try {
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getReadableDatabase();
            cursor = sqLiteDatabase.query("Tabela_fun",null,null,null,null,null,null);
            while (cursor.moveToNext()){
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO(context);
                funcionarioDAO.id_fun = cursor.getInt(cursor.getColumnIndex("id_fun"));
                funcionarioDAO.nome_fun = cursor.getString(cursor.getColumnIndex("nome_fun"));
                funcionarioDAO.rg_fun = cursor.getString(cursor.getColumnIndex("rg_fun"));
                funcionarios.add(funcionarioDAO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }
        return funcionarios;
    }

    public void carregaFunPeloId (int id_fun){

        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        try {
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getReadableDatabase();
            cursor = sqLiteDatabase.query("Tabela_fun",null,"id_fun = ?",new String[]{String.valueOf(id_fun)},null,null,null);
            excluir = true;
            while (cursor.moveToNext()){
                this.id_fun = cursor.getInt(cursor.getColumnIndex("id_fun"));
                nome_fun = cursor.getString(cursor.getColumnIndex("nome_fun"));
                rg_fun = cursor.getString(cursor.getColumnIndex("rg_fun"));
                excluir = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }
    }


}
