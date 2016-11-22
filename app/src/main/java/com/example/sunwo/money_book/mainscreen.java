package com.example.sunwo.money_book;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class mainscreen extends AppCompatActivity {

    int type = 2;
    int adapterSelect = 0;
    String typeString[] = {"","미등록"};
    String dateTemp[] = {"",""};
    int Date[] = {20000000,20000000};
    final int DIALOG_START_DATE = 0;
    final int DIALOG_END_DATE = 1;
    int exMonth = 0;
    int exDay = 0;
    String tempMonth = "";
    String tempDate = "";
    String dateString = "";
    int dateInt = 20000000;
    int listViewCount = 0;
    TextView typeselected;
    TextView start_date;
    TextView end_date;
    String CurrentDate = "";
    int curryear = 0;
    int currmonth = 0;
    int currday = 0;
    DBExpense dbExpense;
    DBIncome dbIncome;
    ListViewAdapterEx adapter1;
    ListViewAdapterIn adapter2;
    AdapterView parent1, parent2;
    int position1, position2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        dbExpense = new DBExpense(getApplicationContext(), "money_ex.db", null, 1);
        dbIncome = new DBIncome(getApplicationContext(), "money_in.db", null, 1);

        ListView listview1, listview2 ;
        final TextView testing = (TextView)findViewById(R.id.notyet1);
        typeselected = (TextView)findViewById(R.id.type1);
        start_date = (TextView)findViewById(R.id.start_date);
        end_date = (TextView)findViewById(R.id.end_date);

        // Adapter 생성
        adapter1 = new ListViewAdapterEx() ;
        adapter2 = new ListViewAdapterIn();

        // 리스트뷰 참조 및 Adapter달기
        listview1 = (ListView) findViewById(R.id.listview1);
        listview1.setAdapter(adapter1);
        listview2 = (ListView)findViewById(R.id.listview2);
        listview2.setAdapter(adapter2);

        Button btnSelect = (Button) findViewById(R.id.select_type);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogSelectType();
            }
        });

        Button btnSelectStartDate = (Button) findViewById(R.id.select_date_start);
        btnSelectStartDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_START_DATE);
            }
        });

        Button btnSelectEndDate = (Button) findViewById(R.id.select_date_end);
        btnSelectEndDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DIALOG_END_DATE);
            }
        });

        Button btnFind = (Button) findViewById(R.id.find);
        btnFind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(type ==0){
                    adapter1.deleteAll();
                    adapter2.deleteAll();
                    listViewCount = dbExpense.searchExpense(Date[0],Date[1]);
                    for(int i = 0;i<listViewCount;i++){
                        adapter1.addItem(dbExpense.expenseStructs[i].getId(),dbExpense.expenseStructs[i].getAmount(), dbExpense.expenseStructs[i].getDate(),
                                dbExpense.expenseStructs[i].getCategory(), dbExpense.expenseStructs[i].getMethod(), dbExpense.expenseStructs[i].getDescription()) ;
                    }
                }
                else if(type == 1){
                    adapter1.deleteAll();
                    adapter2.deleteAll();
                    listViewCount = dbIncome.searchIncome(Date[0],Date[1]);
                    for(int i = 0;i<listViewCount;i++){
                        adapter2.addItem(dbIncome.incomeStructs[i].getId(),dbIncome.incomeStructs[i].getAmount(), dbIncome.incomeStructs[i].getDate(),
                                dbIncome.incomeStructs[i].getCategory(), dbIncome.incomeStructs[i].getMethod(), dbIncome.incomeStructs[i].getDescription()) ;
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "지출 또는 수입을 선택하시오", Toast.LENGTH_SHORT);
                    toast.show();
                }
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });

        Button btnRefresh = (Button) findViewById(R.id.refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(type ==0){
                    adapter1.deleteAll();
                    adapter2.deleteAll();
                    listViewCount = dbExpense.searchExpense(Date[0],Date[1]);
                    for(int i = 0;i<listViewCount;i++){
                        adapter1.addItem(dbExpense.expenseStructs[i].getId(),dbExpense.expenseStructs[i].getAmount(), dbExpense.expenseStructs[i].getDate(),
                                dbExpense.expenseStructs[i].getCategory(), dbExpense.expenseStructs[i].getMethod(), dbExpense.expenseStructs[i].getDescription()) ;
                    }
                }
                else if(type == 1){
                    adapter1.deleteAll();
                    adapter2.deleteAll();
                    listViewCount = dbIncome.searchIncome(Date[0],Date[1]);
                    for(int i = 0;i<listViewCount;i++){
                        adapter2.addItem(dbIncome.incomeStructs[i].getId(),dbIncome.incomeStructs[i].getAmount(), dbIncome.incomeStructs[i].getDate(),
                                dbIncome.incomeStructs[i].getCategory(), dbIncome.incomeStructs[i].getMethod(), dbIncome.incomeStructs[i].getDescription()) ;
                    }
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "지출 또는 수입을 선택하시오", Toast.LENGTH_SHORT);
                    toast.show();
                }
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });


        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                parent1 = parent;
                position1  = position;
                Listview_expense item = (Listview_expense) parent.getItemAtPosition(position) ;
                String description = item.getDescription();
                alert(description);
            }
        }) ;

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                parent2 = parent;
                position2  = position;
                Listview_income item = (Listview_income) parent.getItemAtPosition(position) ;
                String description = item.getDescription();
                alert(description);
            }
        }) ;

    }

    private void alert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("상세설명")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        adapterSelect = 0;
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("수정", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        if(type==0){
                            Listview_expense item = (Listview_expense) parent1.getItemAtPosition(position1) ;
                            String e_id = item.getE_id();
                            Intent intent = new Intent(mainscreen.this, change_exp.class);
                            intent.putExtra("e_id", e_id);
                            startActivity(intent);
                        }
                        else{
                            Listview_income item = (Listview_income) parent2.getItemAtPosition(position2) ;
                            String i_id = item.getI_id();
                            Intent intent = new Intent(mainscreen.this, change_inc.class);
                            intent.putExtra("i_id", i_id);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("삭제", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        if(type == 0){
                            Listview_expense item = (Listview_expense) parent1.getItemAtPosition(position1) ;
                            String e_id = item.getE_id();
                            dbExpense.delete("delete from MONEY_EX where _id ="+e_id+";");
                            Toast toast = Toast.makeText(getApplicationContext(), "선택한 지출이 삭제되었습니다", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            Listview_income item = (Listview_income) parent2.getItemAtPosition(position2) ;
                            String i_id = item.getI_id();
                            dbIncome.delete("delete from MONEY_IN where _id ="+i_id+";");
                            Toast toast = Toast.makeText(getApplicationContext(), "선택한 수입이 삭제되었습니다", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void DialogSelectType() {
        final String items[] = { "지출", "수입" };
        AlertDialog.Builder ab = new AlertDialog.Builder(mainscreen.this);
        ab.setTitle("지출/수입");
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        type = whichButton;
                        typeString[0] = items[type];
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        typeselected.setText(typeString[0]);
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        ab.show();
    }

    protected Dialog onCreateDialog(final int id){
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date currentTime = new Date();
        CurrentDate = mSimpleDateFormat.format(currentTime);
        curryear = Integer.parseInt(CurrentDate.substring(0,4));
        currmonth = Integer.parseInt(CurrentDate.substring(4,6));
        currday = Integer.parseInt(CurrentDate.substring(6,8));
        DatePickerDialog dpd = new DatePickerDialog(mainscreen.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Toast.makeText(getApplicationContext(),year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+ "일", Toast.LENGTH_SHORT).show();
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
                dateTemp[id] = dateString;

                dateInt = Integer.parseInt(dateString);
                Date[id] = dateInt;

                if(id == 0)
                    start_date.setText(dateTemp[0]);
                else
                    end_date.setText(dateTemp[1]);
            }
        },curryear,currmonth-1,currday);


        return dpd;
    }

}
