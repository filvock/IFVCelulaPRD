package com.example.juarez.celula;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.ArrayList;

public class MyActivity extends AppCompatActivity {
    ArrayList<String> result;
    static int codCelula;
    static String igreja;
    static String celname;
    static String password;
    static String login;
    static String loginCode;
    AlertDialog alert;

    static SharedPreferences sharedPref;
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String nothing = "";
        String savedLogin = sharedPref.getString("login", nothing);

        if (savedLogin.length() > 0) {
            Intent launchNextActivity = new Intent(this, OptionsPage.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(launchNextActivity);
        }
    }

    public static void ClearPreferences ()
    {
        editor.clear();
        editor.commit();
    }

    public void ButtonClicked(View view){
        TextView editUser = (EditText)findViewById(R.id.editUserId);
        TextView editPass = (EditText)findViewById(R.id.editPassword);

        ConnectionClass connection = new ConnectionClass();
        String query = "Select * from LiderCelula where UserId like '" + editUser.getText().toString() + "'";
        result = connection.SimpleQuery(query);

        if (result.isEmpty())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("ERRO!");
            builder.setMessage("Usuário não encontrado, por favor entre novo usuário.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    EditText user = (EditText)findViewById(R.id.editUserId);
                    user.setText("");
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

            codCelula = Integer.parseInt(result.get(4));

            query = "select nome,Igreja from celulas where codigo='"+codCelula+"'";
            ArrayList<String> aux = connection.SimpleQuery(query);
            celname = aux.get(0).toString();
            igreja = aux.get(1).toString();
            loginCode = result.get(0);
            login = result.get(1);
            password = result.get(2);


            if (!editPass.getText().toString().equals(result.get(2))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("ERRO!");
                builder.setMessage("Senha não confere, por favor digite novamente.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText pwd = (EditText)findViewById(R.id.editPassword);
                        pwd.setText("");
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
            } else {

                SaveInfo();
                Intent launchNextActivity = new Intent(this, OptionsPage.class);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(launchNextActivity);
            }
        }
    }

    public void SaveInfo(){
        editor.putString("login", login);
        editor.putString("loginCode", loginCode);
        editor.putInt("codCel", codCelula);
        editor.putString("Igreja", igreja);

        editor.commit();
    }

    public static int CodCel(){ return sharedPref.getInt("codCel", -1);}
    public static String Igreja(){return sharedPref.getString("Igreja", "Erro");}
    public static String Password(){return sharedPref.getString("passoword", "Erro");}
    public static String Login(){return sharedPref.getString("login", "Erro");}
    public static String LoginCode(){return sharedPref.getString("loginCode", "Erro");}
    public static String CelName(){
        return sharedPref.getString("celname", "Erro");
    }
}