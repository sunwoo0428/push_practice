package com.example.sunwo.money_book;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class statistic_lin extends AppCompatActivity {

    private View mChart;
    private String[] mMonth = new String[9];
    private int[] expense = new int[9];
    String CurrentDate = "";

    int tempyear = 0;
    int tempmonth = 0;
    int tempday = 0;
    int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_statistic_lin);

        final DBExpense dbExpense = new DBExpense(getApplicationContext(),"money_ex.db",null,1);

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd",
                Locale.KOREA);
        Date currentTime = new Date();
        CurrentDate = mSimpleDateFormat.format(currentTime);
        tempyear = Integer.parseInt(CurrentDate.substring(0,4));
        tempmonth = Integer.parseInt(CurrentDate.substring(4,6));
        tempday = Integer.parseInt(CurrentDate.substring(6,8));
        dbExpense.get7DaysAgoGraph(tempyear, tempmonth, tempday,k);
        for(int i=0;i<9;i++)
            mMonth[i]=dbExpense.days7graph[i];

        dbExpense.get7DaysAgoDb(tempyear,tempmonth,tempday,k);
        for(int i=0;i<7;i++){
            expense[i+1]=dbExpense.GetTotalExpense(dbExpense.days7db[i]);
        }
        expense[0]=0;expense[8]=0;

        openChart();
        setText();
    }

    private void openChart(){
        int[] x = { 0,1,2,3,4,5,6,7,8};

        // Creating an XYSeries for expense
        XYSeries expenseSeries = new XYSeries("expense");
        // Creating an XYSeries for Expense
        //XYSeries expenseSeries = new XYSeries("Expense");
        // Adding data to expense and Expense Series
        for(int i=0;i<x.length;i++){
            expenseSeries.add(i,expense[i]);

        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding expense Series to the dataset
        dataset.addSeries(expenseSeries);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(R.color.graphcolor); //color of the graph set to cyan
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);
        //expenseRenderer.setDisplayChartValuesDistance(10); //setting chart value distance

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        //multiRenderer.setChartTitle("expense ");
        multiRenderer.setXTitle("Year 2016");
        multiRenderer.setYTitle("Amount in Dollars");

        /***
         * Customizing graphs
         */
//setting text size of the title
        multiRenderer.setChartTitleTextSize(40);
        //setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(24);
        //setting text size of the graph lable
        multiRenderer.setLabelsTextSize(35);
        //setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
        //setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
        //setting click false on graph
        multiRenderer.setClickEnabled(false);
        //setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
        //setting lines to display on y axis
        multiRenderer.setShowGridY(false);
        //setting lines to display on x axis
        multiRenderer.setShowGridX(false);
        //setting legend to fit the screen size
        multiRenderer.setFitLegend(true);
        //setting displaying line on grid
        multiRenderer.setShowGrid(false);
        //setting zoom to false
        multiRenderer.setZoomEnabled(false);
        //setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
        //setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(true);
        //setting to in scroll to false
        multiRenderer.setInScroll(false);
        //setting to set legend height of the graph
        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(10);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        multiRenderer.setYAxisMax(30000);
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-0.5);
        //setting max values to be display in x axis
        multiRenderer.setXAxisMax(8);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(0.3);
        //Setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        //Setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        multiRenderer.setApplyBackgroundColor(true);

        //setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30});

        for(int i=0; i< x.length;i++){
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding expenseRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        //remove any views before u paint the chart
        chartContainer.removeAllViews();
        //drawing bar chart
        mChart = ChartFactory.getBarChartView(statistic_lin.this, dataset, multiRenderer, BarChart.Type.DEFAULT);
        //adding the view to the linearlayout
        chartContainer.addView(mChart);

    }


    public void onButtonbackClicked(View v){
        k++;
        onResume();
    }

    public void onButtonforwardClicked(View v){
        if(k>0)
            k--;

        onResume();
    }

    public void setText(){
        TextView sum = (TextView)findViewById(R.id.sum);
        TextView avg = (TextView)findViewById(R.id.avg);
        int tempt1=0,tempt2=0;
        for(int i=1;i<8;i++) {
            tempt1 += expense[i];
        }
        tempt2=tempt1/7;
        sum.setText(String.valueOf(tempt1)+"원");
        avg.setText(String.valueOf(tempt2)+"원");
    }
}
