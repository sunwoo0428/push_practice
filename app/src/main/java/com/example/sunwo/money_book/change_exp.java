package com.example.sunwo.money_book;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class change_exp extends AppCompatActivity {

    final int DIALOG_DATE = 1;
    EditText Des;
    String id="";
    EditText changedMoney;
    String exCategory="미등록";
    String exMethod = "미등록";
    String exDescription = "미등록";
    String dateString = "";
    int dateInt = 20000000;
    String tempMonth = "";
    String tempDate="";
    int exYear = 0;
    int exMonth = 0;
    int exDay = 0;
    String CurrentDate = "";
    int curryear = 0, currmonth=0,currday=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_exp);

        final DBBudget dbBudget = new DBBudget(getApplicationContext(), "money_bdg.db", null, 1);
        final DBExpense dbExpense = new DBExpense(getApplicationContext(),"money_ex.db",null,1);

        Des = (EditText) findViewById(R.id.et_description);
        changedMoney = (EditText) findViewById(R.id.et_amount);


        Intent intent = getIntent();
        id = (String)intent.getSerializableExtra("e_id");


        Button exDate = (Button) findViewById(R.id.btn_exDate);
        exDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_DATE);
            }
        });

        Button btnEXCategory = (Button)findViewById(R.id.btn_exCategory);
        btnEXCategory.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DialogSelectOptionEx();
            }
        });

        Button btnExMethod= (Button)findViewById(R.id.btn_exMethod);
        btnExMethod.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DialogSelectOptionPayEx();
            }
        });

        Button btnExDescription= (Button)findViewById(R.id.btn_exDescription);
        btnExDescription.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                exDescription = Des.getText().toString();
            }
        });

        Button btnInsertEx = (Button) findViewById(R.id.btn_exInsert);
        btnInsertEx.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(dbBudget.getBid()==2){
                    dbExpense.update("update MONEY_EX set expense='"+changedMoney.getText().toString()+"' where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set category='"+exCategory+"' where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set date="+dateInt+" where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set paymentMethod='"+exMethod+"' where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set description='"+exDescription+"' where _id ='"+id+"';");
                    int currentBudget = dbBudget.getData(6);
                    int currentRecommend = dbBudget.getData(7);
                    int updatedBudget = currentBudget - Integer.parseInt(changedMoney.getText().toString());
                    int updatedRecommend = currentRecommend - Integer.parseInt(changedMoney.getText().toString());
                    dbBudget.update("update MONEY_BUD set remain_budget = " + updatedBudget + " where _id = " + 2 + ";");
                    dbBudget.update("update MONEY_BUD set remain_recommend = " + updatedRecommend + " where _id = " + 2 + ";");
                }
                else if(dbBudget.getBid()==-1){ //예산 없을때 구현 어떻게 하지?ㄷㄷㄷ
                    dbExpense.update("update MONEY_EX set expense='"+changedMoney.getText().toString()+"' where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set category='"+exCategory+"' where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set date="+dateInt+" where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set paymentMethod='"+exMethod+"' where _id ='"+id+"';");
                    dbExpense.update("update MONEY_EX set description='"+exDescription+"' where _id ='"+id+"';");
                    Toast toast = Toast.makeText(getApplicationContext(), "등록된 예산이 없습니다", Toast.LENGTH_SHORT);
                    toast.show();
                }
                onBackPressed();
            }
        });

        Button btnExClose = (Button)findViewById(R.id.btn_exReturn);
        btnExClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void DialogSelectOptionEx() {
        final String items[] = { "미등록", "음식", "의류", "교통비" };
        AlertDialog.Builder ab = new AlertDialog.Builder(change_exp.this);
        ab.setTitle("카테고리");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        exCategory = items[whichButton];
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        ab.show();
    }

    private void DialogSelectOptionPayEx() {
        final String items[] = { "미등록", "카드", "현금", "통장" };
        AlertDialog.Builder ab = new AlertDialog.Builder(change_exp.this);
        ab.setTitle("카테고리");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        exMethod = items[whichButton];
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        ab.show();
    }

    protected Dialog onCreateDialog(int id){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date currentTime = new Date();
        CurrentDate = mSimpleDateFormat.format(currentTime);
        curryear = Integer.parseInt(CurrentDate.substring(0,4));
        currmonth = Integer.parseInt(CurrentDate.substring(4,6));
        currday = Integer.parseInt(CurrentDate.substring(6,8));
        DatePickerDialog dpd = new DatePickerDialog(change_exp.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Toast.makeText(getApplicationContext(),year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+ "일", Toast.LENGTH_SHORT).show();
//                exYear = year;
                exMonth = monthOfYear+1;
                exDay = dayOfMonth;
                if(monthOfYear<9)
                    tempMonth = "0"+exMonth;
                else
                    tempMonth = ""+exMonth;

                if(exDay<10)
                    tempDate = "0"+exDay;
                else
                    tempDate = ""+exDay;

                dateString = ""+year+""+tempMonth+tempDate;

                dateInt = Integer.parseInt(dateString);
            }
        },curryear,currmonth-1,currday);
        return dpd;
    }
}
