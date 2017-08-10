package com.example.juarez.celula.MembersPkg;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class  AddMember extends AppCompatActivity {

    ConnectionClass connection;
    AlertDialog alert;
    private SimpleDateFormat dateFormatter;
    int memberCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_add_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connection = new ConnectionClass();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        memberCode = 0;

        EditText celula = (EditText)findViewById(R.id.addMemberCelulaEditText);
        celula.setText(MyActivity.CelName());
        celula.setEnabled(false);

        if(MembrosCelulas.memberAnotherCel.length() != 0)FillFieldsWithOption(MembrosCelulas.memberAnotherCel);

        //SetAutoCompleteName();
        SetAutoCompleteDisc();
    }

    private void SetAutoCompleteName() {
        String query = "Select nome from Membros where celula='0' and igreja='"+MyActivity.Igreja()+"'";
        final AutoCompleteTextView name = (AutoCompleteTextView) findViewById(R.id.addMemberNameEditText);

        final ArrayList<String> nomes = connection.SimpleQuery(query);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        name.setAdapter(adapter);

        name.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView textView = (TextView) arg1;
                String nome = textView.getText().toString();
                FillFieldsWithOption(nome);
                name.dismissDropDown();
            }
        });

    }

    private void SetAutoCompleteDisc() {
        String query = "Select nome from Membros where igreja='"+MyActivity.Igreja()+"'";
        AutoCompleteTextView disc = (AutoCompleteTextView) findViewById(R.id.addMemberDiscEditText);

        ArrayList<String> nomes = connection.SimpleQuery(query);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        disc.setAdapter(adapter);
    }

    public void AddMemberSetDateDialog(View view) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EditText date = (EditText) findViewById(R.id.addMemberNiverEditText);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();
    }

    public void AddMemberSetCityDialog(View view) {
        final EditText city = (EditText) findViewById(R.id.addMemberCidadeEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
        .hideSoftInputFromWindow(city.getWindowToken(), 0);

        String query = "Select nome from cidades";
        ArrayList<String> citys = connection.SimpleQuery(query);

        final CharSequence[] items = new CharSequence[citys.size()];

        int count = citys.size();
        for (int i = 0; i < count; i++) {
            items[i] = citys.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cidade:");
        builder.setSingleChoiceItems(items, -1,
            new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                city.setText(items[arg1]);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                city.setText("");
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void AddMemberSetUFDialog(View view) {
        final EditText uf = (EditText) findViewById(R.id.addMemberUFEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(uf.getWindowToken(), 0);

        String query = "Select sigla from estados";
        ArrayList<String> states = connection.SimpleQuery(query);

        final CharSequence[] items = new CharSequence[states.size()];

        int count = states.size();
        for (int i = 0; i < count; i++) {
            items[i] = states.get(i);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estado:");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        uf.setText(items[arg1]);
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                uf.setText("");
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void AddMemberSetOpDialog(View view) {
        final EditText op = (EditText) findViewById(R.id.addMemberOpEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(op.getWindowToken(), 0);

        final CharSequence[] items = new CharSequence[]{"Vivo","Claro","Oi","Tim","Nextel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Operadora:");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        op.setText(items[arg1]);
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                op.setText("");
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void AddMemberSetSexoDialog(View view) {
        final EditText sex = (EditText) findViewById(R.id.addMemberSexoEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(sex.getWindowToken(), 0);

        final CharSequence[] items = new CharSequence[]{"M","F"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sexo:");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        sex.setText(items[arg1]);
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                sex.setText("");
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void AddMemberSetEstCivilDialog(View view) {
        final EditText estCivil = (EditText) findViewById(R.id.addMemberEstCivilEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(estCivil.getWindowToken(), 0);

        final CharSequence[] items = new CharSequence[]{"Casado(a)","Solteiro(a)","Viúvo(a)","Divorciado(a)","outros"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estado Civil:");
        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        estCivil.setText(items[arg1]);
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                estCivil.setText("");
            }
        });
        alert = builder.create();
        alert.show();
    }

    public void AddMemberSaveButton(View view){

        AutoCompleteTextView nome = (AutoCompleteTextView)findViewById(R.id.addMemberNameEditText);
        AutoCompleteTextView discipulador = (AutoCompleteTextView)findViewById(R.id.addMemberDiscEditText);
        EditText aniversario = (EditText)findViewById(R.id.addMemberNiverEditText);
        EditText rua = (EditText)findViewById(R.id.addMemberRuaEditText);
        EditText num = (EditText)findViewById(R.id.addMemberNumberEditText);
        EditText complemento = (EditText)findViewById(R.id.addMemberCompEditText);
        EditText bairro = (EditText)findViewById(R.id.addMemberBairroEditText);
        EditText cep = (EditText)findViewById(R.id.addMemberCEPEditText);
        EditText cidade = (EditText)findViewById(R.id.addMemberCidadeEditText);
        EditText uf = (EditText)findViewById(R.id.addMemberUFEditText);
        EditText celular = (EditText)findViewById(R.id.addMemberCelularEditText);
        EditText operadora = (EditText)findViewById(R.id.addMemberOpEditText);
        EditText sexo = (EditText)findViewById(R.id.addMemberSexoEditText);
        EditText email = (EditText)findViewById(R.id.addMemberEmailEditText);
        EditText estadocivil = (EditText)findViewById(R.id.addMemberEstCivilEditText);

        ArrayList<String> data = new ArrayList<>(20);

        data.add(nome.getText().toString());
        if (discipulador.getText().toString().equals("")) {
            data.add("0");
        } else {
            data.add(getDiscipCode(discipulador.getText().toString()));
        }
        if (aniversario.getText().toString().equals("")){data.add("");}
        else {data.add(FormatDateForDB(aniversario.getText().toString()));}
        data.add(rua.getText().toString());
        data.add(num.getText().toString());
        data.add(complemento.getText().toString());
        data.add(bairro.getText().toString());
        data.add(cep.getText().toString());
        data.add(cidade.getText().toString());
        data.add(uf.getText().toString());
        data.add(celular.getText().toString());
        data.add(operadora.getText().toString());
        data.add(sexo.getText().toString());
        data.add(email.getText().toString());
        data.add(estadocivil.getText().toString());
        data.add(Integer.toString(MyActivity.CodCel()));

        String query;

        if (memberCode != 0){
            query = "update Membros set Nome='" + data.get(0) + "'," +
            "Discipulador= '" + data.get(1) + "'," +
                    "Aniversario= '" + data.get(2) + "'," +
                    "Rua= '" + data.get(3) + "'," +
                    "Num= '" + data.get(4)  + "'," +
                    "Complemento= '" + data.get(5)  + "'," +
                    "Bairro= '" + data.get(6)  + "'," +
                    "CEP= '" + data.get(7)  + "'," +
                    "Cidade= '" + data.get(8)  + "'," +
                    "UF= '" + data.get(9)  + "'," +
                    "Celular= '" + data.get(10)  + "'," +
                    "Operadora= '" + data.get(11)  + "'," +
                    "Sexo= '" + data.get(12) + "'," +
                    "Email= '" + data.get(13) + "'," +
                    "EstadoCivil= '" + data.get(14)  + "'," +
                    "Celula= '"+ MyActivity.CodCel()  +" ' where codigo='" + memberCode +"'";

            connection.ExecuteQuery(query);
        }
        else {
             query = "insert into Membros values ('" + data.get(0)
                    + "', '" + data.get(1) +
                    "','" + data.get(2) +
                    "','" + data.get(3) +
                    "','" + data.get(4) +
                    "','" + data.get(5) +
                    "','" + data.get(6) +
                    "','" + data.get(7) +
                    "','" + data.get(8) +
                    "','" + data.get(9) +
                    "','','" +
                    "','" + data.get(10) +
                    "','" + data.get(11) +
                    "','','','','','" +
                    "','" + data.get(12) +
                    "','" + data.get(13) +
                    "','" + data.get(14) +
                    "','" + MyActivity.Igreja() +
                    "','','','','','" + MyActivity.CodCel() +
                    "','','')";
            connection.ExecuteQuery(query);
            query = "Select codigo from membros where nome='"+data.get(0)+"'";
            ArrayList<String> result = connection.SimpleQuery(query);
            query = "insert into EscadaSucesso values ('"+result.get(0)+"',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ')";
            connection.ExecuteQuery(query);

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Adiciona membro de célula");
        builder.setMessage("Novo membro adicionado na célula.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        alert = builder.create();
        alert.show();

    }

    private String FormatDateForDB(String date) {
        String formattedDate;
        String ano = date.substring(6, 10);
        String mes = date.substring(3, 5);
        String dia = date.substring(0, 2);

        formattedDate = mes + "-" + dia + "-" + ano;

        return formattedDate;
    }

    private String getDiscipCode(String disc) {
        String query = "select Codigo from membros where Nome='"+disc+"'";
        ArrayList<String> discipulador = connection.SimpleQuery(query);
        return discipulador.get(0);
    }

    private String getDiscipName (String disc) {
        String query = "select Nome from membros where codigo='"+disc+"'";
        ArrayList<String> discipulador = connection.SimpleQuery(query);
        return discipulador.get(0);
    }

    private void FillFieldsWithOption(String option){
        AutoCompleteTextView nome = (AutoCompleteTextView)findViewById(R.id.addMemberNameEditText);
        AutoCompleteTextView discipulador = (AutoCompleteTextView)findViewById(R.id.addMemberDiscEditText);
        EditText aniversario = (EditText)findViewById(R.id.addMemberNiverEditText);
        EditText rua = (EditText)findViewById(R.id.addMemberRuaEditText);
        EditText num = (EditText)findViewById(R.id.addMemberNumberEditText);
        EditText complemento = (EditText)findViewById(R.id.addMemberCompEditText);
        EditText bairro = (EditText)findViewById(R.id.addMemberBairroEditText);
        EditText cep = (EditText)findViewById(R.id.addMemberCEPEditText);
        EditText cidade = (EditText)findViewById(R.id.addMemberCidadeEditText);
        EditText uf = (EditText)findViewById(R.id.addMemberUFEditText);
        EditText celular = (EditText)findViewById(R.id.addMemberCelularEditText);
        EditText operadora = (EditText)findViewById(R.id.addMemberOpEditText);
        EditText sexo = (EditText)findViewById(R.id.addMemberSexoEditText);
        EditText email = (EditText)findViewById(R.id.addMemberEmailEditText);
        EditText estadocivil = (EditText)findViewById(R.id.addMemberEstCivilEditText);

        String query = "Select * from membros where nome='"+option+"' and celula='0' and igreja='"+MyActivity.Igreja()+"'";
        ArrayList<String> membro = connection.SimpleQuery(query);

        memberCode = Integer.parseInt(membro.get(0));
        nome.setText(membro.get(1).toString());
        discipulador.setText(getDiscipName(membro.get(2).toString()));
        aniversario.setText(FormatDate(membro.get(3).toString()));
        rua.setText(membro.get(4).toString());
        num.setText(membro.get(5).toString());
        complemento.setText(membro.get(6).toString());
        bairro.setText(membro.get(7).toString());
        cep.setText(membro.get(8).toString());
        cidade.setText(membro.get(9).toString());
        uf.setText(membro.get(10).toString());
        celular.setText(membro.get(13).toString());
        operadora.setText(membro.get(14).toString());
        sexo.setText(membro.get(20).toString());
        email.setText(membro.get(21).toString());
        estadocivil.setText(membro.get(22).toString());

    }

    private String FormatDate(String date){
        String formattedDate;
        String ano = date.substring(0,4);
        String mes = date.substring(5,7);
        String dia = date.substring(8,10);

        formattedDate = dia+"/"+mes+"/"+ano;

        return formattedDate;
    }

    public void AddMemberClearButton(View view){

        AutoCompleteTextView nome = (AutoCompleteTextView)findViewById(R.id.addMemberNameEditText);
        AutoCompleteTextView discipulador = (AutoCompleteTextView)findViewById(R.id.addMemberDiscEditText);
        EditText aniversario = (EditText)findViewById(R.id.addMemberNiverEditText);
        EditText rua = (EditText)findViewById(R.id.addMemberRuaEditText);
        EditText num = (EditText)findViewById(R.id.addMemberNumberEditText);
        EditText complemento = (EditText)findViewById(R.id.addMemberCompEditText);
        EditText bairro = (EditText)findViewById(R.id.addMemberBairroEditText);
        EditText cep = (EditText)findViewById(R.id.addMemberCEPEditText);
        EditText cidade = (EditText)findViewById(R.id.addMemberCidadeEditText);
        EditText uf = (EditText)findViewById(R.id.addMemberUFEditText);
        EditText celular = (EditText)findViewById(R.id.addMemberCelularEditText);
        EditText operadora = (EditText)findViewById(R.id.addMemberOpEditText);
        EditText sexo = (EditText)findViewById(R.id.addMemberSexoEditText);
        EditText email = (EditText)findViewById(R.id.addMemberEmailEditText);
        EditText estadocivil = (EditText)findViewById(R.id.addMemberEstCivilEditText);

        nome.setText("");
        discipulador.setText("");
        aniversario.setText("");
        rua.setText("");
        num.setText("");
        complemento.setText("");
        bairro.setText("");
        cep.setText("");
        cidade.setText("");
        uf.setText("");
        celular.setText("");
        operadora.setText("");
        sexo.setText("");
        email.setText("");
        estadocivil.setText("");
    }
}
