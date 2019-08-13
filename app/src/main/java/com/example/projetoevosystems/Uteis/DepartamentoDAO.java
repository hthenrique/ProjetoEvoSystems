package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DepartamentoDAO {

    private int id_dep;
    private String  nome_dep;
    private String  sigla_dep;
    private Context context;
    private boolean excluir;

    public DepartamentoDAO(Context context){
        this.context = context;
        id_dep = -1;
    }

    public int getId_dep(){
        return id_dep;
    }

    public String getNome_dep(){
        return nome_dep;
    }

    public void setNome_dep(String nome_dep){
        this.nome_dep = nome_dep;
    }

    public String getSigla_dep(){
        return sigla_dep;
    }

    public void setSigla_dep(String sigla_dep){
        this.sigla_dep = sigla_dep;
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public boolean excluirDep(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;

        try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.delete("Tabela_dep", "id_dep = ?",new String[]{String.valueOf(id_dep)});

            excluir = true;

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

    public boolean salvarDep(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;

        try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getWritableDatabase();
            String sql = "";
            if (id_dep == -1){
                sql = "INSERT INTO Tabela_dep (nome_dep,sigla_dep) VALUES (?,?)";
            }else {
                sql = "UPDATE Tabela_dep SET nome_dep = ?, sigla_dep = ? WHERE id_dep = ?";
            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1,nome_dep);
            sqLiteStatement.bindString(2,sigla_dep);
            if (id_dep != -1)
            sqLiteStatement.bindString(3,String.valueOf(id_dep));
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

    public ArrayList<DepartamentoDAO> getDepartamentos(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<DepartamentoDAO> departamentos = new ArrayList<>();

        try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getReadableDatabase();
            cursor = sqLiteDatabase.query("Tabela_dep",null, null,null,null,null,null);
            while (cursor.moveToNext()){
                DepartamentoDAO departamentoDAO = new DepartamentoDAO(context);
                departamentoDAO.id_dep = cursor.getInt(cursor.getColumnIndex("id_dep"));
                departamentoDAO.nome_dep = cursor.getString(cursor.getColumnIndex("dep_nome"));
                departamentoDAO.sigla_dep = cursor.getString(cursor.getColumnIndex("sigla_dep"));
                departamentos.add(departamentoDAO);
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


        return departamentos;
    }

    public void carregaDepPeloId (int id_dep){

        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
         try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getReadableDatabase();
            cursor = sqLiteDatabase.query("Tabela_dep",null, "id_dep = ?",new String[]{String.valueOf(id_dep)},null,null,null);
            excluir = true;
            while (cursor.moveToNext()){
                this.id_dep = cursor.getInt(cursor.getColumnIndex("id_dep"));
                nome_dep = cursor.getString(cursor.getColumnIndex("dep_nome"));
                sigla_dep = cursor.getString(cursor.getColumnIndex("sigla_dep"));
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
