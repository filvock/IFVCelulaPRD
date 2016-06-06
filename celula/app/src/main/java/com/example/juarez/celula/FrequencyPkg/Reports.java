package com.example.juarez.celula.FrequencyPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.juarez.celula.R;

public class Reports extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_3_reports_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public void ButtonCellReport(View view)
    {
        Intent cellReport = new Intent(this, CellFrequencyReport.class);
        startActivity(cellReport);
    }

    public void ButtonServiceReport(View view)
    {
        Intent serviceReport = new Intent(this, ServiceFrequencyReport.class);
        startActivity(serviceReport);
    }

}
