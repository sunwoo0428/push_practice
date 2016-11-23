package com.example.sunwo.money_book;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class statistic_cir extends AppCompatActivity {




    private ChartView mChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_statistic_cir);
        LinearLayout dynamicLayout = (LinearLayout) findViewById(R.id.chart);


        //차트를 출력하는 뷰객체(ChartView) 생성
        mChartView = new ChartView(this);

        //리니어 레이아웃에 차트뷰 추가( 폭, 높이 가득차게 )
        dynamicLayout.addView(mChartView, new LayoutParams(
        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        //차트 그리기
        mChartView.makeChart();

        TextView[] text = new TextView[mChartView.values.length];
        TextView[] color = new TextView[mChartView.values.length];
        for(int i=0;i<mChartView.values.length;i++) {
            text[i] = new TextView(this);
            text[i] = (TextView) findViewById(mChartView.texts_id[i]);
            text[i].setText(mChartView.values[i]+"원");
        }

        for(int i=0;i<mChartView.values.length;i++) {
            color[i] = new TextView(this);
            color[i] = (TextView) findViewById(mChartView.colors_id[i]);
            color[i].setBackgroundColor(mChartView.colors[i]);
        }
}

    public void onButtonbackClicked(View v){
            ChartView.k++;
            onResume();
            }

    public void onButtonforwardClicked(View v){
            if(ChartView.k>0)
            ChartView.k--;

            onResume();
            }



}


