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

public class CardViewGetDataTask extends AsyncTask<Void,Void,Void> {

    private AnimRFRecyclerView mRecyclerView;
    private List<String> mListItems;
    private List<String> mListItems2;
    private List<String> mListItems3;

    int rownumStart, rownumEnd;

    JobListDao jobListItemDao = new JobListDao();
    List<JobListXML.JobListItem> jobListItemList = new ArrayList<JobListXML.JobListItem>();

    public CardViewGetDataTask(AnimRFRecyclerView recyclerView, List<String> listItems, List<String> listItems2, List<String> listItems3,  Integer rownumStart,  Integer rownumEnd) {
        mRecyclerView = recyclerView;
        mListItems = listItems;
        mListItems2 = listItems2;
        mListItems3 = listItems3;

        this.rownumStart=rownumStart;
        this.rownumEnd=rownumEnd;
    }



    @Override
    protected Void doInBackground(Void... params) {
        jobListItemList = jobListItemDao.jobListItemDao(rownumStart, rownumEnd);
        for(int i = 0; i < jobListItemList.size(); i++){
            //Log.v("tempTest","" + jobListItemList.get(i).getJobID());
            mListItems.add(jobListItemList.get(i).getJobTitle());
            mListItems2.add(jobListItemList.get(i).getCompany());
            mListItems3.add(jobListItemList.get(i).getCreateDate());

            Log.v(jobListItemList.get(i).getJobTitle(),"~");
        }
        return null;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.setRefresh(false);



    }




}