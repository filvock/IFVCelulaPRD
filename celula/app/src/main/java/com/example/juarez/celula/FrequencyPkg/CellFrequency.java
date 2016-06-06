package com.example.juarez.celula.FrequencyPkg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CellFrequency extends AppCompatActivity {

    //frequencia
    //0 - faltou
    //1 - presente
    //2 - Programação na Igreja
    //3 - Recesso
    //4 - Ausência do Anfitrião
    //5 - Outros

    ConnectionClass connection;
    AlertDialog alert;
    CharSequence item;
    static String cellDate;



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_1_cell_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connection = new ConnectionClass();


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final DatePicker picker = new DatePicker(this);

            builder.setTitle("Data da Célula");
            builder.setView(picker);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(IsDateEntered(picker)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CellFrequency.this);

                    builder.setTitle("ERRO!");
                    builder.setMessage("Essa data já foi usada para relatório. Informe nova data.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
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
                else {
                    SetCellDate(picker);
                    SetDateTextView(picker);
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

    private void SetDateTextView(DatePicker picker) {
        TextView date = (TextView) findViewById(R.id.cellDateString);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        date.setText(day + "/" + month + "/" + year);
    }

    private void SetCellDate(DatePicker picker){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        cellDate = sdf.format(new Date(year-1900, month-1, day));

    }

    private boolean IsDateEntered(DatePicker picker){
        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        String query = "Select Data from FrequenciaCelula where Data='"+ year + "-" + month + "-" + day +"' and celula='"+ MyActivity.CodCel()+"'";
        ArrayList <String>result = connection.SimpleQuery(query);

        if (result.isEmpty()) return false;
        else return true;

    }

    private boolean IsDateUsed(){
        String query = "Select Data from FrequenciaCelula where Data='"+ cellDate +"' and celula='"+MyActivity.CodCel()+"'";
        ArrayList <String>result = connection.SimpleQuery(query);

        if (result.isEmpty()) return false;
        else return true;

    }

    public void CellReportDataFrequency(View view){
        if(IsDateUsed()){
            AlertDialog.Builder builder = new AlertDialog.Builder(CellFrequency.this);

            builder.setTitle("ERRO!");
            builder.setMessage("Essa data já foi usada para relatório. Informe nova data.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
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
        else {

            final List<HashMap<String, String>> membros = connection.QueryMembrosCel();

            final CharSequence[] items = new CharSequence[membros.size()];
            final boolean[] checados = new boolean[items.length];

            int count = membros.size();

            HashMap<String, String> aux;
            for (int i = 0; i < count; i++) {
                aux = membros.get(i);
                items[i] = aux.get("Name");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Membros da Célula");
            builder.setMultiChoiceItems(items, checados, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                    checados[arg1] = arg2;
                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    AddFrequency(membros, checados);
                }
            });

            alert = builder.create();
            alert.show();
        }
    }

    public void CellReportDataOffer(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Oferta da Célula");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        input.addTextChangedListener(new TextWatcher() {

            private boolean isUpdating = false;
            // Pega a formatacao do sistema, se for brasil R$ se EUA US$
            private NumberFormat nf = NumberFormat.getCurrencyInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int after) {
                // Evita que o método seja executado varias vezes.
                // Se tirar ele entre em loop
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                isUpdating = true;
                String str = s.toString();
                // Verifica se já existe a máscara no texto.
                boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) &&
                        (str.indexOf(".") > -1 || str.indexOf(",") > -1));
                // Verificamos se existe máscara
                if (hasMask) {
                    // Retiramos a máscara.
                    str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
                            .replaceAll("[.]", "");
                }

                try {
                    // Transformamos o número que está escrito no EditText em
                    // monetário.
                    str = nf.format(Double.parseDouble(str) / 100);
                    input.setText(str);
                    input.setSelection(input.getText().length());
                } catch (NumberFormatException e) {
                    s = "";
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Não utilizamos
            }
            @Override
            public void afterTextChanged(Editable s) {
                // Não utilizamos
            }
        });


        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SaveOfferValue(input.getText().toString());
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
    public void CellReportDataNoCell(View view){
        final CharSequence[] items = new CharSequence[]{"Programação na Igreja", "Recesso", "Ausência do Anfitrião", "Outros"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Membros da Célula");
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                item = items[arg1];
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SaveNoCellReason();
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

    public void CellReportNewDate(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data da Célula");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(IsDateEntered(picker)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CellFrequency.this);

                    builder.setTitle("ERRO!");
                    builder.setMessage("Essa data já foi usada para relatório. Informe nova data.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
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
                else {
                    SetCellDate(picker);
                    SetDateTextView(picker);
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



    public void CellReportDataOk(View view){
        finish();
    }


    private void AddFrequency(List<HashMap<String, String>> items, boolean[] checados){
        String query;
        int cel = MyActivity.CodCel();
        String date = cellDate;

        int count = checados.length;
        for (int i = 0; i < count; i++) {
            HashMap<String, String> aux;
            aux = items.get(i);
            if (checados[i]) {
                query = "insert into FrequenciaCelula values ('" + aux.get("Code") + "','" + cel + "','1','" + date + "')";
                connection.ExecuteQuery(query);
            }
            else{
                query = "insert into FrequenciaCelula values ('" + aux.get("Code") + "','" + cel + "','0','" + date + "')";
                connection.ExecuteQuery(query);
            }
        }

    }

    private void SaveOfferValue (String value){

        String query = "insert into OfertasCelulas values ('"+MyActivity.CodCel()+"','"+value.substring(2)+"','"+cellDate+"')";
        connection.ExecuteQuery(query);

    }

    private void SaveNoCellReason (){
        //2 - Programação na Igreja
        //3 - Recesso
        //4 - Ausência do Anfitrião
        //5 - Outros
        final List<HashMap<String, String>> membros = connection.QueryMembrosCel();
        int cel= MyActivity.CodCel();
        String date = cellDate;
        int count = membros.size();
        String query;

        if (item.toString().equals("Programação na Igreja")) {
            HashMap<String, String> aux;
            for (int i = 0; i < count; i++) {
                aux = membros.get(i);
                query = "insert into FrequenciaCelula values ('" + aux.get("Code") + "','" + cel + "','2','" + date + "')";
                connection.ExecuteQuery(query);
            }
        }
        else if (item.toString().equals("Recesso")) {
            HashMap<String, String> aux;
            for (int i = 0; i < count; i++) {
                aux = membros.get(i);
                query = "insert into FrequenciaCelula values ('" + aux.get("Code") + "','" + cel + "','3','" + date + "')";
                connection.ExecuteQuery(query);
            }
        }
        else if (item.toString().equals("Ausência do Anfitrião")) {
            HashMap<String, String> aux;
            for (int i = 0; i < count; i++) {
                aux = membros.get(i);
                query = "insert into FrequenciaCelula values ('" + aux.get("Code") + "','" + cel + "','4','" + date + "')";
                connection.ExecuteQuery(query);
            }
        }
        else if (item.toString().equals("Outros")) {
            HashMap<String, String> aux;
            for (int i = 0; i < count; i++) {
                aux = membros.get(i);
                query = "insert into FrequenciaCelula values ('" + aux.get("Code") + "','" + cel + "','5','" + date + "')";
                connection.ExecuteQuery(query);
            }
        }

    }




}
