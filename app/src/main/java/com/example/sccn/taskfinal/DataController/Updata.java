package com.example.sccn.taskfinal.DataController;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.sccn.taskfinal.data.EquationInfo;
import com.example.sccn.taskfinal.data.EquationSb;
import com.example.sccn.taskfinal.data.ReactionEquation;

import java.util.ArrayList;

/**
 * Created by SCCN on 18/06/2015.
 */
public class Updata {
    Database database;

    public Updata(Database database) {
        this.database = database;
    }

    private void upEquation(ArrayList<EquationSb> data, String talbeName) {
        int max = getMaxTable(talbeName);
        for (int i = 0; i < data.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", String.valueOf(max + 1));
            contentValues.put("substance",data.get(i).kihieu);
            contentValues.put("quantity",data.get(i).heso);
            contentValues.put("status_vi",data.get(i).trangthai);
            contentValues.put("color_vi",data.get(i).mausac);
            database.database.insert(talbeName,null,contentValues);
        }
    }


    private int getMaxTable(String tableName) {
        Cursor cursor = database.database.rawQuery(" SELECT MAX(id) AS max_id FROM " + tableName, null);
        cursor.moveToNext();
        return Integer.valueOf(cursor.getString(0).toString());
    }

    private void upInfo(EquationInfo data) {
        String tableName = "equation";
        int max = getMaxTable(tableName);

        ContentValues values = new ContentValues();
        values.put("id",String.valueOf(max + 1));
        values.put("temperature_condition_vi",data.nhietdo);
        values.put("pressure_condition_vi",data.apsuat);
        values.put("solven_condition_vi",data.xuctac);
        values.put("phenomenon_vi",data.hientuong);
        values.put("type_vi", data.loai);
        database.database.insert(tableName,null,values);
    }

    private void upChat(ArrayList<EquationSb> data) {
        String tableName = "danhsachchat";

        for (int i = 0; i < data.size(); i++) {
            String id = data.get(i).kihieu;
            String sql = "SELECT id FROM " + tableName + " WHERE kihieu= '" +
                    id + "' ";
            Cursor cursor = database.database.rawQuery(sql, null);
            if (cursor.getCount() == 0) {
                int max = getMaxTable(tableName);
                ContentValues values = new ContentValues();
                values.put("id",String.valueOf(max + 1));
                values.put("kihieu",data.get(i).kihieu);
                database.database.insert(tableName,null,values);
            }
        }
    }

    private void upChatS(ArrayList<EquationSb> data, ArrayList<EquationSb> data2) {
        upChat(data);
        upChat(data2);
    }


    public void upReaction(ReactionEquation data) {
        String tablename1 = "equation_product";
        String tablename2 = "equation_reactant";
        upEquation(data.Sp, tablename1);
        upEquation(data.Pu, tablename2);
        upInfo(data.info);
        upChatS(data.Pu, data.Sp);
    }
}
