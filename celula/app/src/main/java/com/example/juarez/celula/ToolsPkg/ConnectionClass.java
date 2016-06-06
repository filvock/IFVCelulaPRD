package com.example.juarez.celula.ToolsPkg;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.example.juarez.celula.MyActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class ConnectionClass {

    Connection connect;


    public ConnectionClass() {

        connect = CONN("igrejafont10", "Fre1del.", "igrejafont10", "dbsq0015.whservidor.com:1433");
    }


    @SuppressLint("NewApi")
    private Connection CONN(String _user, String _pass, String _DB, String _server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL;

        Properties prop = new Properties();
        prop.put("charSet", "iso-8859-7");
        prop.put("user", "igrejafont10");
        prop.put("password", "Fre1del.");

        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + _server + ";" + "databaseName=" + _DB + ";user=" + _user + ";password=" + _pass + ";";
            conn = DriverManager.getConnection(ConnURL, prop);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }

        return conn;
    }

    public ArrayList<String> SimpleQuery(String query) {
        ResultSet rs;
        ArrayList<String> result = new ArrayList<>(5);
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(query);

            result = new ArrayList<String>();

            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount()+1;
                for (int i = 1; i < columnCount; i++) {
                    result.add(rs.getString(i));
                }
            }
        }
        catch (Exception e)
        {
            Log.e("ERRO", e.getMessage());
        }
        return result;
    }

    public void ExecuteQuery(String query) {
        try {
            Statement statement = connect.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
    }

    public List<HashMap<String, String>> QueryMembrosCel(){
        String querycmd = "select * from membros where celula='"+ MyActivity.CodCel()+"'";
        ResultSet rs;
        List<HashMap<String, String>> data = null;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(querycmd);

            data = new ArrayList<HashMap<String, String>>();
            while (rs.next()) {
                HashMap<String, String> datanum = new HashMap<String, String>();
                datanum.put("Code",rs.getString("Codigo"));
                datanum.put("Name", rs.getString("Nome"));
                data.add(datanum);
            }
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
        return data;
    }

    public List<HashMap<String, String>> QueryMembrosNoCel(){
        String querycmd = "select * from membros where celula='0' and igreja='"+MyActivity.Igreja()+"'";
        ResultSet rs;
        List<HashMap<String, String>> data = null;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(querycmd);

            data = new ArrayList<HashMap<String, String>>();
            while (rs.next()) {
                HashMap<String, String> datanum = new HashMap<String, String>();
                datanum.put("Code",rs.getString("Codigo"));
                datanum.put("Name", rs.getString("Nome"));
                data.add(datanum);
            }
        }
        catch(Exception e){
            Log.e("ERRO", e.getMessage());
        }
        return data;
    }

}


