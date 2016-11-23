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
    static int k = 0;
    DBExpense dbExpense;


    String[] texts = new String[]{"식비", "교통비", "문화생활", "생필품", "의류","기타",}; //각 계열(Series) 명
    Integer[] texts_id = {R.id.food, R.id.transport, R.id.culture, R.id.living, R.id.cloth,R.id.empty}; //각 계열(Series) 명
    Integer[] colors_id = {R.id.food_color, R.id.transport_color, R.id.culture_color, R.id.living_color, R.id.cloth_color,R.id.empty_color}; //각 계열(Series) 명
    int[] values = new int[texts.length]; //각 계열(Series)의 값

    int[] colors = new int[]{getResources().getColor(R.color.lightsalmon),getResources().getColor(R.color.lightpink) ,
            getResources().getColor(R.color.peach), getResources().getColor(R.color.PowderBlue),
            getResources().getColor(R.color.skyblue),getResources().getColor(R.color.yellowgreend)}; //각 계열(Series) 색깔



    public ChartView(Context context) {
        super(context);
        setFocusable(true);
        parent = (statistic_cir) context;

    }

    public ChartView(Context context, AttributeSet attrs) {

        super(context, attrs);
        setFocusable(true);
        parent = (statistic_cir) context;
    }

    public ChartView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        setFocusable(true);
        parent = (statistic_cir) context;
    }

    public void makeChart() {

        dbExpense = new DBExpense(parent, "money_ex.db", null, 1);

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd",
                Locale.KOREA);
        Date currentTime = new Date();
        String CurrentDate = mSimpleDateFormat.format(currentTime);
        int tempyear = Integer.parseInt(CurrentDate.substring(0, 4));
        int tempmonth = Integer.parseInt(CurrentDate.substring(4, 6));
        int tempday = Integer.parseInt(CurrentDate.substring(6, 8));

        int total = 0;
        dbExpense.get7DaysAgoDb(tempyear, tempmonth, tempday, k);
        dbExpense.get7DaysAgoGraph(tempyear, tempmonth, tempday, k);

        for (int j = 0; j < values.length; j++) {
            for (int i = 0; i < 7; i++) {
                values[j] += dbExpense.GetTotalExpenseCategorized(dbExpense.days7db[i], texts[j]);
            }
        }



        //차트에서 사용될 데이터(아이템의 값, 색깔, 텍스트)를 차트를 만들 때 필요한
        //DefaultRenderer객체와 CategorySeries객체에 세팅한다.

        CategorySeries series = new CategorySeries("계열 타이틀");
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setShowLabels(true);
        renderer.setLegendTextSize(30); //凡例の文字サイズ
        renderer.setShowLegend(false);
        //renderer.setLegendHeight(0);
        renderer.setLabelsTextSize(30);
        //renderer.setLegendTextSize(40);
        renderer.setLabelsColor(Color.BLACK);
        //renderer.setBackgroundColor(Color.RED);
        //renderer.setFitLegend(true);
        renderer.setMargins(new int[]{30, 30, 30, 30});
        //renderer.setBackgroundColor(Color.BLACK);
        renderer.setChartTitle(dbExpense.days7graph[0]+"~"+dbExpense.days7graph[6]);
        renderer.setChartTitleTextSize(50);





        for (int color : colors) {
            SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
            ssr.setColor(color);
            renderer.addSeriesRenderer(ssr);
        }

        //CategorySeries(java.lang.String title)

        int count = 0;
        for (int i = 0; i < values.length; i++)
            total += values[i];

        for (double value : values) {
            double temp = (double) ((value / total) * 100);
            //add(java.lang.String category, double value)
            series.add(texts[count++] + "(" + String.format("%.1f", temp) + "%)", value);
        }
        //PieChart 생성자 -> PieChart(CategorySeries dataset, DefaultRenderer renderer)
        mPieChart = new PieChart(series, renderer);

        //뷰에 다시 그리기
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.i("ChartView", "onDraw->Width:" + width + "/height:" + height);

        paint = new Paint();

        //mPieChart가 널이 아니면 차트가 가득차도록 그림
        if (mPieChart != null)
            mPieChart.draw(canvas, 0, 100, width, height, paint);

    }

}