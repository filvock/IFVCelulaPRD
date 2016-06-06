package com.example.juarez.celula.SuccessLaderPkg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;
import com.example.juarez.celula.ToolsPkg.ConnectionClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EscadaSucesso extends AppCompatActivity {
    static int memberClicked = 0;
    AlertDialog alert;
    ConnectionClass connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_lacder_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connection = new ConnectionClass();

        ListView list = (ListView) findViewById(R.id.escadaListView);
        FillMembersList();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = String.valueOf(parent.getItemAtPosition(position));
                SetMemberCode(item);
                SetEscada(item);
            }
        });

    }

    private void SetMemberCode(String item){

        String str = item.replaceAll("\\D+", "");
        memberClicked = Integer.parseInt(str);

    }

    private void SetEscada(String item){
        final CharSequence[] items = new CharSequence[]{"Oração de Entrega","Decisão pública na Igreja","Discipulado","Pré-encontro","Encontro","Pós-encontro","Encontreiro","Batismo nas águas","Está matriculado na EM","É fiel nos dízimos e nas ofertas","casamento legalizado","TADEL","Tem M.D.A.2","TLC","Co-lider de célula"};
        final boolean[] checados = new boolean[items.length];

        String query = "select * from EscadaSucesso where codigo='"+memberClicked+"'";
        ArrayList<String> result = connection.SimpleQuery(query);

        int count = checados.length+1;
        for (int i = 1; i < count; i++) {
            if (result.get(i).equals("X")) checados[i-1]=true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(item.substring(item.indexOf("=")+1,item.indexOf(",")));
        builder.setMultiChoiceItems(items, checados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                checados[arg1] = arg2;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                AddEscada(items, checados);
            }
        });

        alert = builder.create();
        alert.show();

    }

    private void FillMembersList(){
        ListView list = (ListView) findViewById(R.id.escadaListView);
        List<HashMap<String, String>> data = connection.QueryMembrosCel();

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.divs_list_display,new String[] { "Code", "Name" },
                new int[] { R.id.listcode, R.id.listname });

        list.setAdapter(adapter);
    }

    public void EscadaButtonBack(View view){
        finish();
    }

    private void AddEscada(CharSequence[] items, boolean[] checados){
        String query;
        char[] values = new char[checados.length];


        int count = checados.length;
        for (int i = 0; i < count; i++) {
            if (checados[i]) values[i] = 'X';
            else values[i] = ' ';
        }

        query = "update EscadaSucesso set OracaoEntrega='"+values[0]+
                "',DecisaoIgreja='" +values[1]+
                "',Discipulado='" +values[2]+
                "',PreEncontro='" +values[3]+
                "',Encontro='" +values[4]+
                "',PosEncontro='" +values[5]+
                "',Encontreiro='" +values[6]+
                "',Batismo='" +values[7]+
                "',EM='" +values[8]+
                "',DizimosOfertas='" +values[9]+
                "',CasamentoLegal='" +values[10]+
                "',TADEL='" +values[11]+
                "',MDA2='" +values[12]+
                "',TLC='"+ values[13]+
                "',CoLider='"+values[14]+
                "' where codigo='"+memberClicked+"'";

            connection.ExecuteQuery(query);
    }

}
