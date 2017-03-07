package com.example.dennislam.myapplication.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dennislam.myapplication.R;
import com.example.dennislam.myapplication.adapter.JobListAdapter;
import com.example.dennislam.myapplication.dao.AppliedJobDao;
import com.example.dennislam.myapplication.dao.JobListDao;
import com.example.dennislam.myapplication.xml.AppliedJobXML;
import com.example.dennislam.myapplication.xml.JobListXML;
import com.sch.rfview.AnimRFRecyclerView;
import com.sch.rfview.manager.AnimRFLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class AppliedJobActivity extends BaseActivity{

    List<AppliedJobXML.AppliedJobItem> appliedJobItemList = new ArrayList<AppliedJobXML.AppliedJobItem>();
    ArrayList<String> jobTitleArray = new ArrayList<String>();
    ArrayList<String> companyArray = new ArrayList<String>();
    ArrayList<String> applyDateArray = new ArrayList<String>();

    AppliedJobDao appliedJobItemDao = new AppliedJobDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_applied_job, null, false);
        mDrawer.addView(contentView, 0);

        //Run the code if there are network connected
        if(globalVariable.getNetwork() == true){
            new getAppliedJobAsyncTaskRunner().execute();
        }

    }

    class getAppliedJobAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            appliedJobItemList = appliedJobItemDao.getAppliedJobItemDao();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            for(int i = 0; i < appliedJobItemList.size(); i++){
                jobTitleArray.add(i, appliedJobItemList.get(i).getJobTitle());
                companyArray.add(i, appliedJobItemList.get(i).getCompany());
                applyDateArray.add(i, appliedJobItemList.get(i).getApplyDate());
            }

        }
    }
}
