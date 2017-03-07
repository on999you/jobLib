package com.example.dennislam.myapplication.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.dennislam.myapplication.R;
import com.example.dennislam.myapplication.dao.RelevantDataDao;
import com.example.dennislam.myapplication.xml.RelevantDataXML;

import java.util.ArrayList;
import java.util.List;

public class RelevantDataActivity extends BaseActivity {

    String jobTitle, workExpFrom, workExpTo;
    Boolean withSimilarWord;

    ArrayList<String> relevantJobTitleArray = new ArrayList<String>();
    ArrayList<String> relevantJobCatArray = new ArrayList<String>();

    RelevantDataDao relevantDataItemDao = new RelevantDataDao();

    List<RelevantDataXML.RelevantDataItem> relevantDataItemList = new ArrayList<RelevantDataXML.RelevantDataItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_relevant_data, null, false);
        mDrawer.addView(contentView, 0);

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
            new GetRelevantDataAsyncTaskRunner().execute();
        }

    }

    class GetRelevantDataAsyncTaskRunner extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            relevantDataItemList = relevantDataItemDao.getRelevantDataItemDao(jobTitle, withSimilarWord, workExpFrom, workExpTo);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            System.out.println(relevantDataItemDao.getItemsTotal());

            for(int i = 0; i < relevantDataItemList.size(); i++){
                relevantJobTitleArray.add(i, relevantDataItemList.get(i).getJobTitle());
                relevantJobCatArray.add(i, relevantDataItemList.get(i).getJobCat());
            }
            if (relevantDataItemList.size() > 0) {
                System.out.println("successful");
            }
            else {
                System.out.println("failed");
            }


        }
    }
}
