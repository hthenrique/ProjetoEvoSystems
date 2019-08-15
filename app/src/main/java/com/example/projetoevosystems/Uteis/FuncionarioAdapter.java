package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projetoevosystems.R;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioAdapter extends ArrayAdapter<FuncionarioDAO> {

    private ArrayList<FuncionarioDAO> funcionarios;

    public FuncionarioAdapter(Context context, ArrayList<FuncionarioDAO> funcionarios) {
        super(context, 0, funcionarios);
        this.funcionarios = funcionarios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FuncionarioDAO funcionarioDAO = funcionarios.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_linha_consulta_fun,null);

        TextView textViewCodigoFun = (TextView)convertView.findViewById(R.id.textViewCodigoFun);
        TextView textViewNomeFun = (TextView)convertView.findViewById(R.id.textViewNomeFun);
        TextView textViewRG2 = (TextView)convertView.findViewById(R.id.textViewRG2);
        TextView textViewIDDEP = (TextView)convertView.findViewById(R.id.textViewIDDEP);

        textViewCodigoFun.setText(funcionarioDAO.getId_fun()+"");
        textViewNomeFun.setText(funcionarioDAO.getNome_fun().toString());
        textViewRG2.setText(funcionarioDAO.getRg_fun().toString());
        textViewIDDEP.setText(funcionarioDAO.getId_dep_fk().toString());

        return convertView;
    }
}
