package com.example.juarez.celula.FrequencyPkg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceFrequency extends AppCompatActivity {

    ConnectionClass connection;
    AlertDialog alert;
    CharSequence item;
    static String serviceDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_2_service_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connection = new ConnectionClass();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data do Culto:");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(IsDateEntered(picker)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceFrequency.this);

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
                    SetServiceDate(picker);
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
        TextView date = (TextView) findViewById(R.id.serviceDateString);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        date.setText(day + "/" + month + "/" + year);
    }

    private void SetServiceDate(DatePicker picker){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        serviceDate = sdf.format(new Date(year-1900, month-1, day));

    }

    private boolean IsDateEntered(DatePicker picker){
        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        String query = "Select Data from FrequenciaCulto where Data='"+ year + "-" + month + "-" + day +"' and celula='"+ MyActivity.CodCel()+"'";
        ArrayList<String> result = connection.SimpleQuery(query);

        if (result.isEmpty()) return false;
        else return true;

    }

    public void ServiceFrequency(View view){
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

    private void AddFrequency(List<HashMap<String, String>> items, boolean[] checados){
        String query;
        int cel = MyActivity.CodCel();


        int count = checados.length;
        for (int i = 0; i < count; i++) {
            HashMap<String, String> aux;
            aux = items.get(i);
            if (checados[i]) {
                query = "insert into FrequenciaCulto values ('" + aux.get("Code") + "','" + cel + "','1','" + serviceDate + "')";
                connection.ExecuteQuery(query);
            }
            else{
                query = "insert into FrequenciaCulto values ('" + aux.get("Code") + "','" + cel + "','0','" + serviceDate + "')";
                connection.ExecuteQuery(query);
            }
        }

    }

    public void ServiceNewDate(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data do Culto:");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(IsDateEntered(picker)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceFrequency.this);

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
                    SetServiceDate(picker);
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

    public void ServiceBack(View view){
        finish();
    }
}
