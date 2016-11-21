package com.example.sunwo.money_book;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.achartengine.chart.PieChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChartView extends View {
    private PieChart mPieChart = null;
    private statistic_cir parent;
    private Paint paint;
     static int k=0;
    public ChartView(Context context) {
        super(context);
        setFocusable(true);
        parent = (statistic_cir) context;

    }

    public ChartView(Context context, AttributeSet attrs){

        super(context,attrs);
        setFocusable(true);
        parent = (statistic_cir) context;
    }

    public ChartView(Context context, AttributeSet attrs, int defaultStyle)
    {
        super(context, attrs, defaultStyle);
        setFocusable(true);
        parent = (statistic_cir) context;
    }

    public void makeChart(){

        final DBExpense dbExpense = new DBExpense(parent,"money_ex.db",null,1);

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd",
                Locale.KOREA);
        Date currentTime = new Date();
        String CurrentDate = mSimpleDateFormat.format(currentTime);
        int tempyear = Integer.parseInt(CurrentDate.substring(0,4));
        int tempmonth = Integer.parseInt(CurrentDate.substring(4,6));
        int tempday = Integer.parseInt(CurrentDate.substring(6,8));


        int[] values = new int[5]; //각 계열(Series)의 값
        int total=0;
        String[] texts = new String[] {"미등록", "교통비", "음식", "미등록" ,"의류"}; //각 계열(Series) 명

        dbExpense.get7DaysAgoDb(tempyear,tempmonth,tempday,k);
        for(int j=0;j<5;j++) {
            for (int i = 0; i < 7; i++) {
                values[j]+= dbExpense.GetTotalExpenseCategorized(dbExpense.days7db[i],texts[j]);
            }
        }



        int[] colors = new int[]{Color.BLUE, Color.RED, Color.CYAN, Color.GREEN,Color.MAGENTA}; //각 계열(Series) 색깔

        //차트에서 사용될 데이터(아이템의 값, 색깔, 텍스트)를 차트를 만들 때 필요한
        //DefaultRenderer객체와 CategorySeries객체에 세팅한다.

        CategorySeries series = new CategorySeries("계열 타이틀");
        DefaultRenderer renderer = new DefaultRenderer();

        renderer.setShowLabels(true);
        renderer.setLegendTextSize(30); //凡例の文字サイズ
        renderer.setShowLegend(false);
        //renderer.setLegendHeight(0);
        renderer.setLabelsTextSize(35);
        //renderer.setLegendTextSize(40);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setBackgroundColor(Color.WHITE);
        renderer.setFitLegend(true);
        renderer.setMargins(new int[]{30, 30, 30, 30});
        renderer.setBackgroundColor(Color.TRANSPARENT);




        for(int color:colors){
            SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
            ssr.setColor(color);
            renderer.addSeriesRenderer(ssr);
        }

        //CategorySeries(java.lang.String title)

        int count = 0;
        for(int i=0;i<5;i++)
            total+=values[i];

        for(double value: values){
            double temp = (double)((value/total)*100);

            //add(java.lang.String category, double value)
            series.add(texts[count++]+"("+String.format("%.1f",temp)+"%)",value);
        }
        //PieChart 생성자 -> PieChart(CategorySeries dataset, DefaultRenderer renderer)
        mPieChart = new PieChart(series, renderer);

        //뷰에 다시 그리기
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.i("ChartView","onDraw->Width:"+width+"/height:"+height);

        paint = new Paint();

        if (mPieChart != null) //mPieChart가 널이 아니면 차트가 가득차도록 그림
            mPieChart.draw(canvas, 0, 0, width-10, height-10, paint);
    }
}