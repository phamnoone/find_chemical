package com.example.sccn.taskfinal.activity.functional;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sccn.taskfinal.R;
import com.example.sccn.taskfinal.activity.MainActivity;
import com.example.sccn.taskfinal.data.Periodic;
import com.example.sccn.taskfinal.DataController.LoadData;

import java.util.ArrayList;

/**
 * Created by SCCN on 09/06/2015.
 */
public class ChemicalPeriodicList extends ActionBarActivity implements TextWatcher {
    private void setThemes() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    public void onBackPressed() {
        startActivity(new Intent(ChemicalPeriodicList.this, MainActivity.class));
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        finish();
        System.exit(0);
    }

    ArrayList<Periodic> periodicArrayList;
    String[] chuky = {"1", "2", "3", "4", "5", "6", "7"};
    String[] nhom = {"I A", "II A", "III A", "IV A", "V A", "VI A", "VII A", "VIII A",
            "I B", "II B", "III B", "IV B", "V B", "VI B", "VII B", "VIII B"};
    ListView listView;
    AutoCompleteTextView textChuki;
    AutoCompleteTextView textNhom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_periodic_list);
        setThemes();

        textChuki = (AutoCompleteTextView) findViewById(R.id.chuki);
        textNhom = (AutoCompleteTextView) findViewById(R.id.nhom);
        Button buttonSeach = (Button) findViewById(R.id.button2);
        listView = (ListView) findViewById(R.id.listView2);

        textChuki.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, chuky));
        textNhom.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, nhom
        ));
        textChuki.addTextChangedListener(this);
        textNhom.addTextChangedListener(this);

        LoadData loadData = new LoadData();
        periodicArrayList = loadData.loadPeriodic();

        buttonSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) ChemicalPeriodicList.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ChemicalPeriodicList.this.getCurrentFocus().getWindowToken(), 0);

                final ArrayList<String> list = new ArrayList<String>();

                if (textChuki.getText().toString().equals("")
                        && textNhom.getText().toString().equals("")) {
                    for (int i = 0; i < periodicArrayList.size(); i++) {
                        list.add(
                                periodicArrayList.get(i).id + ":" +
                                        periodicArrayList.get(i).ten + " ( " +
                                        periodicArrayList.get(i).kihieu + " )");
                    }
                }

                if (!textChuki.getText().toString().equals("")
                        && textNhom.getText().toString().equals("")) {
                    for (int i = 0; i < periodicArrayList.size(); i++) {
                        if (periodicArrayList.get(i).chuki.equals(textChuki.getText().toString()))
                            list.add(
                                    periodicArrayList.get(i).id + ":" +
                                            periodicArrayList.get(i).ten + " ( " +
                                            periodicArrayList.get(i).kihieu + " )");
                    }
                }

                if (textChuki.getText().toString().equals("")
                        && !textNhom.getText().toString().equals("")) {
                    for (int i = 0; i < periodicArrayList.size(); i++) {
                        if (periodicArrayList.get(i).nhom.equals(textNhom.getText().toString()))
                            list.add(
                                    periodicArrayList.get(i).id + ":" +
                                            periodicArrayList.get(i).ten + " ( " +
                                            periodicArrayList.get(i).kihieu + " )");
                    }
                }

                if (!textChuki.getText().toString().equals("")
                        && !textNhom.getText().toString().equals("")) {
                    for (int i = 0; i < periodicArrayList.size(); i++) {
                        if (periodicArrayList.get(i).chuki.equals(textChuki.getText().toString()))
                            if (periodicArrayList.get(i).nhom.equals(textNhom.getText().toString()))
                                list.add(
                                        periodicArrayList.get(i).id + ":" +
                                                periodicArrayList.get(i).ten + " ( " +
                                                periodicArrayList.get(i).kihieu + " )");
                    }
                }

                listView.setAdapter(new ArrayAdapter<String>(ChemicalPeriodicList.this, android.R.layout.simple_dropdown_item_1line, list));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Sbid = 0;
                String data = listView.getAdapter().getItem(position).toString();
                String[] temp = data.split(":");

                for (String token : temp) {
                    Sbid = Integer.valueOf(token) - 1;
                    break;
                }
                Periodic temdata = periodicArrayList.get(Sbid);
                TextView kihieu = (TextView) findViewById(R.id.textView8);
                TextView sohieu = (TextView) findViewById(R.id.textView14);
                TextView ten = (TextView) findViewById(R.id.textView15);
                TextView nhom = (TextView) findViewById(R.id.textView16);
                TextView chuki = (TextView) findViewById(R.id.textView17);
                TextView loai = (TextView) findViewById(R.id.textView18);
                TextView che = (TextView) findViewById(R.id.textView20);

                kihieu.setText(temdata.kihieu);
                sohieu.setText(String.valueOf(temdata.id));
                ten.setText(temdata.ten);
                nhom.setText(temdata.nhom);
                chuki.setText(temdata.chuki);
                loai.setText(temdata.loai);
                che.setText(temdata.che);

            }
        });

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
