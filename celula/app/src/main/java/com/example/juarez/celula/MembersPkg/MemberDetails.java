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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MemberDetails extends AppCompatActivity {

    ConnectionClass connection;
    AlertDialog alert;
    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_details_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connection = new ConnectionClass();
        FillDetails();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        EditText celula = (EditText) findViewById(R.id.memberDetailsCelulaEditText);
        celula.setEnabled(true);
        celula.setText(MyActivity.CelName().toString());
        celula.setEnabled(false);

    }

    @Override
    public void onRestart() {
        super.onRestart();
        FillDetails();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        EditText celula = (EditText) findViewById(R.id.memberDetailsCelulaEditText);
        celula.setEnabled(true);
        celula.setText(MyActivity.CelName().toString());
        celula.setEnabled(false);
    }

    public void FillDetails() {

        EditText nome = (EditText) findViewById(R.id.memberDetailsNameEditText);
        EditText aniversario = (EditText) findViewById(R.id.memberDetailsNiverEditText);
        EditText rua = (EditText) findViewById(R.id.memberDetailsRuaEditText);
        EditText num = (EditText) findViewById(R.id.memberDetailsNumEditText);
        EditText complemento = (EditText) findViewById(R.id.memberDetailsCompEditText);
        EditText bairro = (EditText) findViewById(R.id.memberDetailsBairroEditText);
        EditText cep = (EditText) findViewById(R.id.memberDetailsCEPEditText);
        EditText cidade = (EditText) findViewById(R.id.memberDetailsCidadeEditText);
        EditText uf = (EditText) findViewById(R.id.memberDetailsUFEditText);
        EditText celular = (EditText) findViewById(R.id.memberDetailsCelularEditText);
        EditText operadora = (EditText) findViewById(R.id.memberDetailsOpEditText);
        EditText sexo = (EditText) findViewById(R.id.memberDetailsSexEditText);
        EditText email = (EditText) findViewById(R.id.memberDetailsEmailEditText);
        EditText estadocivil = (EditText) findViewById(R.id.memberDetailsEstCivEditText);

        String query = "select * from membros where codigo='" + MembrosCelulas.MemberClicked() + "'";
        ArrayList<String> data = connection.SimpleQuery(query);

        nome.setText(data.get(1).toString());
        if (Integer.parseInt(data.get(2).toString()) == 0) {
            AutoCompleteTextView discipulador = (AutoCompleteTextView) findViewById(R.id.memberDetailsDiscEditText);
            discipulador.setText("0");
            discipulador.setEnabled(false);
        } else {
            setDiscipulador(data.get(2).toString());
        }
        aniversario.setText(FormatDate(data.get(3).toString()));
        rua.setText(data.get(4).toString());
        num.setText(data.get(5).toString());
        complemento.setText(data.get(6).toString());
        bairro.setText(data.get(7).toString());
        cep.setText(data.get(8).toString());
        cidade.setText(data.get(9).toString());
        uf.setText(data.get(10).toString());
        celular.setText(data.get(13).toString());
        operadora.setText(data.get(14).toString());
        sexo.setText(data.get(20).toString());
        email.setText(data.get(21).toString());
        estadocivil.setText(data.get(22).toString());

        nome.setEnabled(false);
        aniversario.setEnabled(false);
        rua.setEnabled(false);
        num.setEnabled(false);
        complemento.setEnabled(false);
        bairro.setEnabled(false);
        cep.setEnabled(false);
        cidade.setEnabled(false);
        uf.setEnabled(false);
        celular.setEnabled(false);
        operadora.setEnabled(false);
        sexo.setEnabled(false);
        email.setEnabled(false);
        estadocivil.setEnabled(false);

    }

    private void setDiscipulador(String discipCode) {

        AutoCompleteTextView discipulador = (AutoCompleteTextView) findViewById(R.id.memberDetailsDiscEditText);
        discipulador.setEnabled(false);

        String query = "select nome from membros where codigo='" + discipCode + "'";
        ArrayList<String> data = connection.SimpleQuery(query);
        discipulador.setText(data.get(0));

    }

    public void MemberDetailsEditButton(View view) {
        EditText nome = (EditText) findViewById(R.id.memberDetailsNameEditText);
        AutoCompleteTextView discipulador = (AutoCompleteTextView) findViewById(R.id.memberDetailsDiscEditText);
        EditText aniversario = (EditText) findViewById(R.id.memberDetailsNiverEditText);
        EditText rua = (EditText) findViewById(R.id.memberDetailsRuaEditText);
        EditText num = (EditText) findViewById(R.id.memberDetailsNumEditText);
        EditText complemento = (EditText) findViewById(R.id.memberDetailsCompEditText);
        EditText bairro = (EditText) findViewById(R.id.memberDetailsBairroEditText);
        EditText cep = (EditText) findViewById(R.id.memberDetailsCEPEditText);
        EditText cidade = (EditText) findViewById(R.id.memberDetailsCidadeEditText);
        EditText uf = (EditText) findViewById(R.id.memberDetailsUFEditText);
        EditText celular = (EditText) findViewById(R.id.memberDetailsCelularEditText);
        EditText operadora = (EditText) findViewById(R.id.memberDetailsOpEditText);
        EditText sexo = (EditText) findViewById(R.id.memberDetailsSexEditText);
        EditText email = (EditText) findViewById(R.id.memberDetailsEmailEditText);
        EditText estadocivil = (EditText) findViewById(R.id.memberDetailsEstCivEditText);


        nome.setEnabled(true);
        discipulador.setEnabled(true);
        aniversario.setEnabled(true);
        rua.setEnabled(true);
        num.setEnabled(true);
        complemento.setEnabled(true);
        bairro.setEnabled(true);
        cep.setEnabled(true);
        cidade.setEnabled(true);
        uf.setEnabled(true);
        celular.setEnabled(true);
        operadora.setEnabled(true);
        sexo.setEnabled(true);
        email.setEnabled(true);
        estadocivil.setEnabled(true);

        Button edit = (Button) findViewById(R.id.memberDetailsEditButton);
        Button save = (Button) findViewById(R.id.memberDetailsSaveButton);

        edit.setEnabled(false);
        edit.setVisibility(View.INVISIBLE);
        save.setEnabled(true);
        save.setVisibility(View.VISIBLE);

        SetAutoCompleteDisc();

    }

    private void SetAutoCompleteDisc() {
        String query = "Select nome from Membros where igreja='" + MyActivity.Igreja() + "'";
        AutoCompleteTextView disc = (AutoCompleteTextView) findViewById(R.id.memberDetailsDiscEditText);

        ArrayList<String> nomes = connection.SimpleQuery(query);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes);
        disc.setAdapter(adapter);
    }

    public void MemberDetailsSaveButton(View view) {

        EditText nome = (EditText) findViewById(R.id.memberDetailsNameEditText);
        AutoCompleteTextView discipulador = (AutoCompleteTextView) findViewById(R.id.memberDetailsDiscEditText);
        EditText aniversario = (EditText) findViewById(R.id.memberDetailsNiverEditText);
        EditText rua = (EditText) findViewById(R.id.memberDetailsRuaEditText);
        EditText num = (EditText) findViewById(R.id.memberDetailsNumEditText);
        EditText complemento = (EditText) findViewById(R.id.memberDetailsCompEditText);
        EditText bairro = (EditText) findViewById(R.id.memberDetailsBairroEditText);
        EditText cep = (EditText) findViewById(R.id.memberDetailsCEPEditText);
        EditText cidade = (EditText) findViewById(R.id.memberDetailsCidadeEditText);
        EditText uf = (EditText) findViewById(R.id.memberDetailsUFEditText);
        EditText celular = (EditText) findViewById(R.id.memberDetailsCelularEditText);
        EditText operadora = (EditText) findViewById(R.id.memberDetailsOpEditText);
        EditText sexo = (EditText) findViewById(R.id.memberDetailsSexEditText);
        EditText email = (EditText) findViewById(R.id.memberDetailsEmailEditText);
        EditText estadocivil = (EditText) findViewById(R.id.memberDetailsEstCivEditText);

        ArrayList<String> data = new ArrayList<>(20);

        data.add(nome.getText().toString());
        if (discipulador.getText().toString().equals("0")) {
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

        String query = "Update Membros set Nome='" + data.get(0)
                + "',Discipulador='" + data.get(1) +
                "',Aniversario='" + data.get(2) +
                "',Rua='" + data.get(3) +
                "',Num='" + data.get(4) +
                "',Complemento='" + data.get(5) +
                "',Bairro='" + data.get(6) +
                "',CEP='" + data.get(7) +
                "',Cidade='" + data.get(8) +
                "',UF='" + data.get(9) +
                "',Celular='" + data.get(10) +
                "',Operadora='" + data.get(11) +
                "',Sexo='" + data.get(12) +
                "',Email='" + data.get(13) +
                "',EstadoCivil='" + data.get(14) +
                "' where Codigo='" + MembrosCelulas.MemberClicked() + "'";

        connection.ExecuteQuery(query);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Atualiza membro de célula");
        builder.setMessage("Dados do membro atualizados.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        alert = builder.create();
        alert.show();

    }

    public void MemberDetailsRemoveButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover Membro da célula");
        builder.setMessage("Deseja realmente remover o membro da célula?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String query = "update membros set celula=0 where codigo='" + MembrosCelulas.MemberClicked() + "'";
                connection.ExecuteQuery(query);
                finish();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        alert = builder.create();
        alert.show();
    }


    public void MemberDetailsSetDateDialog(View view) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EditText date = (EditText) findViewById(R.id.memberDetailsNiverEditText);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();
    }

    private String FormatDate(String date) {
        String formattedDate;
        String ano = date.substring(0, 4);
        String mes = date.substring(5, 7);
        String dia = date.substring(8, 10);

        formattedDate = dia + "/" + mes + "/" + ano;

        return formattedDate;
    }

    private String FormatDateForDB(String date) {
        String formattedDate;
        String ano = date.substring(6, 10);
        String mes = date.substring(3, 5);
        String dia = date.substring(0, 2);

        formattedDate = mes + "-" + dia + "-" + ano;

        return formattedDate;
    }

    public void MemberDetailsSetCityDialog(View view) {
        final EditText city = (EditText) findViewById(R.id.memberDetailsCidadeEditText);
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

    public void MemberDetailsSetUFDialog(View view) {
        final EditText uf = (EditText) findViewById(R.id.memberDetailsUFEditText);
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

    public void MemberDetailsSetOpDialog(View view) {
        final EditText op = (EditText) findViewById(R.id.memberDetailsOpEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(op.getWindowToken(), 0);

        final CharSequence[] items = new CharSequence[]{"Vivo", "Claro", "Oi", "Tim", "Nextel"};

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

    public void MemberDetailsSetSexoDialog(View view) {
        final EditText sex = (EditText) findViewById(R.id.memberDetailsSexEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(sex.getWindowToken(), 0);

        final CharSequence[] items = new CharSequence[]{"M", "F"};

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

    public void MemberDetailsSetEstCivilDialog(View view) {
        final EditText estCivil = (EditText) findViewById(R.id.memberDetailsEstCivEditText);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(estCivil.getWindowToken(), 0);

        final CharSequence[] items = new CharSequence[]{"Casado(a)", "Solteiro(a)", "Viúvo(a)", "Divorciado(a)", "outros"};

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

    private String getDiscipCode(String disc) {
        String query = "select Codigo from membros where Nome='" + disc + "'";
        ArrayList<String> discipulador = connection.SimpleQuery(query);
        return discipulador.get(0);
    }

    private String getDiscipName(String disc) {
        String query = "select Nome from membros where codigo='" + disc + "'";
        ArrayList<String> discipulador = connection.SimpleQuery(query);
        return discipulador.get(0);
    }
}
