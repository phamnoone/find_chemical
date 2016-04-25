package com.example.sccn.taskfinal.CustomListview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sccn.taskfinal.R;

import java.util.ArrayList;

/**
 * Created by SCCN on 17/06/2015.
 */
public class AddEquationArrayAdapter extends ArrayAdapter<String> {
    Activity context = null;
    ArrayList<String> myArray = null;
    int layoutId;

    public AddEquationArrayAdapter(Activity context, int layoutId, ArrayList<String> myArray) {
        super(context, layoutId, myArray);
        this.context = context;
        this.myArray = myArray;
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(layoutId, parent, false);
        if (myArray.size() != 1) {
            TextView Sb = (TextView) row.findViewById(R.id.textView27);
            Sb.setText(myArray.get(position));
        } else {

        }
//        EditText Heso = (EditText) row.findViewById(R.id.editText2);
//        EditText TrangTai = (EditText) row.findViewById(R.id.editText3);
//        EditText Mau = (EditText) row.findViewById(R.id.editText4);

        return row;
    }
}
