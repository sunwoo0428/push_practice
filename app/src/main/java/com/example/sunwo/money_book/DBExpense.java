package com.example.sunwo.money_book;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DBExpense extends SQLiteOpenHelper {


    public ExpenseStruct[] expenseStructs = new ExpenseStruct[100];
    public String[] days7db = new String[7];
    public String[] days7graph = new String[9];



    public DBExpense(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE MONEY_EX( _id INTEGER PRIMARY KEY AUTOINCREMENT, expense INTEGER, category TEXT, " +
                "date INTEGER, paymentMethod TEXT, description TEXT);");
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
    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select * from MONEY_EX", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " : expense "
                    + cursor.getInt(1)
                    + ", category = "
                    + cursor.getString(2)
                    + ", Date = "
                    + cursor.getInt(3)
                    + ", "
                    + cursor.getString(4)
                    +", "
                    + cursor.getString(5)
                    + "\n";
        }
        return str;
    }

    public int sevenDayExpense(String startDate, String endDate){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MONEY_EX where date >= '"+startDate+"' and date <='"+ endDate+"'", null);
        int Expense7 = 0;
        while(cursor.moveToNext()){
            Expense7 += cursor.getInt(1);
        }
        return Expense7;
    }
    public int oneMonthExpense(int year, int month, String currentDate){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MONEY_EX where date >= "+year+month+"00 and date <='"+ currentDate+"'", null);
        int ExpenseMonth = 0;
        while(cursor.moveToNext()){
            ExpenseMonth += cursor.getInt(1);
        }
        return ExpenseMonth;
    }

    public int searchExpense(int startDate, int endDate){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from MONEY_EX where date >= "+startDate+" and date <="+ endDate+"", null);
        int count = 0;
        for(int i=0; i<expenseStructs.length; i++){
            expenseStructs[i] = new ExpenseStruct();
        }

        while(cursor.moveToNext()){
            String tempId = String.valueOf(cursor.getInt(0));
            String tempAmount = String.valueOf(cursor.getInt(1));
            String tempDate = String.valueOf(cursor.getInt(3));

            expenseStructs[count].setId(tempId);

            expenseStructs[count].setAmount(tempAmount);
            expenseStructs[count].setCategory(cursor.getString(2));
            expenseStructs[count].setDate(tempDate);
            expenseStructs[count].setMethod(cursor.getString(4));
            expenseStructs[count].setDescription(cursor.getString(5));
            count++;
        }
        return count;
    }

    public int GetTotalExpense(String selectedDate){
        SQLiteDatabase db = getReadableDatabase();
        int totalExpense = 0;
        int dateInt = Integer.parseInt(selectedDate);
        Cursor cursor = db.rawQuery("select expense from MONEY_EX where date ="+dateInt, null);
        while(cursor.moveToNext()){
            totalExpense += cursor.getInt(0);
        }
        return totalExpense;
    }


    public int GetTotalExpenseCategorized(String selectedDate,String category){
        SQLiteDatabase db = getReadableDatabase();
        int totalExpense = 0;
        int dateInt = Integer.parseInt(selectedDate);
        Cursor cursor = db.rawQuery("select expense from MONEY_EX where date ="+dateInt+" and category ='"+category+"'", null);
        while(cursor.moveToNext()){
            totalExpense += cursor.getInt(0);
        }
        return totalExpense;
    }



    public void get7DaysAgoDb(int year , int month , int day,int k) {
        for(int i = 6;i>=0;i--){
            Calendar cal = Calendar
                    .getInstance();
            cal.set(year, month-1, day);
            cal.add(Calendar.DATE, -i-7*k);


            java.util.Date weekago = cal.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                    Locale.getDefault());
            days7db[6-i] = formatter.format(weekago);
        }
    }

    public void get7DaysAgoGraph(int year , int month , int day,int k) {
        for (int i=0;i<9;i++)
            days7graph[i]= new String();

        for(int i = 6;i>=0;i--){
            Calendar cal = Calendar
                    .getInstance();
            cal.set(year, month-1, day);
            cal.add(Calendar.DATE, -i-7*k);

            java.util.Date weekago = cal.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd",
                    Locale.getDefault());
            days7graph[7-i] = formatter.format(weekago);
        }

        days7graph[0]="";
        days7graph[8]="";

    }

 /*   public int GetWeekExpense(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select expense from MONEY_EX where ")
    }

    public int GetMonthExpense(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select expense from MONEY_EX where ")
    } */
}