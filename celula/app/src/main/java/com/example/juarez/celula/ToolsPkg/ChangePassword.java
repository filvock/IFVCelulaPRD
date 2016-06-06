package com.example.juarez.celula.ToolsPkg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;

public class ChangePassword extends AppCompatActivity {

    AlertDialog alert;
    ConnectionClass connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connection = new ConnectionClass();

        TextView login = (TextView)findViewById(R.id.changePwLogin);
        TextView cell = (TextView) findViewById(R.id.changePwCell);

        login.setText(MyActivity.Login());
        cell.setText(MyActivity.CelName());
    }

    public void ChangePwOkButton (View view){
        EditText oldPw = (EditText) findViewById(R.id.changePwOldPw);
        EditText newPw = (EditText) findViewById(R.id.changePwNewPw1);
        EditText newPw1 = (EditText) findViewById(R.id.changePwNewPw2);

        String oldPwStr = oldPw.getText().toString();
        String newPwStr = newPw.getText().toString();
        String newPw1Str = newPw1.getText().toString();

        if (!oldPwStr.equals(MyActivity.Password()))
        {    AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("ERRO!");
            builder.setMessage("Senha antiga não confere, por favor digite novamente.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    EditText pwd = (EditText)findViewById(R.id.changePwOldPw);
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
        } else if(!newPwStr.equals(newPw1Str)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("ERRO!");
            builder.setMessage("Nova Senha digitada incorretamente no campo Repita Nova Senha.Por favor digite novamente.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    EditText pwd = (EditText)findViewById(R.id.changePwNewPw2);
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

        }else{
            String query = "update lidercelula set Senha='"+newPwStr+"' where codigo='"+MyActivity.LoginCode()+"'";
            connection.ExecuteQuery(query);
            finish();
        }

    }


    public void ChangePwBackButton (View view){
        finish();
    }
}
