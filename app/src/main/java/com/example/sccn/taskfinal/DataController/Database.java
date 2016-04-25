package com.example.sccn.taskfinal.DataController;

import android.database.sqlite.SQLiteDatabase;


/**
 * Created by SCCN on 10/06/2015.
 */
public class Database {
    private static String DB_PATH = "/data/data/com.example.sccn.taskfinal/database/";
    private static String DB_NAME = "data.db";
    SQLiteDatabase database;

    public Database() {

        database = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }

}
