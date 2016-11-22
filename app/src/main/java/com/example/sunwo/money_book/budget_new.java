package com.example.sunwo.money_book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class budget_new extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_new);

        final DBBudget dbBudget = new DBBudget(getApplicationContext(), "money_bdg.db", null, 1);

        final EditText etBudget = (EditText) findViewById(R.id.et_budget);
        final EditText etPeriod = (EditText) findViewById(R.id.et_period);

        Button btnInsertBdg = (Button) findViewById(R.id.btn_insertBdg);
        btnInsertBdg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String budget = etBudget.getText().toString();
                String period = etPeriod.getText().toString();

                if(budget.equals("")||period.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "예산/기간을 모두 입력하시오", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Date currentTime = new Date( );
                    int now = dbBudget.DatetoInt(currentTime);
                    dbBudget.insert("insert into MONEY_BUD values(null, "+budget+", "+period+", "+now+", "+0+", "+0+");");
                    int recommend = Integer.parseInt(budget)/Integer.parseInt(period);
                    dbBudget.update("update MONEY_BUD set recommend = " + recommend + " where _id = " + 2 + ";");
                    Intent intent = new Intent(budget_new.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
