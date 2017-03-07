package com.example.dennislam.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dennislam.myapplication.R;
import com.example.dennislam.myapplication.adapter.SalaryCheckListViewAdapter;
import com.example.dennislam.myapplication.dao.GraphInfoDao;
import com.example.dennislam.myapplication.dao.SalaryResultDao;
import com.example.dennislam.myapplication.xml.GraphInfoXML;
import com.example.dennislam.myapplication.xml.SalaryResultXML;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalaryCheckResultActivity extends BaseActivity {

    private ListView listView;

    public static List<BarEntry> entries = new ArrayList<>();
    public static List<BarEntry> entries2 = new ArrayList<>();
    boolean firstuse =true;
    public BarChart barChart;
    public static int total= 0;
    public double percent=0;
    public int sourceType= 0;
    private double itemcount;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0.00");
    private TextView sdtv,headerValue;

    SalaryResultDao salaryResultItemDao = new SalaryResultDao();
    GraphInfoDao graphInfoItemDao = new GraphInfoDao();

    String jobTitle, workExpFrom, workExpTo;
    Boolean withSimilarWord;
    int itemsTotal;

    List<SalaryResultXML.SalaryResultItem> salaryResultItemList = new ArrayList<SalaryResultXML.SalaryResultItem>();
    List<GraphInfoXML.GraphInfoItem> graphInfoItemList = new ArrayList<GraphInfoXML.GraphInfoItem>();

    ArrayList<Integer> medSalaryArray = new ArrayList<Integer>();

    ArrayList<String> labelArray = new ArrayList<String>();
    ArrayList<String> countArray = new ArrayList<String>();
    ArrayList<String> sourceTypeArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_salary_check_result, null, false);
        mDrawer.addView(contentView, 0);


         sdtv =(TextView)findViewById(R.id.salarydistributionTV);
//       sdtv.setText(SalaryCheckSearchActivity.sdtvName+" 's Salary Distribution Chart");
        listView = (ListView)findViewById(R.id.list_view);
        Button relevantDataButton = (Button)findViewById(R.id.relevantDataButton);



        relevantDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RelevantDataActivity.class);
                intent.putExtra("jobTitle", jobTitle);
                intent.putExtra("withSimilarWord", withSimilarWord);
                intent.putExtra("workExpFrom", workExpFrom);
                intent.putExtra("workExpTo", workExpTo);
                startActivity(intent);
            }
        });

        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        if(b!=null) {
            jobTitle =(String) b.get("jobTitle");
            withSimilarWord =(Boolean) b.get("withSimilarWord");
            workExpFrom =(String) b.get("workExpFrom");
            workExpTo =(String) b.get("workExpTo");
        }

        //Run the code if there are network connected
        if(globalVariable.getNetwork() == true){
            new GetSalaryCheckResultAsyncTaskRunner().execute();
            new GetGraphInfoAsyncTaskRunner().execute();
        }



        barChart = (BarChart) findViewById(R.id.barChart);
        final  String[] salaryIndex = new String[] {"10k-20k", "20k-30k", "30k-40k","40k-50k","50k-60K","60k-70k","70k-80k" ,"80k-90k","90k-100K",">100k"};

        IAxisValueFormatter moneyFormatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return salaryIndex[(int)value];
            }

            @Override
            public int getDecimalDigits() {  return 1; }
        };

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(moneyFormatter);
        xAxis.setGranularity(1f);

        barChart.getDescription().setEnabled(false);


        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        barChart.getAxisLeft().setValueFormatter(new PercentFormatter());
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                Intent myintent = new Intent(getBaseContext(),LandScapeBarChart.class);
                startActivity(myintent);
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

        Legend legendBarChart = barChart.getLegend();
        legendBarChart.setOrientation(Legend.LegendOrientation.VERTICAL);
        legendBarChart.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);



    }


    class GetSalaryCheckResultAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            System.out.println(jobTitle);

            if(salaryResultItemList == null) {
                Toast.makeText(getBaseContext(), "nothing", Toast.LENGTH_LONG).show();
            } else {
                salaryResultItemList = salaryResultItemDao.getSalaryResultItemDao(jobTitle, withSimilarWord, workExpFrom, workExpTo);
            }

            itemsTotal = salaryResultItemDao.getItemsTotal();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            View header = (View)getLayoutInflater().inflate(R.layout.salary_check_result_listview_header,null);
            headerValue = (TextView) header.findViewById(R.id.txtHeader);
            listView.removeHeaderView(header);
            if(salaryResultItemList == null) {
                Toast.makeText(getBaseContext(), "nothing", Toast.LENGTH_LONG).show();
                headerValue.setText("Result not find");
            } else {
                headerValue.setText(jobTitle);
                for(int i = 0; i < salaryResultItemList.size(); i++) {
                    medSalaryArray.add(i, Integer.parseInt(salaryResultItemList.get(i).getMedSalary()));
                }
                listView.addHeaderView(header);
                Collections.sort(medSalaryArray);

                int mid = medSalaryArray.size()/2;
                int median = (int)medSalaryArray.get(mid);
                if (medSalaryArray.size()%2 == 0) {
                    median = (median + (int)medSalaryArray.get(mid-1))/2;
                }

                String resultMedSalary = "Median  : $" + median;
                String resultMaxSalary = "Highest : $" + salaryResultItemList.get(0).getMaxSalary();
                String resultMinSalary = "Lowest  : $" + salaryResultItemList.get(0).getMinSalary();

                //Set ListView Item
                //Header


                //Row
                String[] list = {resultMinSalary,resultMedSalary,resultMaxSalary};
                listView.setAdapter(new SalaryCheckListViewAdapter(getBaseContext(), list));

                System.out.println(itemsTotal);
                Log.v("Testing steps", "Salary Check : Got Salary Check Result");
            }


        }
    }







    class GetGraphInfoAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            graphInfoItemList = graphInfoItemDao.getGraphInfoItemDao(jobTitle);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            for(int i = 0; i < graphInfoItemList.size(); i++){
                labelArray.add(i, graphInfoItemList.get(i).getLabel());
                countArray.add(i, graphInfoItemList.get(i).getCount());
                sourceTypeArray.add(i, graphInfoItemList.get(i).getSourceType());

                System.out.println(labelArray.get(i));
                System.out.println(countArray.get(i));
                System.out.println(sourceTypeArray.get(i));
            }
              entries.clear();
              entries2.clear();
            for(int i = 0; i < graphInfoItemList.size(); i++){
                total = total + Integer.parseInt(graphInfoItemList.get(i).getCount());
                sourceType=Integer.parseInt(graphInfoItemList.get(i).getSourceType());
                switch (graphInfoItemList.get(i).getLabel()){
                    case "10000 to 19999":
                        if (sourceType==1){
                            addEntries1(i,0);
                        }else if (sourceType==2){
                            addEntries2(i,0);
                        }
                        break;
                    case "20000 to 29999":
                        if (sourceType==1){
                            addEntries1(i,1);
                        }else if (sourceType==2){
                            addEntries2(i,1);
                        }
                        break;
                    case "30000 to 39999":
                        if (sourceType==1){
                            addEntries1(i,2);
                        }else if (sourceType==2){
                            addEntries2(i,2);
                        }
                        break;
                    case "40000 to 49999":
                        if (sourceType==1){
                            addEntries1(i,3);
                        }else if (sourceType==2){
                            addEntries2(i,3);
                        }
                        break;
                    case "50000 to 59999":
                        if (sourceType==1){
                            addEntries1(i,4);
                        }else if (sourceType==2){
                            addEntries2(i,4);
                        }
                        break;
                    case "60000 to 69999":
                        if (sourceType==1){
                            addEntries1(i,5);
                        }else if (sourceType==2){
                            addEntries2(i,5);
                        }
                        break;
                    case "70000 to 79999":
                        if (sourceType==1){
                            addEntries1(i,6);
                        }else if (sourceType==2){
                            addEntries2(i,6);
                        }
                        break;
                    case "80000 to 89999":
                        if (sourceType==1){
                            addEntries1(i,7);
                        }else if (sourceType==2){
                            addEntries2(i,7);
                        }
                        break;
                    case "90000 to 99999":
                        if (sourceType==1){
                            addEntries1(i,8);
                        }else if (sourceType==2){
                            addEntries2(i,8);
                        }
                        break;
                    case ">100000":
                        if (sourceType==1){
                            addEntries1(i,9);
                        }else if (sourceType==2){
                            addEntries2(i,9);
                        }
                        break;
                }
            }

            BarDataSet set1 = new BarDataSet(entries, "Labour");
            BarDataSet set2 = new BarDataSet(entries2,"Employer");

            set1.setColors(Color.parseColor("#81DAF5"));
            set2.setColor(Color.parseColor("#5858FA"));
            float groupSpace = 0.06f;
            float barSpace = 0.02f; // x2 dataset
            float barWidth = 0.45f; // x2 dataset

            BarData data = new BarData(set1,set2);
            data.setBarWidth(barWidth);
            barChart.setData(data);

            barChart.setFitBars(true);

            barChart.groupBars(-0.5f, groupSpace, barSpace);
            barChart.invalidate();

        }
    }




    public void addEntries1 (int i,float position){
        itemcount = Integer.parseInt(graphInfoItemList.get(i).getCount());
        percent= (itemcount*100/total);
        decimalFormat.format(percent);
        entries.add(new BarEntry(position,(float)percent));
    }

    public void addEntries2 (int i,float firstletter){
        itemcount = Integer.parseInt(graphInfoItemList.get(i).getCount());
        percent= (itemcount*100/total);
        decimalFormat.format(percent);
        entries2.add(new BarEntry(firstletter,(float)percent));
    }




}
