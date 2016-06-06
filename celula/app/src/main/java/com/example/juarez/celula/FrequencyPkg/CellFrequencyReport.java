package com.example.juarez.celula.FrequencyPkg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CellFrequencyReport extends AppCompatActivity {

    AlertDialog alert;
    static String cellIniDate;
    static String cellEndDate;
    ConnectionClass connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_reports_cell_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connection = new ConnectionClass();
    }

    public static String CellIniDate(){return cellIniDate;}
    public static String CellEndDate(){return cellEndDate;}

    public void ButtonDateIni(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data Inicial");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SetCellIniDate(picker);
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


    public void ButtonDateEnd(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker picker = new DatePicker(this);

        builder.setTitle("Data Final");
        builder.setView(picker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SetCellEndDate(picker);
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
        TextView date = (TextView) findViewById(R.id.cellDateIniString);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        date.setText("Data Inicial:   " + day + "/" + month + "/" + year);
        date.setVisibility(View.VISIBLE);
    }

    private void SetDateEndTextView(DatePicker picker) {
        TextView date = (TextView) findViewById(R.id.cellDateEndString);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        date.setText("Data Final:     " + day + "/" + month + "/" + year);
        date.setVisibility(View.VISIBLE);
    }


    private void SetCellIniDate(DatePicker picker){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        cellIniDate = sdf.format(new Date(year-1900, month-1, day));

    }

    private void SetCellEndDate(DatePicker picker){
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        int   day  = picker.getDayOfMonth();
        int   month= picker.getMonth() + 1;
        int   year = picker.getYear();

        cellEndDate = sdf.format(new Date(year-1900, month-1, day));

    }

    public void ButtonGenerateReport(View view)
    {
        if (cellEndDate==null || cellIniDate == null) {
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
            Intent cellFreqList = new Intent(this, CellFrequencyReportList.class);
            startActivity(cellFreqList);
        }
    }
}
