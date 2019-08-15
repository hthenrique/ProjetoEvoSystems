package com.example.projetoevosystems.Uteis;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projetoevosystems.R;

import java.util.ArrayList;

public class DepartamentoSpinnerAdapter extends ArrayAdapter<DepartamentoDAO> {

    private ArrayList<DepartamentoDAO> spinnerDep;

    public DepartamentoSpinnerAdapter(Context context, ArrayList<DepartamentoDAO> departamentos) {
        super(context, 0, departamentos);
        this.spinnerDep = departamentos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DepartamentoDAO departamentoDAO =  spinnerDep.get(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_list,null);

        TextView textViewID_fk = (TextView)convertView.findViewById(R.id.textViewID_fk);
        TextView textViewSiglaSpinner = (TextView)convertView.findViewById(R.id.textViewSiglaSpinner);
        TextView textViewDepDpinner = (TextView)convertView.findViewById(R.id.textViewDepSpinner);

        textViewID_fk.setText(departamentoDAO.getId_dep()+"");
        textViewSiglaSpinner.setText(departamentoDAO.getSigla_dep().toString());
        textViewDepDpinner.setText(departamentoDAO.getNome_dep().toString());

        return convertView;
    }
}
