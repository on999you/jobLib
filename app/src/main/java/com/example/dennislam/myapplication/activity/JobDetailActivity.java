package com.example.dennislam.myapplication.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dennislam.myapplication.R;
import com.example.dennislam.myapplication.dao.ApplyJobDao;
import com.example.dennislam.myapplication.dao.JobDetailDao;
import com.example.dennislam.myapplication.dao.JobListDao;
import com.example.dennislam.myapplication.xml.ApplyJobXML;
import com.example.dennislam.myapplication.xml.ItemsInfoBaseXML;
import com.example.dennislam.myapplication.xml.JobDetailXML;
import com.example.dennislam.myapplication.xml.JobListXML;

import java.util.ArrayList;
import java.util.List;

import static com.example.dennislam.myapplication.R.id.jobDescription;

public class JobDetailActivity extends BaseActivity {

    private SharedPreferences settings;
    private static final String data = "DATA";
    private static final String existingUdid = "";
    String udid;

    String jobId;

    List<JobDetailXML.JobDetailItem> jobDetailItemList = new ArrayList<JobDetailXML.JobDetailItem>();
    ArrayList<String> jobTitleArray = new ArrayList<String>();
    ArrayList<String> industryArray = new ArrayList<String>();
    ArrayList<String> jobAreaArray = new ArrayList<String>();
    ArrayList<String> companyArray = new ArrayList<String>();
    ArrayList<String> emailArray = new ArrayList<String>();
    ArrayList<String> simpleBodyArray = new ArrayList<String>();
    ArrayList<String> jobDescArray = new ArrayList<String>();
    ArrayList<String> contactArray = new ArrayList<String>();
    ArrayList<String> companyDescArray = new ArrayList<String>();
    ArrayList<String> createDateArray = new ArrayList<String>();

    List<ItemsInfoBaseXML> applyJobItemList = new ArrayList<ItemsInfoBaseXML>();
    JobDetailDao jobDetailItemDao = new JobDetailDao();
    ApplyJobDao applyJobItemDao = new ApplyJobDao();

    TextView jobTitleView, industryView, jobAreaView, companyView, emailView, simpleBodyView, jobDescView, contactView, createDateView;
    TextView textView1, textView2, textView3, textView4, textView5, textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_job_detail, null, false);
        mDrawer.addView(contentView, 0);

        jobId = getIntent().getStringExtra("jobId");

        settings = getSharedPreferences(data,0);
        udid = settings.getString(existingUdid, "");

        //Run the code if there are network connected
        if(globalVariable.getNetwork() == true){
            new getJobDetailAsyncTaskRunner().execute();
        }


        jobTitleView = (TextView)findViewById(R.id.jobTitleView);
        companyView = (TextView)findViewById(R.id.companyView);
        createDateView = (TextView)findViewById(R.id.createDateView);

        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView5 = (TextView)findViewById(R.id.textView5);
        textView6 = (TextView)findViewById(R.id.textView6);

        RadioGroup jobDetailRadioGroup = (RadioGroup)findViewById(R.id.jobDetailRadioGroup);
        RadioButton jobDescRadio = (RadioButton) findViewById(R.id.jobDescription);
        RadioButton companyInfoRadio = (RadioButton) findViewById(R.id.companyInfo);
        jobDetailRadioGroup.check(jobDescription);
        jobDescRadio.setBackgroundColor(Color.parseColor("#85A4A0"));
        companyInfoRadio.setBackgroundColor(Color.parseColor("#FFFFFF"));
        jobDescRadio.setTextColor(Color.parseColor("#F7F7F7"));
        companyInfoRadio.setTextColor(Color.parseColor("#3B616B"));

        jobDetailRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == jobDescription) {
                    jobDescRadio.setBackgroundColor(Color.parseColor("#85A4A0"));
                    companyInfoRadio.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jobDescRadio.setTextColor(Color.parseColor("#F7F7F7"));
                    companyInfoRadio.setTextColor(Color.parseColor("#3B616B"));
                    //Toast.makeText(getApplicationContext(), "choice: job", Toast.LENGTH_SHORT).show();

                    textView1.setText("Job Function");
                    textView3.setText("Job Industry");
                    textView5.setText("Job Description");

                    textView2.setText(jobAreaArray.get(0));
                    textView4.setText(industryArray.get(0));
                    textView6.setText(jobDescArray.get(0));

                } else if(checkedId == R.id.companyInfo) {
                    companyInfoRadio.setBackgroundColor(Color.parseColor("#85A4A0"));
                    jobDescRadio.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    companyInfoRadio.setTextColor(Color.parseColor("#F7F7F7"));
                    jobDescRadio.setTextColor(Color.parseColor("#3B616B"));
                    //Toast.makeText(getApplicationContext(), "choice: company", Toast.LENGTH_SHORT).show();

                    textView1.setText("Email Address");
                    textView3.setText("Contact");
                    textView5.setText("Company Description");

                    textView2.setText(emailArray.get(0));
                    textView4.setText(contactArray.get(0));
                    textView6.setText(companyDescArray.get(0));


                }
            }
        });


        Button applyJobButton = (Button)findViewById(R.id.applyJobButton);

        applyJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new applyJobAsyncTaskRunner().execute();
            }
        });


    }


    class getJobDetailAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            jobDetailItemList = jobDetailItemDao.jobDetailItemDao(jobId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            for(int i = 0; i < jobDetailItemList.size(); i++){

                jobTitleArray.add(i, jobDetailItemList.get(i).getJobTitle());
                industryArray.add(i, jobDetailItemList.get(i).getIndustry());
                jobAreaArray.add(i, jobDetailItemList.get(i).getJobArea());
                companyArray.add(i, jobDetailItemList.get(i).getCompany());
                emailArray.add(i, jobDetailItemList.get(i).getEmail());
                simpleBodyArray.add(i, jobDetailItemList.get(i).getSimpleBody());
                jobDescArray.add(i, jobDetailItemList.get(i).getJobDesc());
                contactArray.add(i, jobDetailItemList.get(i).getContact());
                companyDescArray.add(i, jobDetailItemList.get(i).getCompanyDesc());
                createDateArray.add(i, jobDetailItemList.get(i).getCreateDate());


                System.out.println(jobTitleArray.get(i));
                System.out.println(industryArray.get(i));
                System.out.println(jobAreaArray.get(i));
                System.out.println(companyArray.get(i));
                System.out.println(emailArray.get(i));
                System.out.println(simpleBodyArray.get(i));
                System.out.println(jobDescArray.get(i));
                System.out.println(contactArray.get(i));
                System.out.println(createDateArray.get(i));



                jobTitleView.setText(jobTitleArray.get(0));
                //industryView.setText(industryArray.get(0));
                //jobAreaView.setText(jobAreaArray.get(0));
                companyView.setText("( " + companyArray.get(0) + " )");
                //emailView.setText(emailArray.get(0));
                //simpleBodyView.setText(simpleBodyArray.get(0));
                //jobDescView.setText(jobDescArray.get(0));
                //contactView.setText(contactArray.get(0));
                createDateView.setText(createDateArray.get(0));

                textView1.setText("Job Function");
                textView3.setText("Industry");
                textView5.setText("Job Description");

                textView2.setText(jobAreaArray.get(0));
                textView4.setText(industryArray.get(0));
                textView6.setText(jobDescArray.get(0));

            }
            if (jobDetailItemList.size() > 0) {
                Log.v("Testing steps", "Search Job : Got Job Detail");
                System.out.println("successful");
            }
            else {
                System.out.println("failed");
            }

        }
    }




    class applyJobAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            applyJobItemList = applyJobItemDao.applyJobItemDao(udid, "001");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            Log.v("Testing steps", "Apply Job : Successful");

            System.out.println(applyJobItemList.get(0).getMsg());

        }
    }
}
