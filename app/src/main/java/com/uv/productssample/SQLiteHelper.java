package com.uv.productssample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by venkatsr on 21/11/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PRODUCT_CATALOG_DB_NEW.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "PRODUCT_NEW1";
    private static final String COL_PRODUCT_NAME = "PRODUCT_NAME";
    private static final String COL_PRODUCT_DESC = "PRODUCT_DESC";
    private static final String COL_ID = "ID";

    SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("DBHelper", "Constructor executed");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DBHelper", "Table Creation");
        String create_table_ddl = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_PRODUCT_NAME + " TEXT, " + COL_PRODUCT_DESC + " TEXT);";
        db.execSQL(create_table_ddl);
        Log.e("DBHelper", "Table Creation done" + create_table_ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertProduct(String productName, String productDescription) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, productName);
        values.put(COL_PRODUCT_DESC, COL_PRODUCT_DESC);
        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public String[] getAllProducts() {
        ArrayList<String> allProducts = new ArrayList<String>();
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        String selectProductsQuery = "select * from " + TABLE_NAME;
        Cursor cursor = database.rawQuery(selectProductsQuery, null);
        Log.e("DBHelper", "After rawQuery");
        if(cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String productName = cursor.getString(1);
                allProducts.add(productName);
            } while (cursor.moveToNext());
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        String[] stringArray = allProducts.toArray(new String[0]);
        return stringArray;
    }

    public boolean isTableExists(String tableName, boolean openDb) {
        if(openDb) {
            if(database == null || !database.isOpen()) {
                database = getReadableDatabase();
            }

            if(!database.isReadOnly()) {
                database.close();
                database = getReadableDatabase();
            }
        }

        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}