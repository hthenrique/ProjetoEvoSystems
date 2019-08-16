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

    //metodo responsavel em excluir departamentos
    public boolean excluirDep(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;

        try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getWritableDatabase();
            //começa a o processo no banco de dados
            sqLiteDatabase.beginTransaction();
            //exclui o departamento pelo id
            sqLiteDatabase.delete("Tabela_dep", "id_dep = ?",new String[]{String.valueOf(id_dep)});
            excluir = true;
            //termina o processo no banco de dados
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            //fecha o banco de dados
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }

    }

    //metodo responsavel pelo departamento
    public boolean salvarDep(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getWritableDatabase();
            String sql = "";
            //insere um novo departamento
            if (id_dep == -1){
                sql = "INSERT INTO Tabela_dep (nome_dep,sigla_dep) VALUES (?,?)";
            }
            //edita o departamento ja existente
            else {
                sql = "UPDATE Tabela_dep SET nome_dep = ?, sigla_dep = ? WHERE id_dep = ?";
            }
            //liga os campos digitados com as colunas do banco de dados
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1,nome_dep);
            sqLiteStatement.bindString(2,sigla_dep);
            if (id_dep != -1)
            sqLiteStatement.bindString(3,String.valueOf(id_dep));
            //executa a inserção de dados
            sqLiteStatement.executeInsert();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            //termina a transação
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            //fecha o banco de dados aberto
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }

    }

    //metodo para listar departamentos
    public ArrayList<DepartamentoDAO> getDepartamentos(){
        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<DepartamentoDAO> departamentos = new ArrayList<>();

        try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getReadableDatabase();
            //cursor verifica todos os dados da tabela
            cursor = sqLiteDatabase.query("Tabela_dep",null, null,null,null,null,null);
            //cursor passa todos os dados das colunas enquanto tiver dados
            while (cursor.moveToNext()){
                DepartamentoDAO departamentoDAO = new DepartamentoDAO(context);
                departamentoDAO.id_dep = cursor.getInt(cursor.getColumnIndex("id_dep"));
                departamentoDAO.nome_dep = cursor.getString(cursor.getColumnIndex("nome_dep"));
                departamentoDAO.sigla_dep = cursor.getString(cursor.getColumnIndex("sigla_dep"));
                departamentos.add(departamentoDAO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //fecha o banco de dados e encerra o cursor
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }
        //retorna os departamentos
        return departamentos;
    }

    //carrega departamentos pelo id
    public void carregaDepPeloId (int id_dep){

        BdDepartamento bdDepartamento = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
         try{
            bdDepartamento = new BdDepartamento(context);
            sqLiteDatabase = bdDepartamento.getReadableDatabase();
            //cursor verifica id's da tabela selecionada
            cursor = sqLiteDatabase.query("Tabela_dep",null, "id_dep = ?",new String[]{String.valueOf(id_dep)},null,null,null);
            excluir = true;
            //enquanto tiver dados o cursor irá passar
            while (cursor.moveToNext()){
                this.id_dep = cursor.getInt(cursor.getColumnIndex("id_dep"));
                nome_dep = cursor.getString(cursor.getColumnIndex("nome_dep"));
                sigla_dep = cursor.getString(cursor.getColumnIndex("sigla_dep"));
                excluir = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
             //encerra o processo do cursor e fecha o banco de dados
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (bdDepartamento != null)
                bdDepartamento.close();
        }

    }


}
