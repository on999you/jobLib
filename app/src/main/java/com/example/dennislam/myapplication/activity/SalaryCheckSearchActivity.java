package com.example.dennislam.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.dennislam.myapplication.R;
import com.example.dennislam.myapplication.dao.criteria.IndustryDao;
import com.example.dennislam.myapplication.dao.criteria.JobCatDao;
import com.example.dennislam.myapplication.dao.criteria.SalarySourceDao;
import com.example.dennislam.myapplication.dao.criteria.WorkExpDao;
import com.example.dennislam.myapplication.xml.IndustryXML;
import com.example.dennislam.myapplication.xml.JobCatXML;
import com.example.dennislam.myapplication.xml.SalarySourceXML;
import com.example.dennislam.myapplication.xml.WorkExpXML;

import java.util.ArrayList;
import java.util.List;

import static com.example.dennislam.myapplication.R.id.jobDescription;
import static com.example.dennislam.myapplication.R.id.unspecifiedRadio;

public class SalaryCheckSearchActivity extends BaseActivity {

    private Toast toast;
    public static String sdtvName;

    WorkExpDao workExpItemDao = new WorkExpDao();
    SalarySourceDao salarySourceItemDao = new SalarySourceDao();
    JobCatDao jobCatItemDao = new JobCatDao();
    IndustryDao industryItemDao = new IndustryDao();

    List<WorkExpXML.WorkExpItem> workExpItemList = new ArrayList<WorkExpXML.WorkExpItem>();
    ArrayList<String> workExpNameArray = new ArrayList<String>();
    ArrayList<String> workExpIdArray = new ArrayList<String>();

    List<JobCatXML.JobCatItem> jobCatItemList = new ArrayList<JobCatXML.JobCatItem>();
    ArrayList<String> jobCatArray = new ArrayList<String>();
    ArrayList<String> jobCatIdArray = new ArrayList<String>();

    List<IndustryXML.IndustryItem> industryItemList = new ArrayList<IndustryXML.IndustryItem>();
    ArrayList<String> industryArray = new ArrayList<String>();
    ArrayList<String> industryIdArray = new ArrayList<String>();


    List<SalarySourceXML.SalarySourceItem> salarySourceItemList = new ArrayList<SalarySourceXML.SalarySourceItem>();
    ArrayList<String> salarySourceArray = new ArrayList<String>();

    TextView jobFunctionButton , jobIndustryButton;
    RadioButton radioButton1, radioButton2, radioButton3;

    //Values that pass to database
    String jobCat, jobIndustry;
    String workExpFrom;
    String workExpTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_salary_check_search, null, false);
        mDrawer.addView(contentView, 0);

        final EditText jobTitleField = (EditText)findViewById(R.id.jobTitleField);
        Button searchButton = (Button)findViewById(R.id.searchButton);
        final CheckBox similarCheckBox = (CheckBox)findViewById(R.id.similarCheckBox);

        jobFunctionButton = (TextView)findViewById(R.id.jobFunctionButton);
        jobIndustryButton = (TextView)findViewById(R.id.jobIndustryButton);

        radioButton1 = (RadioButton)findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);

        RadioGroup soruceRadioGroup = (RadioGroup)findViewById(R.id.soruceRadioGroup);
        RadioButton unspecifiedRadioBtn = (RadioButton) findViewById(R.id.unspecifiedRadio);
        RadioButton employerRadioBtn = (RadioButton) findViewById(R.id.employerRadio);
        RadioButton jobSeekerRadioBtn = (RadioButton) findViewById(R.id.jobSeekerRadio);

        soruceRadioGroup.check(unspecifiedRadio);
        unspecifiedRadioBtn.setBackgroundColor(Color.parseColor("#85A4A0"));
        unspecifiedRadioBtn.setTextColor(Color.parseColor("#F7F7F7"));
        employerRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
        employerRadioBtn.setTextColor(Color.parseColor("#3B616B"));
        jobSeekerRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
        jobSeekerRadioBtn.setTextColor(Color.parseColor("#3B616B"));

        soruceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == unspecifiedRadio) {
                    unspecifiedRadioBtn.setBackgroundColor(Color.parseColor("#85A4A0"));
                    unspecifiedRadioBtn.setTextColor(Color.parseColor("#F7F7F7"));
                    employerRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    employerRadioBtn.setTextColor(Color.parseColor("#3B616B"));
                    jobSeekerRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jobSeekerRadioBtn.setTextColor(Color.parseColor("#3B616B"));

                } else if(checkedId == R.id.employerRadio) {
                    employerRadioBtn.setBackgroundColor(Color.parseColor("#85A4A0"));
                    employerRadioBtn.setTextColor(Color.parseColor("#F7F7F7"));
                    unspecifiedRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    unspecifiedRadioBtn.setTextColor(Color.parseColor("#3B616B"));
                    jobSeekerRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jobSeekerRadioBtn.setTextColor(Color.parseColor("#3B616B"));

                } else if(checkedId == R.id.jobSeekerRadio) {
                    jobSeekerRadioBtn.setBackgroundColor(Color.parseColor("#85A4A0"));
                    jobSeekerRadioBtn.setTextColor(Color.parseColor("#F7F7F7"));
                    employerRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    employerRadioBtn.setTextColor(Color.parseColor("#3B616B"));
                    unspecifiedRadioBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    unspecifiedRadioBtn.setTextColor(Color.parseColor("#3B616B"));

                }
            }
        });



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(jobTitleField.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Please enter the job title first", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getBaseContext(), SalaryCheckResultActivity.class);
                    intent.putExtra("jobTitle", jobTitleField.getText().toString().trim());
                    intent.putExtra("withSimilarWord", similarCheckBox.isChecked());
                    intent.putExtra("workExpFrom", workExpFrom);
                    intent.putExtra("workExpTo", workExpTo);
                    sdtvName = jobTitleField.getText().toString().trim();
                    startActivity(intent);
                }
            }
        });

        //Run the code if there are network connected
        if(globalVariable.getNetwork() == true){
            new GetCriteriasAsyncTaskRunner().execute();
        }


    }

    class GetCriteriasAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            jobCatItemList = jobCatItemDao.getJobCatItemDao();
            workExpItemList = workExpItemDao.getWorkExpItemDao();
            salarySourceItemList = salarySourceItemDao.getSalarySourceItemDao();
            industryItemList = industryItemDao.getIndustryItemDao();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //Get Job Cat
            for(int i = 0; i < jobCatItemList.size(); i++){
                jobCatArray.add(i, jobCatItemList.get(i).getJobCatName());
                jobCatIdArray.add(i, jobCatItemList.get(i).getJobCatID());
            }
            if (jobCatItemList.size() > 0) {
                Log.v("Testing steps", "Salary Check : Got Job Cat");
            }

            //Get Job Industry
            for(int i = 0; i < industryItemList.size(); i++){
                industryArray.add(i, industryItemList.get(i).getIndustryName());
                industryIdArray.add(i, industryItemList.get(i).getIndustryID());
            }
            if (industryItemList.size() > 0) {
                Log.v("Testing steps", "Salary Check : Got Job industry");
            }

            //Get Work Exp
            for(int i = 0; i < workExpItemList.size(); i++){
                workExpNameArray.add(i, workExpItemList.get(i).getWorkExpname());
                workExpIdArray.add(i, workExpItemList.get(i).getWorkExpid());
            }
            if (workExpItemList.size() > 0) {
                Log.v("Testing steps", "Salary Check : Got Work Exp");
            }

            //Get Salary Source
            for(int i = 0; i < salarySourceItemList.size(); i++){
                salarySourceArray.add(i, salarySourceItemList.get(i).getDescription());
            }
            if (salarySourceItemList.size() > 0) {
                Log.v("Testing steps", "Salary Check : Got Salary Source");
            }
            radioButton1.setText(salarySourceArray.get(0));
            radioButton2.setText(salarySourceArray.get(1));
            radioButton3.setText(salarySourceArray.get(2));

        }
    }

    public void WorkExpFromDialog(View v) {
        new MaterialDialog.Builder(this)
                .title("Work Experience")
                .widgetColor(Color.parseColor("#6F9394"))
                .items(workExpNameArray.toArray(new CharSequence[workExpNameArray.size()]))
                .itemsCallbackSingleChoice(0, (dialog, view, which, text) -> {
                    TextView WorkExpFrom = (TextView)findViewById(R.id.WorkExpFrom);
                    WorkExpFrom.setText(text);
                    workExpFrom = workExpIdArray.get(which);
                    System.out.println(workExpFrom);
                    return true; // allow selection
                })
                .positiveColor(Color.parseColor("#486E76"))
                .positiveText("Done")
                .show();
    }

    public void WorkExpToDialog(View v) {
        new MaterialDialog.Builder(this)
                .title("Work Experience")
                .widgetColor(Color.parseColor("#6F9394"))
                .items(workExpNameArray.toArray(new CharSequence[workExpNameArray.size()]))
                .itemsCallbackSingleChoice(0, (dialog, view, which, text) -> {
                    TextView WorkExpTo = (TextView)findViewById(R.id.WorkExpTo);
                    WorkExpTo.setText(text);
                    workExpTo = workExpIdArray.get(which);
                    System.out.println(workExpTo);
                    return true; // allow selection
                })
                .positiveColor(Color.parseColor("#486E76"))
                .positiveText("Done")
                .show();
    }



    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showJobFunction(View v){
        new MaterialDialog.Builder(this)
                .title("Job Function")
                .widgetColor(Color.parseColor("#6F9394"))
                .items(jobCatArray.toArray(new CharSequence[jobCatArray.size()]))
                .itemsCallbackMultiChoice(new Integer[]{}, (dialog, which, text) -> {


                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < which.length; i++) {
                        if (i > 0) str.append('\n');
                        str.append(which[i]);
                        str.append(": ");
                        str.append(text[i]);
                    }
                    //showToast(str.toString());

                    boolean allowSelectionChange = which.length <= 5;
                    if (!allowSelectionChange) {
                        showToast("Select up to 5 choices only.");
                    }
                    if(which.length == 6){
                        jobFunctionButton.setText(5 + " items selected");
                    } else {
                        jobFunctionButton.setText(which.length + " items selected");
                    }
                    return allowSelectionChange;

                })
                .positiveColor(Color.parseColor("#486E76"))
                .positiveText("Done")
                //.alwaysCallMultiChoiceCallback()
                .show();
    }

    public void showJobIndustry (View v){
        new MaterialDialog.Builder(this)
                .title("Job Function")
                .widgetColor(Color.parseColor("#6F9394"))
                .items(industryArray.toArray(new CharSequence[industryArray.size()]))
                .itemsCallbackMultiChoice(new Integer[]{}, (dialog, which, text) -> {
                    boolean allowSelectionChange = which.length <= 5;
                    if (!allowSelectionChange) {
                        showToast("Select up to 5 choices only.");
                    }
                    if(which.length == 6){
                        jobIndustryButton.setText(5 + " items selected");
                    } else {
                        jobIndustryButton.setText(which.length + " items selected");
                    }
                    return allowSelectionChange;
                })
                .positiveColor(Color.parseColor("#486E76"))
                .positiveText("Done")
                .alwaysCallMultiChoiceCallback()
                .show();
    }
}




/*
        CharSequence[] cs = jobCatArray.toArray(new CharSequence[jobCatArray.size()]);

        android.support.v7.app.AlertDialog dialog;
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SalaryCheckSearchActivity.this);

        builder.setTitle("Job Function");
        builder.setMultiChoiceItems(cs, job_function_checkbox_status,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            job_function_seletedItems.add(indexSelected);
                            job_function_checkbox_status[indexSelected] = isChecked;


                            if(job_function_seletedItems.size() > 4) {
                                Toast.makeText(SalaryCheckSearchActivity.this, "More than 5 options" , Toast.LENGTH_SHORT).show();

                                //((android.support.v7.app.AlertDialog)dialog).getListView().getChildAt(0).setFocusable(false);
                                //((android.support.v7.app.AlertDialog)dialog).getListView().getChildAt(0).setEnabled(false);
                                //((android.support.v7.app.AlertDialog)dialog).getListView().getChildAt(0).setClickable(false);


                                for(int i = 0; i<job_function_seletedItems.size(); i++) {
                                    ((android.support.v7.app.AlertDialog)dialog).getListView().getChildAt(Integer.parseInt(job_function_seletedItems.get(i).toString())).setEnabled(false);
                                }
                            }

                        } else if (job_function_seletedItems.contains(indexSelected)) {
                            job_function_seletedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(job_function_seletedItems.size() > 4) {
                            Toast.makeText(SalaryCheckSearchActivity.this, "Cannot select more than five", Toast.LENGTH_SHORT).show();
                        }

                        jobFunctionButton.setTextColor(Color.parseColor("#000000"));
                        jobFunctionButton.setText(job_function_seletedItems.size() + " Items Selected");

                    }
                });
        dialog = builder.create();
        dialog.show();
        */



