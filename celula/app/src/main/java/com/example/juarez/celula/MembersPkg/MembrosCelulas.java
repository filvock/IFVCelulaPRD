package com.example.juarez.celula.MembersPkg;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;

import java.util.HashMap;
import java.util.List;

public class MembrosCelulas extends AppCompatActivity {

    static int memberClicked = 0;
    ListView list;
    static SimpleAdapter adapter;
    static String memberAnotherCel;

    ConnectionClass connection;
    AlertDialog alert;
    CharSequence item;
    CharSequence itemToChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_cells_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = (ListView) findViewById(R.id.listView);
        FillMembersList();
        connection = new ConnectionClass();
        memberAnotherCel = "";


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = String.valueOf(parent.getItemAtPosition(position));

                SetMemberCode(item);
                Intent memberDetails = new Intent(MembrosCelulas.this, MemberDetails.class);
                startActivity(memberDetails);
            }
        });

    }

    public void FillMembersList(){
        ConnectionClass connection = new ConnectionClass();
        List<HashMap<String, String>> data = connection.QueryMembrosCel();

        final CharSequence[] items = new CharSequence[data.size()];

        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.divs_list_display,new String[] { "Code", "Name" },
                new int[] { R.id.listcode, R.id.listname });

        list.setAdapter(adapter);

    }


    public static int MemberClicked(){
        return memberClicked;
    }

    private void SetMemberCode(String item){

        String str = item.replaceAll("\\D+", "");
        memberClicked = Integer.parseInt(str);

    }

    public void AddNewMemberButton(View view){
        final CharSequence[] items = new CharSequence[]{"Membro vindo de outra célula", "Novo Membro"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma Opção:");
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                item = items[arg1];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (item.toString().equals("Membro vindo de outra célula")) {
                    FillNoCellMembersList();
                } else{
                    CallAddMember();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alert = builder.create();
        alert.show();

    }
    private void CallAddMember(){
        Intent newMember = new Intent (this, AddMember.class);
        startActivity(newMember);
    }

    private void FillNoCellMembersList(){

        List<HashMap<String, String>> data = connection.QueryMembrosNoCel();
        final CharSequence[] items = new CharSequence[data.size()];
        final CharSequence[] codes = new CharSequence[data.size()];

        HashMap<String, String> aux;

        for (int i=0; i<data.size(); i++){
            aux = data.get(i);
            items[i] = aux.get("Name");
            codes[i] = aux.get("Code");
        }

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setTitle("Membro Adicionado!");
        builder1.setMessage("Membro adicionado na célula com sucesso.");
        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha um membro:");
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                itemToChange = codes[arg1];
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String query = "Update membros set Celula='"+MyActivity.CodCel()+"' where Codigo='"+itemToChange+"'";
                connection.ExecuteQuery(query);

                alert = builder1.create();
                alert.show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        alert = builder.create();
        alert.show();

    }
}
