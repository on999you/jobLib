package com.example.dennislam.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dennislam.myapplication.CardViewGetDataTask;
import com.example.dennislam.myapplication.R;
import com.example.dennislam.myapplication.adapter.CardViewAdapter;
import com.sch.rfview.AnimRFRecyclerView;
import com.sch.rfview.manager.AnimRFLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SearchJobsActivity extends BaseActivity {

    //Card View
    private AnimRFRecyclerView recyclerView;
    private AnimRFLinearLayoutManager manager ;
    private View headerView;
    private View footerView;
    private CardViewAdapter customAdapter;
    private List<String> mListItems= new ArrayList<>();
    private List<String> mListItems2= new ArrayList<>();
    private List<String> mListItems3= new ArrayList<>();
    int rownumStart, rownumEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search_jobs, null, false);
        mDrawer.addView(contentView, 0);

        Button testingJobDetailButton = (Button)findViewById(R.id.testingJobDetailButton);

        testingJobDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), JobDetailActivity.class);
                startActivity(intent);
            }
        });

        //Card View
        recyclerView = (AnimRFRecyclerView)findViewById(R.id.refresh_layout);
        headerView = LayoutInflater.from(this).inflate(R.layout.header_view, null);
        footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, null);

        customAdapter = new CardViewAdapter(this,mListItems,mListItems2,mListItems3);

        recyclerView.addHeaderView(headerView);
        recyclerView.addFootView(footerView);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setHeaderImage((ImageView)headerView.findViewById(R.id.iv_hander));

        manager = new AnimRFLinearLayoutManager(this);
        //manager = new AnimRFLinearLayoutManager(this,manager.getOrientation(),false);

        recyclerView.setLayoutManager(manager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation(), true));



        recyclerView.setLoadDataListener(new AnimRFRecyclerView.LoadDataListener(){
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rownumStart = 1;
                        rownumEnd = 5;
                        newData();
                    }
                }).start();
            }
            @Override
            public void onLoadMore() {
                Runnable myRun = new Runnable() {
                    @Override
                    public void run() {
                        addData();
                    }
                };
                Thread loadMore = new Thread(myRun);
                loadMore.start();
                recyclerView.getAdapter().notifyDataSetChanged();
                //recyclerView.loadMoreComplate();
            }
        });
        recyclerView.setRefresh(true);
    }

    private void addData() {
        rownumStart += 5;
        rownumEnd += 5;
        new CardViewGetDataTask(recyclerView,mListItems,mListItems2,mListItems3,rownumStart,rownumEnd).execute();
//        for (int i = 0; i < 18; i++) {
//            mListItems.add("条目  " + (mListItems.size() + 1));
//        }
    }

    public void newData() {
        mListItems.clear();
        mListItems2.clear();
        mListItems3.clear();
        new CardViewGetDataTask(recyclerView,mListItems,mListItems2,mListItems3,rownumStart,rownumEnd).execute();
//                for (int i = 0; i < 13; i++) {
//            mListItems.add("num  " + (mListItems.size() + 1));
//        }
    }




}
