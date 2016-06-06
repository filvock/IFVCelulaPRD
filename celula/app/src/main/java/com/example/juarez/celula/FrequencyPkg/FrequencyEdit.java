package com.example.juarez.celula.FrequencyPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.juarez.celula.R;

public class FrequencyEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_4_edit_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void ButtonFrequencyEditService(View view)
    {
        Intent newIntent = new Intent(this, FrequencyEditService.class);
        startActivity(newIntent);
    }

    public void ButtonFrequencyEditCell(View view)
    {
        Intent newIntent = new Intent(this, FrequencyEditCell.class);
        startActivity(newIntent);
    }

}
