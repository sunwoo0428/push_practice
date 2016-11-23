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

        db.execSQL("CREATE TABLE MONEY_BUD( _id INTEGER PRIMARY KEY AUTOINCREMENT, budget INTEGER, " +
                "period INTEGER, date INTEGER, day INTEGER, recommend INTEGER, remain_total, remain_recommend);");

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

        int now_budget = cursor.getInt(6);
        float temp = cursor.getFloat(6)/cursor.getFloat(1) * 100;
        String percentage = String.format("%.1f", temp);
        str =  " 총 남은 돈 : "+ now_budget + " 원  (" + percentage + "%)\n";
        return str;
    }
    public String PrintDataRecommend(int index) {

        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from MONEY_BUD", null);
        cursor.moveToNext();
        cursor.moveToNext();

        float temp = cursor.getFloat(7)/cursor.getFloat(5) * 100;
        String percentage = String.format("%.1f", temp);
            str =  " 오늘의 돈 : "
                    + cursor.getInt(index) + " 원 (" + percentage + "%)\n";
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