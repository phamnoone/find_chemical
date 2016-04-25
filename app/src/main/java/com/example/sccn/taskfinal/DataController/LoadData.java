package com.example.sccn.taskfinal.DataController;

import android.database.Cursor;

import com.example.sccn.taskfinal.data.Equation;
import com.example.sccn.taskfinal.data.EquationInfo;
import com.example.sccn.taskfinal.data.EquationSb;
import com.example.sccn.taskfinal.data.Periodic;
import com.example.sccn.taskfinal.data.SbId;

import java.util.ArrayList;

/**
 * Created by SCCN on 10/06/2015.
 */
public class LoadData {

    Database database;

    public LoadData() {
        database = new Database();
    }
    public Database getDatabase(){
        return this.database;
    }
    public ArrayList<Equation> loadEquation() {

        ArrayList<Equation> listEquations = new ArrayList<Equation>();
        String tableNameEquation = "danhsachchat";

        Cursor cursor = database.database.query(tableNameEquation, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listEquations.add(new Equation(cursor.getString(1), Integer.valueOf(cursor.getString(0))));
            cursor.moveToNext();
        }
        return listEquations;
    }

    public EquationInfo loadEquationInfo(int id) {

        String tableNameEquationInfo = "equation";
        String key = String.valueOf(id);
        EquationInfo equationInfo = null;

        Cursor cursor = database.database.rawQuery(
                "SELECT * FROM " + tableNameEquationInfo + " WHERE id = '" + key + "'", null);

        while (cursor.moveToNext()) {
            equationInfo =
                    new EquationInfo(
                            Integer.valueOf(cursor.getString(0)).intValue(),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    );
        }
        return equationInfo;
    }

    public ArrayList<EquationSb> loadEquationReactant(int id) {

        String tableName = "equation_reactant";
        String key = String.valueOf(id);
        ArrayList<EquationSb> equationSbArrayList = new ArrayList<EquationSb>();

        Cursor cursor = database.database.rawQuery(
                "SELECT * FROM " + tableName + " WHERE id = '" + key + "'", null);

        while (cursor.moveToNext()) {
            equationSbArrayList.add(new EquationSb(
                    Integer.valueOf(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));

        }

        return equationSbArrayList;
    }

    public ArrayList<EquationSb> loadEquationProduct(int id) {

        String tableName = "equation_product";
        String key = String.valueOf(id);
        ArrayList<EquationSb> equationSbArrayList = new ArrayList<EquationSb>();

        Cursor cursor = database.database.rawQuery(
                "SELECT * FROM " + tableName + " WHERE id = '" + key + "'", null);

        while (cursor.moveToNext()) {
            equationSbArrayList.add(new EquationSb(
                    Integer.valueOf(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));

        }
        return equationSbArrayList;
    }

    public ArrayList<Periodic> loadPeriodic() {
        String tableName = "nguyento";
        ArrayList<Periodic> list = new ArrayList<Periodic>();

        Cursor cursor = database.database.query(tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            list.add(new Periodic(
                    Integer.valueOf(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.valueOf(cursor.getString(3)),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
            ));
            cursor.moveToNext();
        }

        return list;
    }

    public int getIdSb(String id) {
        String tableNameEquation = "danhsachchat";

        Cursor cursor = database.database.rawQuery(
                "SELECT id FORM danhsachchat WHERE kihieu = '" + id + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            return Integer.valueOf(cursor.getString(0));
        }
        return loadEquation().size() + 1;
    }
// lam cai moi


    private static int CheckNot(ArrayList<SbId> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).ten == id) {
                return i;
            }
        }
        return -1;
    }

    private static ArrayList<Integer> check(ArrayList<ArrayList<Integer>> list) {

        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<SbId> a = new ArrayList<SbId>();

        if (list.size() == 0) {
            return temp;
        }
        if (list.size() == 1) {
            return list.get(0);
        } else {
            //add tempkey
            for (int i = 0; i < list.size(); i++) {
                ArrayList<Integer> node = list.get(i);
                for (int j = 0; j < node.size(); j++) {
                    if (CheckNot(a, node.get(j).intValue()) == -1) {
                        a.add(new SbId(node.get(j).intValue(), 0));
                    }
                }
            }


            // calculator tempVlaue
            for (int i = 0; i < list.size(); i++) {
                ArrayList<Integer> node = list.get(i);
                for (int j = 0; j < node.size(); j++) {
                    int value = CheckNot(a, node.get(j).intValue());
                    if (value != -1) {
                        int newvalue = a.get(value).giatri + 1;
                        a.get(value).giatri = newvalue;
                    }
                }
            }
            // getlist id
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).giatri == list.size()) {

                    temp.add(a.get(i).ten);
                }
            }
        }
        return temp;
    }

    private ArrayList<Integer> check2list(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        if (list1.size() == 0)
            return list1;
        if (list2.size() == 0)
            return list2;
        for (int i = 0; i < list1.size(); i++)
            for (int j = 0; j < list2.size(); j++)
                if (list1.get(i).equals(list2.get(j))) {
                    temp.add(list1.get(i));
                }

        return temp;
    }

    private ArrayList<Integer> getIdEquationSp(String Sb) {
        ArrayList<Integer> listId = new ArrayList<Integer>();

        Cursor cursor = database.database.rawQuery("SELECT id FROM equation_product WHERE substance = '" +
                        Sb + "' GROUP BY id", null
        );

        while (cursor.moveToNext()) {
            listId.add(new Integer(Integer.valueOf(cursor.getString(0))));
        }

        return listId;
    }

    private ArrayList<Integer> getIdEquationPu(String Sb) {
        ArrayList<Integer> listId = new ArrayList<Integer>();

        Cursor cursor = database.database.rawQuery("SELECT id FROM equation_reactant WHERE substance = '" +
                        Sb + "' GROUP BY id", null
        );

        while (cursor.moveToNext()) {
            listId.add(new Integer(Integer.valueOf(cursor.getString(0))));
        }
        return listId;
    }

    public ArrayList<Integer> getListIdEquationSp(ArrayList<String> input) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < input.size(); i++)
            list.add(getIdEquationSp(input.get(i)));
        return check(list);
    }

    public ArrayList<Integer> getListIdEquationPu(ArrayList<String> input) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < input.size(); i++)
            list.add(getIdEquationPu(input.get(i)));
        return check(list);
    }

    public ArrayList<Integer> getListIdEquation(ArrayList<String> listPu, ArrayList<String> listSp) {
        if ((listSp == null && listPu == null))
            return new ArrayList<Integer>();
        else if (listPu == null)
            return getListIdEquationSp(listSp);
        else if (listSp == null)
            return getListIdEquationPu(listPu);
        else {
            if (listPu.size() == 0 || listSp.size() == 0)
                return new ArrayList<Integer>();
            return check2list(getListIdEquationPu(listPu), getListIdEquationSp(listSp));
        }
    }
}

