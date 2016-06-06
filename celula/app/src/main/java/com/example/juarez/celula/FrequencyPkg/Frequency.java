package com.example.juarez.celula.FrequencyPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.juarez.celula.R;

public class Frequency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_0_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void ButtonCellFrequency(View view)
    {
        Intent cellFrequency = new Intent(this, CellFrequency.class);
        startActivity(cellFrequency);
    }

    public void ButtonServiceFrequency(View view)
    {
        Intent serviceFrequency = new Intent(this, ServiceFrequency.class);
        startActivity(serviceFrequency);
    }

    public void ButtonReport(View view)
    {
        Intent report = new Intent(this, Reports.class);
        startActivity(report);
    }

    public void ButtonFrequencyEdit(View view)
    {
        Intent newIntent  = new Intent(this, FrequencyEdit.class);
        startActivity(newIntent);
    }


}
