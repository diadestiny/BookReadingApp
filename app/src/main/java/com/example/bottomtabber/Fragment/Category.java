package com.example.bottomtabber.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomtabber.Activity.Login;
import com.example.bottomtabber.Data.Book;
import com.example.bottomtabber.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

public class Category extends Fragment {
    private View view;
    private List<Book> allBooks;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_category, container, false);
        if(LitePal.where("user_name=?", Login.loginUser.getUsername()).find(Book.class).isEmpty()){
            allBooks = new ArrayList<>();
        }else{
            allBooks = LitePal.where("user_name=?", Login.loginUser.getUsername()).order("name").find(Book.class);
        }
        initView();
        return view;
    }

    public void initView(){
        BarChart barChart = view.findViewById(R.id.barChart);
        if (barChart !=null){
            initBarChart(barChart);
            BarData barData = setBarData();
            barChart.setData(barData);
        }
    }

    public BarData setBarData(){
        List<BarEntry> entries = new ArrayList<>();

        for(int i=0;i<allBooks.size();i++){
            entries.add(new BarEntry(i,allBooks.get(i).getSum()));
        }
        BarDataSet barDataSet = new BarDataSet(entries,"书籍浏览次数");
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f);
        return barData;
    }

    public void initBarChart(BarChart barChart){
        Description d =barChart.getDescription();
        d.setText("书籍浏览统计图");
        d.setTextSize(10f);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        setAxis(barChart.getXAxis(),barChart.getAxisLeft(),barChart.getAxisRight());
    }
    public void setAxis(XAxis xAxis,YAxis leftYAxis,YAxis rightYAxis){
        List<String> xValue = new ArrayList<>();
        for(int i=0;i<allBooks.size();i++){
            xValue.add(allBooks.get(i).getName());
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);
        xAxis.setTextSize(2f);
        xAxis.setLabelRotationAngle(-40);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));//设置x轴标签格式化器

        leftYAxis.setAxisMinimum(0);
        leftYAxis.setDrawGridLines(false);
        leftYAxis.setDrawAxisLine(false);

        rightYAxis.setAxisMinimum(0);
        rightYAxis.setDrawGridLines(false);
        rightYAxis.setDrawAxisLine(false);
    }




}
