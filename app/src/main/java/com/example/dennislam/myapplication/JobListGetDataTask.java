package com.example.dennislam.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dennislam.myapplication.dao.JobListDao;
import com.example.dennislam.myapplication.xml.JobListXML;
import com.sch.rfview.AnimRFRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaunlau on 16/1/2017.
 */

public class JobListGetDataTask extends AsyncTask<Void,Void,Void> {

    private AnimRFRecyclerView mRecyclerView;
    private List<String> jobTitleList;
    private List<String> companyNameList;
    private List<String> createDateList;
    private List<String> jobIdList;

    int rownumStart, rownumEnd;

    JobListDao jobListItemDao = new JobListDao();
    List<JobListXML.JobListItem> jobListItemList = new ArrayList<JobListXML.JobListItem>();

    public JobListGetDataTask(AnimRFRecyclerView recyclerView, List<String> jobIdList2, List<String> jobTitleList2, List<String> companyNameList2, List<String> createDateList2, Integer rownumStart, Integer rownumEnd) {
        mRecyclerView = recyclerView;
        jobIdList = jobIdList2;
        jobTitleList = jobTitleList2;
        companyNameList = companyNameList2;
        createDateList = createDateList2;

        this.rownumStart=rownumStart;
        this.rownumEnd=rownumEnd;
    }



    @Override
    protected Void doInBackground(Void... params) {
        jobListItemList = jobListItemDao.jobListItemDao(rownumStart, rownumEnd);
        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.setRefresh(false);

        for(int i = 0; i < jobListItemList.size(); i++){
            //Log.v("tempTest","" + jobListItemList.get(i).getJobID());
            jobIdList.add(jobListItemList.get(i).getJobID());
            jobTitleList.add(jobListItemList.get(i).getJobTitle());
            companyNameList.add(jobListItemList.get(i).getCompany());
            createDateList.add(jobListItemList.get(i).getCreateDate());
        }





    }




}