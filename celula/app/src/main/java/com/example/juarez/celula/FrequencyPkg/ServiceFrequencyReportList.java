package com.example.juarez.celula.FrequencyPkg;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.juarez.celula.ToolsPkg.ConnectionClass;
import com.example.juarez.celula.MyActivity;
import com.example.juarez.celula.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceFrequencyReportList extends AppCompatActivity {
    AlertDialog alert;
    ConnectionClass connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frequency_reports_service_list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connection = new ConnectionClass();
//        ShowList();

        FillReportList();

    }

    private void CloseWindow(){this.finish();}

    private void FillReportList(){

        ArrayList<String> dates;
        String query = "select distinct data from frequenciaculto where celula='" + MyActivity.CodCel() + "' and data between '" + ServiceFrequencyReport.CellIniDate() + "' and '" + ServiceFrequencyReport.CellEndDate() + "'";
        dates = connection.SimpleQuery(query);

        final List<HashMap<String, String>> membros = connection.QueryMembrosCel();
        final CharSequence[] items = new CharSequence[membros.size() + 1];

        int count = dates.size();

        items[0] = "Dias: ";
        for (int i = 0; i < count; i++) {
            items[0] = items[0].toString() + dates.get(i).substring(8, 10) + " ";
        }

        count = membros.size() + 1;

        HashMap<String, String> aux;
        for (int i = 1; i < count; i++) {
            aux = membros.get(i - 1);
            int index = aux.get("Name").indexOf(" ");
            if (index != -1) {
                items[i] = aux.get("Name").substring(0, index+1);
            }
            else{
                items[i] = aux.get("Name")+ " ";
            }

        }


        count = dates.size();

        if (count > 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ERRO!");
            builder.setMessage("Relatório suporta até 4 semanas, por favor entre novas datas.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    CloseWindow();
                }
            });

            alert = builder.create();
            alert.show();
        }
        else {
            for (int i = 0; i < count; i++) {
                query = "select distinct membros.nome, frequenciaculto.present " +
                        "from frequenciaculto " +
                        "join Membros on membros.codigo=frequenciaculto.userid " +
                        "where frequenciaculto.celula='" + MyActivity.CodCel() + "' and frequenciaculto.data='" + dates.get(i).substring(0, 11) + "'";

                ArrayList<String> result = connection.SimpleQuery(query);

                List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

                int count1 = result.size() / 2;
                int count2 = items.length;
                for (int j = 1; j < count2; j++) {
                    int index2 = items[j].toString().indexOf(" ");
                    int semaforo = 0;
                    String name;
                    if (index2 != -1) {
                        name = items[j].toString().substring(0, index2);
                        for (int k = 0; k < count1; k++) {
                            int index = result.get(k * 2).indexOf(" ");

                            if (index != -1) {
                                if (result.get(k * 2).substring(0, index).equals(name)) {
                                    semaforo = 1;
                                    if (result.get(k * 2 + 1).equals("1")) {
                                        items[j] = items[j].toString() + "P ";
                                    } else {
                                        items[j] = items[j].toString() + "F ";
                                    }
                                }
                            } else if (result.get(k * 2).equals(name)) {
                                semaforo = 1;
                                if (result.get(k * 2 + 1).equals("1")) {
                                    items[j] = items[j].toString() + "P ";
                                } else {
                                    items[j] = items[j].toString() + "F ";
                                }
                            }
                        }
                    } else {
                        name = items[j].toString();
                        for (int k = 0; k < count1; k++) {
                            int index = result.get(k * 2).indexOf(" ");

                            if (index != -1) {
                                if (result.get(k * 2).substring(0, index).equals(name)) {
                                    semaforo = 1;
                                    if (result.get(k * 2 + 1).equals("1")) {
                                        items[j] = items[j].toString() + "P ";
                                    } else {
                                        items[j] = items[j].toString() + "F ";
                                    }
                                }
                            } else if (result.get(k * 2).equals(name)) {
                                semaforo = 1;
                                if (result.get(k * 2 + 1).equals("1")) {
                                    items[j] = items[j].toString() + "P ";
                                } else {
                                    items[j] = items[j].toString() + "F ";
                                }
                            }
                        }
                    }

                    if (semaforo == 0) items[j] = items[j].toString() + "F ";

                }
            }

            ListView lv = (ListView) findViewById(R.id.serviceReportListView);

            ArrayList<HashMap<String, String>> feedList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> total = new HashMap<String, String>();

            total.put("Name", "Presentes:");
            int week1 = 0;
            int week2 = 0;
            int week3 = 0;
            int week4 = 0;

            count = items.length;
            for (int i = 0; i < count; i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                int index = items[i].toString().indexOf(" ");
                if (index != -1) {
                    map.put("Name", items[i].toString().substring(0, index));
                    items[i] = items[i].toString().substring(index + 1);
                }
//                else {
//                    map.put("Name", items[i].toString());
//                }

                index = items[i].toString().indexOf(" ");
                if (index != -1) {
                    map.put("week1", items[i].toString().substring(0, index));
                    if (items[i].toString().substring(0, index).equals("P")) week1++;
                    items[i] = items[i].toString().substring(index + 1);
                }
//                else {
//                    map.put("week1", items[i].toString());
//                    if (items[i].toString().substring(0, index).equals("P"))
//                        week1++;
//                }

                index = items[i].toString().indexOf(" ");
                if (index != -1) {
                    map.put("week2", items[i].toString().substring(0, index));
                    if (items[i].toString().substring(0, index).equals("P")) week2++;
                    items[i] = items[i].toString().substring(index + 1);
                }
//                else {
//                    map.put("week2", items[i].toString());
//                    if (items[i].toString().substring(0, index).equals("P"))
//                        week2++;
//              }

                index = items[i].toString().indexOf(" ");
                if (index != -1) {
                    map.put("week3", items[i].toString().substring(0, index));
                    if (items[i].toString().substring(0, index).equals("P")) week3++;
                    items[i] = items[i].toString().substring(index + 1);
                }
//                else {
//                    map.put("week3", items[i].toString());
//                    if (items[i].toString().substring(0, index).equals("P"))
//                        week3++;
//                }

                index = items[i].toString().indexOf(" ");
                if (index != -1) {
                    map.put("week4", items[i].toString().substring(0, index));
                    if (items[i].toString().substring(0, index).equals("P")) week4++;
                    items[i] = items[i].toString().substring(index + 1);
                }
//                else {
//                    map.put("week4", items[i].toString());
//                    if (items[i].toString().substring(0, index).equals("P"))
//                        week4++;
//                }

                feedList.add(map);

            }

            if (week1 != 0) total.put("week1", String.valueOf(week1));
            if (week2 != 0) total.put("week2", String.valueOf(week2));
            if (week3 != 0) total.put("week3", String.valueOf(week3));
            if (week4 != 0) total.put("week4", String.valueOf(week4));

            feedList.add(total);


            SimpleAdapter simpleAdapter = new SimpleAdapter(this, feedList, R.layout.divs_column_list_display, new String[]{"Name", "week1", "week2", "week3", "week4"}, new int[]{R.id.name, R.id.week1, R.id.week2, R.id.week3, R.id.week4});
            lv.setAdapter(simpleAdapter);
        }
    }
}
