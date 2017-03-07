package com.example.dennislam.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dennislam.myapplication.R;

import java.util.List;

/**
 * Created by shaunlau on 24/1/2017.
 */

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    private Context context;
    private List<String> jobTitleList;
    private List<String> companyNameList;
    private List<String> createDateList;
    private List<String> jobIdList;

    public JobListAdapter(Context context, List<String> listItemss, List<String> listItems, List<String> listItems2, List<String> listItems3) {
        this.context=context;
        this.jobIdList = listItemss;
        this.jobTitleList = listItems;
        this.companyNameList = listItems2;
        this.createDateList = listItems3;
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewName2;
        public TextView textViewName3;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView)itemView.findViewById(R.id.textView1);
            textViewName2 = (TextView)itemView.findViewById(R.id.textView2);
            textViewName3 = (TextView)itemView.findViewById(R.id.textView3);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout,parent,false);
        ViewHolder myViewHolder = new ViewHolder(itemView);
        return  myViewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        textViewName.setText(jobTitleList.get(position));

        TextView textViewName2 = holder.textViewName2;
        textViewName2.setText(companyNameList.get(position));

        TextView textViewName3 = holder.textViewName3;
        textViewName3.setText(createDateList.get(position));
    }

    @Override
    public int getItemCount() {
        return jobTitleList.size();
    }
}
