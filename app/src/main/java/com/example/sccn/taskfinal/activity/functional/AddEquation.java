package com.example.sccn.taskfinal.activity.functional;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sccn.taskfinal.CustomListview.AddEquationArrayAdapter;
import com.example.sccn.taskfinal.DataController.LoadData;
import com.example.sccn.taskfinal.DataController.Updata;
import com.example.sccn.taskfinal.R;
import com.example.sccn.taskfinal.activity.MainActivity;
import com.example.sccn.taskfinal.data.EquationInfo;
import com.example.sccn.taskfinal.data.EquationSb;
import com.example.sccn.taskfinal.data.ReactionEquation;

import java.util.ArrayList;

/**
 * Created by SCCN on 15/06/2015.
 */
public class AddEquation extends ActionBarActivity {

    private void setThemes() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void hideKey() {
        InputMethodManager inputMethodManager = (InputMethodManager) AddEquation.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(AddEquation.this.getCurrentFocus().getWindowToken(), 0);
    }

    EditText dataText;
    ListView listView;
    Button check;
    TextView tile;
    ArrayList<String> listChatPu, listChatSp;
    ReactionEquation equationAdd;
    LoadData loadData;

    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
        Intent next = new Intent(AddEquation.this, MainActivity.class);
        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(next);
        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equation_add);
        setThemes();


        dataText = (EditText) findViewById(R.id.editText);
        check = (Button) findViewById(R.id.button3);
        listView = (ListView) findViewById(R.id.listView3);
        tile = (TextView) findViewById(R.id.textView23);
        equationAdd = new ReactionEquation();
        loadData = new LoadData();

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acction1();
            }
        });
    }

    private boolean checkInput(String input) {
        if (input.equals(""))
            return false;
        if (input.indexOf("+") == -1)
            return false;
        if (input.indexOf("=") == -1)
            return false;
        return true;
    }

    private ArrayList<Integer> checkEquation(ArrayList<String> Pu, ArrayList<String> Sp) {
        LoadData loadData = new LoadData();
        return loadData.getListIdEquation(Pu, Sp);
    }

    private void notification(String input) {
        Toast toast = Toast.makeText(AddEquation.this, input, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void acction1() {
        hideKey();
        if (checkInput(dataText.getText().toString())) {
            String textEquation = dataText.getText().toString();
            String[] EquationSb = textEquation.split("=");
            String chatPu = EquationSb[0];
            String chatsp = EquationSb[1];
            listChatPu = new ArrayList<String>();
            listChatSp = new ArrayList<String>();
            if (chatPu.indexOf("+") == -1)
                listChatPu.add(chatPu.trim());
            else {
                String[] temp = chatPu.trim().split("\\+");
                for (String token : temp) {
                    listChatPu.add(token.trim());
                }
            }
            if (chatsp.indexOf("+") == -1)
                listChatSp.add(chatsp.trim());
            else {
                String[] temp = chatsp.trim().split("\\+");
                for (String token : temp) {
                    listChatSp.add(token.trim());
                }
            }


            ArrayList<Integer> list = checkEquation(listChatPu, listChatSp);
            if (list.size() != 0) {
                notification("Phương trình đã tồn tại");
            } else {
                ArrayList<String> listEquation = new ArrayList<String>();
                for (int i = 0; i < listChatPu.size(); i++)
                    listEquation.add(listChatPu.get(i));
                for (int i = 0; i < listChatSp.size(); i++)
                    listEquation.add(listChatSp.get(i));

                notification("Mời bạn nhập đầy đủ thông tin");
                AddEquationArrayAdapter arrayAdapter = new AddEquationArrayAdapter(
                        AddEquation.this, R.layout.add_equation_listview, listEquation
                );
                listView.setAdapter(arrayAdapter);
                check.setText("Tiếp tục");
                dataText.setVisibility(View.GONE);
                tile.setText("Nhập thông tin cho các chất. Không được bỏ trống hệ số");
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acction2();
                    }
                });
            }

        } else {
            notification("Bạn nhập không đúng kiểu phương trình");
        }
    }

    private void acction2() {
        if (listChatPu != null) {
            int sptPu = listChatPu.size();


            for (int i = 0; i < listView.getChildCount(); i++) {
                View r = listView.getChildAt(i);
                // check he so
                EditText heso = (EditText) r.findViewById(R.id.editText2);
                TextView chat = (TextView) r.findViewById(R.id.textView27);
                TextView mausac = (TextView) r.findViewById(R.id.editText4);
                TextView trangthai = (TextView) r.findViewById(R.id.editText3);
                if (heso.getText().toString().equals("")) {
                    notification("Thiếu hệ số cho chất " + chat.getText());
                } else {
                    if (i < sptPu) {
                        equationAdd.Pu.add(new EquationSb(
                                        1,
                                        chat.getText().toString(),
                                        heso.getText().toString(),
                                        mausac.getText().toString(),
                                        trangthai.getText().toString()
                                )
                        );
                    } else {
                        equationAdd.Sp.add(new EquationSb(
                                        1,
                                        chat.getText().toString(),
                                        heso.getText().toString(),
                                        mausac.getText().toString(),
                                        trangthai.getText().toString()
                                )
                        );
                    }


                }
            }
            ArrayList<String> tempz = new ArrayList<String>();
            tempz.add("a");
            AddEquationArrayAdapter arrayAdapter = new AddEquationArrayAdapter(
                    AddEquation.this, R.layout.add_equationlistview2, tempz
            );
            listView.setAdapter(arrayAdapter);
            //set new theme2
            check.setText("Thêm phương trình");
            dataText.setVisibility(View.GONE);
            tile.setText("Nhập thông tin cho phương trình. Có thể bỏ trống");
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acction3();
                }
            });
        }
    }

    private void acction3() {

        View r = listView.getChildAt(0);
        String nhietdo = ((EditText) r.findViewById(R.id.editText5)).getText().toString();
        String apxuat = ((EditText) r.findViewById(R.id.editText6)).getText().toString();
        String xuctac = ((EditText) r.findViewById(R.id.editText7)).getText().toString();
        String hientuong = ((EditText) r.findViewById(R.id.editText8)).getText().toString();
        String loaipu = ((EditText) r.findViewById(R.id.editText9)).getText().toString();
        equationAdd.info = new EquationInfo(1, nhietdo, apxuat, xuctac, hientuong, loaipu);

        equationAdd.show();

        Updata updata = new Updata(loadData.getDatabase());
        updata.upReaction(equationAdd);
        notification("Thêm phản ứng thành công");
    }
}
