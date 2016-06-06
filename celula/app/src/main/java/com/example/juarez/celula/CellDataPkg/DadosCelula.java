package com.example.juarez.celula.CellDataPkg;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;
import com.example.juarez.celula.ToolsPkg.ConnectionClass;

import java.util.ArrayList;

public class DadosCelula extends AppCompatActivity {

    ArrayList<String> dadosCelula;
    ConnectionClass connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cell_data_activity);
        connection = new ConnectionClass();
        LoadFields();
    }

    private void LoadFields() {
        Spinner dia = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dias_semana, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dia.setAdapter(adapter);

        String query = "Select membros.nome as nomeResp, * from  Celulas \n" +
                "join membros on membros.codigo=celulas.responsavel\n" +
                "where celulas.Codigo like '" + MyActivity.CodCel() + "'";
        dadosCelula = connection.SimpleQuery(query);

        EditText nome = (EditText)findViewById(R.id.editTextNome);
        EditText cidade = (EditText)findViewById(R.id.editTextCidade);
        EditText rua = (EditText)findViewById(R.id.editTextRua);
        EditText numero = (EditText)findViewById(R.id.editTextNumero);
        EditText horario = (EditText)findViewById(R.id.editTextHorario);
        EditText lider = (EditText)findViewById(R.id.editTextLider);

        lider.setText(dadosCelula.get(0));
        nome.setText(dadosCelula.get(2));
        cidade.setText(dadosCelula.get(16));
        rua.setText(dadosCelula.get(14));
        numero.setText(dadosCelula.get(15));
        horario.setText(dadosCelula.get(18));

        String diaSemana =  dadosCelula.get(17).toString();

        if (diaSemana.equals("Domingo")) {
            dia.setSelection(6);
        }
        else if(diaSemana.equals("Segunda")) {
            dia.setSelection(0);
        }
        else if(diaSemana.equals("Terça")) {
            dia.setSelection(1);
        }
        else if(diaSemana.equals("Quarta")) {
            dia.setSelection(2);
        }
        else if(diaSemana.equals("Quinta")) {
            dia.setSelection(3);
        }
        else if(diaSemana.equals("Sexta")) {
            dia.setSelection(4);
        }
        else if(diaSemana.equals("Sábado")) {
            dia.setSelection(5);
        }

        dia.setEnabled(false);
    }

    public void EditFields(View view){

        EditText rua = (EditText)findViewById(R.id.editTextRua);
        EditText numero = (EditText)findViewById(R.id.editTextNumero);
        EditText horario = (EditText)findViewById(R.id.editTextHorario);
        Spinner dia = (Spinner)findViewById(R.id.spinner);

        rua.setEnabled(true);
        numero.setEnabled(true);
        horario.setEnabled(true);
        dia.setEnabled(true);

        Button editButton = (Button) findViewById(R.id.buttonEdit);
        editButton.setVisibility(View.INVISIBLE);

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setVisibility(View.VISIBLE);

    }

    public void SaveChanges(View view){

        ConnectionClass connection = new ConnectionClass();
        EditText rua = (EditText)findViewById(R.id.editTextRua);
        EditText numero = (EditText)findViewById(R.id.editTextNumero);
        EditText horario = (EditText)findViewById(R.id.editTextHorario);
        Spinner dia = (Spinner)findViewById(R.id.spinner);

        dadosCelula.set(13, rua.getText().toString());
        dadosCelula.set(14, numero.getText().toString());
        dadosCelula.set(17, horario.getText().toString());
        dadosCelula.set(16, dia.getSelectedItem().toString());

        String query = "update Celulas set Rua='" + dadosCelula.get(13) + "', numero='" + dadosCelula.get(14) + "',horario='" + dadosCelula.get(17) + "', diasemana='" + dadosCelula.get(16) + "' where codigo like '" + dadosCelula.get(0) + "'";
        connection.ExecuteQuery(query);

        Button editButton = (Button) findViewById(R.id.buttonEdit);
        editButton.setVisibility(View.VISIBLE);

        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setVisibility(View.INVISIBLE);

        rua.setEnabled(false);
        numero.setEnabled(false);
        horario.setEnabled(false);
        dia.setEnabled(false);

    }




}
