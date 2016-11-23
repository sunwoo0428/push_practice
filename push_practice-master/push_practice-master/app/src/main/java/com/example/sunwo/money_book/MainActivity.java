package com.example.sunwo.money_book;

import android.Manifest;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TabHost;


public class MainActivity extends TabActivity {
    static int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    public static Context context;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        RECEIVEpermission();
        READpermission();
        TabHost.TabSpec spec;
        Intent intent;
        context = this;

        final DBExpense dbExpense = new DBExpense(getApplicationContext(), "money_ex.db", null, 1);
        final DBIncome dbIncome = new DBIncome(getApplicationContext(), "money_in.db", null, 1);
        final DBBudget dbBudget = new DBBudget(getApplicationContext(), "money_bdg.db", null, 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources res = getResources();
        TabHost mTabHost = getTabHost();

        intent = new Intent().setClass(this, mainscreen.class);
        spec = mTabHost.newTabSpec("tab0").setIndicator("메인화면").setContent(intent);
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, inc_exp.class);
        spec = mTabHost.newTabSpec("tab1").setIndicator("수입지출").setContent(intent);
        mTabHost.addTab(spec);

        intent = new Intent().setClass(this, statistic.class);
        spec = mTabHost.newTabSpec("tab2").setIndicator("통계").setContent(intent);
        mTabHost.addTab(spec);


        try{
            SharedPreferences mPref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE);
            Boolean bFirst = mPref.getBoolean("isFirst", false);
            if(bFirst == false)
            {
                Log.d("version", "first");
                SharedPreferences.Editor editor = mPref.edit();
                editor.putBoolean("isFirst", true);
                editor.commit();

                dbExpense.drop("DROP TABLE IF EXISTS MONEY_EX");
                dbExpense.createTable("CREATE TABLE MONEY_EX( _id INTEGER PRIMARY KEY AUTOINCREMENT, expense INTEGER, category TEXT," +
                        " date INTEGER, paymentMethod TEXT, description TEXT);");
                dbIncome.drop("DROP TABLE IF EXISTS MONEY_IN");
                dbIncome.createTable("CREATE TABLE MONEY_IN( _id INTEGER PRIMARY KEY AUTOINCREMENT, income INTEGER, category TEXT," +
                        " date INTEGER, paymentMethod TEXT, description TEXT);");
                dbBudget.drop("DROP TABLE IF EXISTS MONEY_BUD");
                dbBudget.createTable("CREATE TABLE MONEY_BUD( _id INTEGER PRIMARY KEY AUTOINCREMENT, budget INTEGER, period INTEGER);");
                dbBudget.insert("insert into MONEY_BUD values(null, "+50000+", "+5+");");

                intent = new Intent().setClass(this, budget_view.class);
                spec = mTabHost.newTabSpec("tab3").setIndicator("예산").setContent(intent);
                mTabHost.addTab(spec);
            }
            if(bFirst == true)
            {
                Log.d("version", "not first");
                if(dbBudget.getBid()==2){
                    intent = new Intent().setClass(this, budget_view.class);
                    spec = mTabHost.newTabSpec("tab3").setIndicator("예산").setContent(intent);
                    mTabHost.addTab(spec);
                    mTabHost.setCurrentTab(3);
                }
                else{
                    intent = new Intent().setClass(this, budget_new.class);
                    spec = mTabHost.newTabSpec("tab3").setIndicator("예산").setContent(intent);
                    mTabHost.addTab(spec);
                    mTabHost.setCurrentTab(3);
                }
            }

        }finally{};


        intent = new Intent().setClass(this, settings.class);
        spec = mTabHost.newTabSpec("tab4").setIndicator("설정").setContent(intent);
        mTabHost.addTab(spec);

        if(temp.count == 0) {
            mTabHost.setCurrentTab(0);
            temp.count++;
        }

    }

    public void RECEIVEpermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void READpermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
