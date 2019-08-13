package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projetoevosystems.R;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoAdapter extends ArrayAdapter<DepartamentoDAO> {

    private ArrayList<DepartamentoDAO> departamentos;

    public DepartamentoAdapter(@NonNull Context context, @NonNull ArrayList<DepartamentoDAO> departamentos) {
        super(context, 0, departamentos);
        this.departamentos = departamentos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DepartamentoDAO departamentoDAO = departamentos.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_linha_consulta,null);

        TextView textViewCodigodep = (TextView)convertView.findViewById(R.id.textViewCodigodep);
        TextView textViewNomedep = (TextView)convertView.findViewById(R.id.textViewNomedep);
        TextView textViewSigladep = (TextView)convertView.findViewById(R.id.textViewSigladep);


        //Retorna dados do banco na lista
        textViewCodigodep.setText(departamentoDAO.getId_dep()+"");
        textViewNomedep.setText(departamentoDAO.getNome_dep().toString());
        textViewSigladep.setText(departamentoDAO.getSigla_dep().toString());

        return convertView;
    }
}
