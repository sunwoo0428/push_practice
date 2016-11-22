package com.example.sunwo.money_book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBBudget extends SQLiteOpenHelper {

    public DBBudget(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블을 생성한다.
        // cr)eate table 테이블명 (컬럼명 타입 옵션;
        db.execSQL("CREATE TABLE MONEY_BUD( _id INTEGER PRIMARY KEY AUTOINCREMENT, budget INTEGER, period INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createTable(String _query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }
    public void drop(String _query){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public String PrintDataBudget() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from MONEY_BUD", null);
        cursor.moveToNext();
        cursor.moveToNext();
        str =  " 총 남은 돈 = "+ cursor.getInt(1) + "\n";
        return str;
    }
    public String PrintDataRecommend() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from MONEY_BUD", null);
        cursor.moveToNext();
        cursor.moveToNext();
            str =  " 오늘의 돈 = "
                    + cursor.getInt(5) + "\n";
        return str;
    }

    public int getBid() {
        SQLiteDatabase db = getReadableDatabase();
        int bid = 0;

        Cursor countT = db.rawQuery("select * from MONEY_BUD", null);
        while(countT.moveToNext()){
            bid = countT.getInt(0);
        }
        if(bid == 1)
            return -1;
        else
            return 2;

    }

    public int getData(int index) {
        SQLiteDatabase db = getReadableDatabase();
        int data = 0;

        Cursor cursor = db.rawQuery("select * from MONEY_BUD", null);
        cursor.moveToNext();
        cursor.moveToNext();
        data = cursor.getInt(index);
        return data;
    }

    public int DatetoInt(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String dTime = formatter.format(date);
        int result = Integer.parseInt(dTime);

        return result;
    }

}