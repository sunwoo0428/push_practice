package com.example.sunwo.money_book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class budget_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_view);

        final DBBudget dbBudget = new DBBudget(getApplicationContext(), "money_bdg.db", null, 1);
        final TextView bdgResult = (TextView) findViewById(R.id.total_bdg);
        final TextView recResult = (TextView) findViewById(R.id.recommend_mny);

        bdgResult.setText( dbBudget.PrintDataBudget() );
        recResult.setText( dbBudget.PrintDataRecommend(5) );

    }

    @Override
    protected void onResume(){
        super.onResume();

        final DBBudget dbBudget = new DBBudget(getApplicationContext(), "money_bdg.db", null, 1);
        final TextView bdgResult = (TextView) findViewById(R.id.total_bdg);
        final TextView recResult = (TextView) findViewById(R.id.recommend_mny);
        Button btnDropBdg = (Button) findViewById(R.id.btn_dropBdg);
        ImageButton imgbtnPrevious = (ImageButton) findViewById(R.id.previous);
        ImageButton imgbtnNext = (ImageButton) findViewById(R.id.next);

        bdgResult.setText( dbBudget.PrintDataBudget() );
        recResult.setText( dbBudget.PrintDataRecommend(7) );

        btnDropBdg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(budget_view.this, MainActivity.class);
                startActivity(intent);

                dbBudget.drop("DROP TABLE IF EXISTS MONEY_BUD");
                dbBudget.createTable("CREATE TABLE MONEY_BUD( _id INTEGER PRIMARY KEY AUTOINCREMENT, budget INTEGER, " +
                        "period INTEGER, date INTEGER, day INTEGER, recommend INTEGER, remain_budget INTEGER, remain_recommend INTEGER);");
                dbBudget.insert("insert into MONEY_BUD values(null, 50000, 5, 20000101, 0, 0, 0, 0);");

                bdgResult.setText("");
                recResult.setText("");

            }
        });

        imgbtnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dbBudget.update("update MONEY_BUD set day = day + 1 where _id = 2;");
                int remaining = dbBudget.getData(6) / (dbBudget.getData(2) - dbBudget.getData(4));
                dbBudget.update("update MONEY_BUD set recommend = " + remaining + " where _id = 2;");
                dbBudget.update("update MONEY_BUD set remain_recommend = " + remaining + " where _id = 2;");
                bdgResult.setText( dbBudget.PrintDataBudget() );
                recResult.setText( dbBudget.PrintDataRecommend(5) );
            }
        });

        imgbtnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dbBudget.update("update MONEY_BUD set day = day - 1 where _id = 2;");
                int remaining = dbBudget.getData(6) / (dbBudget.getData(2) - dbBudget.getData(4));
                dbBudget.update("update MONEY_BUD set recommend = " + remaining + " where _id = 2;");
                dbBudget.update("update MONEY_BUD set remain_recommend = " + remaining + " where _id = 2;");
                bdgResult.setText( dbBudget.PrintDataBudget() );
                recResult.setText( dbBudget.PrintDataRecommend(5) );
            }
        });

    }

}
