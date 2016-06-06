package com.example.juarez.celula;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juarez.celula.CellDataPkg.DadosCelula;
import com.example.juarez.celula.FrequencyPkg.Frequency;
import com.example.juarez.celula.MembersPkg.MembrosCelulas;
import com.example.juarez.celula.SuccessLaderPkg.EscadaSucesso;
import com.example.juarez.celula.ToolsPkg.ChangePassword;

public class OptionsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);
        String versionCode = BuildConfig.VERSION_NAME;
        TextView version = (TextView)findViewById(R.id.versionText);
        version.setText("Version: "+versionCode);
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    public void ButtonDadosCelula(View view)
    {
        Intent dadosCelula = new Intent(this, DadosCelula.class);
        startActivity(dadosCelula);
    }

    public void ButtonFrequency (View view)
    {
        Intent frequency = new Intent(this, Frequency.class);
        startActivity(frequency);
    }


    public void ButtonMembrosCelula(View view)
    {
        Intent membrosCelula = new Intent(this, MembrosCelulas.class);
        startActivity(membrosCelula);
    }

    public void ButtonEscadaSucesso(View view)
    {
        Intent escada = new Intent(this, EscadaSucesso.class);
        startActivity(escada);
    }
    public void ButtonChangePw(View view)
    {
        Intent changePw = new Intent(this, ChangePassword.class);
        startActivity(changePw);
    }

    public void ButtonLogout(View view)
    {
        MyActivity.ClearPreferences();
        finish();
    }

}
