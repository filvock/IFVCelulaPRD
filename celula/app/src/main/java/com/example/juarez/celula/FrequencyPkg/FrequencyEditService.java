package com.example.juarez.celula.FrequencyPkg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;
import com.example.juarez.celula.ToolsPkg.ConnectionClass;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FrequencyEditService extends AppCompatActivity {
    AlertDialog alert;
    static String serviceEditIniDate;
    static String serviceEditEndDate;
    ConnectionClass connection;
    CharSequence item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_edit_service_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        connection = new ConnectionClass();
    }

    public static String ServiceIniDate(){return serviceEditIniDate;}
    public static String ServiceEndDate(){return serviceEditEndDate;}

    public void ButtonServiceFrequencyEditIniDate(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data Inicial");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SetServiceIniDate(picker);
                SetDateIniTextView(picker);
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


    public void ButtonServiceFrequencyEditEndDate(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data Final");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SetServiceEndDate(picker);
                SetDateEndTextView(picker);
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

    private void SetDateIniTextView(DatePicker picker) {
        TextView date = (TextView) findViewById(R.id.serviceEditDateIniString);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        date.setText("Data Inicial:   " + day + "/" + month + "/" + year);
        date.setVisibility(View.VISIBLE);
    }

    private void SetDateEndTextView(DatePicker picker) {
        TextView date = (TextView) findViewById(R.id.serviceEditDateEndString);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        date.setText("Data Final:     " + day + "/" + month + "/" + year);
        date.setVisibility(View.VISIBLE);
    }


    private void SetServiceIniDate(DatePicker picker){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        serviceEditIniDate = sdf.format(new Date(year-1900, month-1, day));

    }

    private void SetServiceEndDate(DatePicker picker){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        serviceEditEndDate = sdf.format(new Date(year-1900, month-1, day));

    }

    public void ButtonServiceFrequencyOk(View view)
    {
        if (serviceEditEndDate==null || serviceEditIniDate == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("ERRO!");
            builder.setMessage("Por favor, entre data inicial e data final do relatório.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            alert = builder.create();
            alert.show();
        }
        else {
            GenerateDatesList();
        }
    }

    private void GenerateDatesList(){
        ArrayList<String> dates;
        String query = "select distinct data from frequenciaculto where celula='" + MyActivity.CodCel() + "' and data between '" + serviceEditIniDate + "' and '" + serviceEditEndDate + "'";
        dates = connection.SimpleQuery(query);

        int count = dates.size();

        if (count == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Por favor entre novas datas.");
            builder.setMessage("O período informado não contém entradas para esta célula. Verifique as datas informadas ou verifique sua conexão com a internet.");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    finish();

                }
            });
            alert = builder.create();
            alert.show();
        }
        else {
            final CharSequence[] items = new CharSequence[dates.size()];

            for (int i = 0; i < count; i++) {
                items[i] = dates.get(i).substring(0, 10);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Escolha uma data:");
            builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                    item = items[arg1];
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    AskEditOrDelete();
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

    private void AskEditOrDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar/Excluir Lançamento");
        builder.setMessage("Deseja editar ou remover o lançamento para data selecionada?");
        builder.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String query = "delete from frequenciacelula where celula='" + MyActivity.CodCel() + "' and data='" + item +"'";
                connection.ExecuteQuery(query);
                finish();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ShowEditList();
            }
        });
        alert = builder.create();
        alert.show();

    }

    private void ShowEditList(){
        ArrayList<String> result;
        String query = "select distinct membros.nome, frequenciaculto.present, frequenciaculto.userid " +
                "from frequenciaculto " +
                "join Membros on membros.codigo=frequenciaculto.userid " +
                "where frequenciaculto.celula='" + MyActivity.CodCel() + "' and frequenciaculto.data='" + item + "'";

        result = connection.SimpleQuery(query);

        final CharSequence[] items = new CharSequence[result.size()/3];
        final CharSequence[] codes = new CharSequence[result.size()/3];
        final boolean[] checados = new boolean[items.length];

        final ArrayList<HashMap<String, String>> feedList = new ArrayList<HashMap<String, String>>();

        int count = result.size()/3;


        for (int i = 0; i < count; i++) {
            items[i] = result.get(i*3);
            if (result.get(i*3+1).equals("1")) checados[i]=true;
            else checados[i] = false;
            codes[i] = result.get(i*3+2);

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Frequência no Culto");
        builder.setMultiChoiceItems(items, checados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1, boolean arg2) {
                checados[arg1] = arg2;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                AddFrequency(items, checados, codes);
            }
        });

        alert = builder.create();
        alert.show();
    }

    private void AddFrequency(CharSequence[] items, boolean[] checados, CharSequence[] codes){
        String query;
        int cel = MyActivity.CodCel();

        query = "delete from frequenciaculto where celula='" + cel + "' and data='" + item +"'";
        connection.ExecuteQuery(query);

        int count = checados.length;
        for (int i = 0; i < count; i++) {
            if (checados[i]){
                query = "insert into frequenciaculto values('" + codes[i]
                        + "','" + cel
                        + "','1','"
                        + item + "')";
            }
            else{
                query = "insert into frequenciaculto values('" + codes[i]
                        + "','" + cel
                        + "','0','"
                        + item + "')";
            }
            connection.ExecuteQuery(query);
        }

    }
}
