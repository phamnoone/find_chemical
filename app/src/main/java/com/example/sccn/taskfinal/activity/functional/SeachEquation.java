package com.example.sccn.taskfinal.activity.functional;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import com.example.sccn.taskfinal.DataController.LoadData;
import com.example.sccn.taskfinal.R;
import com.example.sccn.taskfinal.activity.MainActivity;
import com.example.sccn.taskfinal.data.Equation;
import com.example.sccn.taskfinal.data.ReactionEquation;

import java.util.ArrayList;

/**
 * Created by SCCN on 09/06/2015.
 */
public class SeachEquation extends ActionBarActivity implements TextWatcher {
    private void setThemes() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
        startActivity(new Intent(SeachEquation.this, MainActivity.class));
    }

    ProgressDialog mprogressDialog;
    LoadData loadData;
    ArrayList<Equation> listEquation;
    MultiAutoCompleteTextView chatPu, chatSp;
    ListView listView;
    ArrayList<ReactionEquation> PhuongTrinh;
    ArrayList<String> danhsachPu;
    ArrayList<String> danhsachInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seach_equation);
        setThemes();

        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setMessage("loading...");
        mprogressDialog.setCancelable(false);
        loadData = new LoadData();
        listEquation = loadData.loadEquation();

        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (int i = 0; i < listEquation.size(); i++) {
            stringArrayList.add(listEquation.get(i).Symboy);
        }

        chatPu = (MultiAutoCompleteTextView) findViewById(R.id.chatPu);
        chatSp = (MultiAutoCompleteTextView) findViewById(R.id.chatSp);
        Button seach = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);

        chatPu.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        chatPu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stringArrayList));
        chatSp.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        chatSp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stringArrayList));

        seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogressDialog.show();
                InputMethodManager inputMethodManager = (InputMethodManager) SeachEquation.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(SeachEquation.this.getCurrentFocus().getWindowToken(), 0);

                ArrayList<String> listChatPu = StringToArray(chatPu.getText().toString());
                ArrayList<String> listChatSp = StringToArray(chatSp.getText().toString());
                PhuongTrinh = new ArrayList<ReactionEquation>();
                danhsachPu = new ArrayList<String>();
                danhsachInfo = new ArrayList<String>();

                ArrayList<Integer> listIdEquation = loadData.getListIdEquation(listChatPu, listChatSp);
                mprogressDialog.dismiss();
                if (listIdEquation.size() == 0)
                    danhsachPu.add("Không tìm thấy phương trình");
                else {
                    for (int i = 0; i < listIdEquation.size(); i++) {
                        int tempid = listIdEquation.get(i).intValue();
                        PhuongTrinh.add(new ReactionEquation(
                                tempid,
                                loadData.loadEquationInfo(tempid),
                                loadData.loadEquationReactant(tempid),
                                loadData.loadEquationProduct(tempid)
                        ));
                    }

                    for (int i = 0; i < PhuongTrinh.size(); i++) {
                        danhsachPu.add(PhuongTrinh.get(i).datatext.toString());
                        danhsachInfo.add(PhuongTrinh.get(i).infotext.toString());
                    }
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //Bạn có thể lược bỏ phần nào trong Dialog mà bạn thích: Tile, Message...

                            AlertDialog.Builder builder = new AlertDialog.Builder(SeachEquation.this);
                            builder.setTitle("Thông tin phản ứng");
                            builder.setMessage(danhsachPu.get(position) + "\n" + danhsachInfo.get(position));
                            builder.setCancelable(false);//Set có cho người dùng Cancer bằng nút quay lại (back) ko? false: ko
                            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Làm cái gì đó khi ấn Yes tại đây
                                    dialog.dismiss();
                                }
                            });
                            builder.show();//Hiển thị Dialog


                        }
                    });

                }
                listView.setAdapter(new ArrayAdapter<String>(
                        SeachEquation.this, android.R.layout.simple_dropdown_item_1line, danhsachPu));

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

    private String convert(String text) {

        SpannableStringBuilder cs = new SpannableStringBuilder(text);//Your suppose String
        for (int i = 0; i < text.length(); i++) {
            if (cs.charAt(i) > '0' | cs.charAt(i) > '9') {
                cs.setSpan(new SubscriptSpan(), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//Setting span index
                cs.setSpan(new RelativeSizeSpan((float) 0.75), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return cs.toString();
    }

    private ArrayList<String> StringToArray(String input) {
        ArrayList<String> list = new ArrayList<String>();
        if (!input.equals("")) {
            try {
                String[] temptoken1 = input.substring(0, input.length() - 2).split(", ");
                for (String token : temptoken1) {
                    list.add(token.trim());
                }
            } catch (Exception e) {
                return new ArrayList<String>();
            }
        } else return null;
        return list;
    }

}