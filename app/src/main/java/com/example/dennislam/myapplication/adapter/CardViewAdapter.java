package com.example.dennislam.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dennislam.myapplication.R;

import java.util.List;

/**
 * Created by shaunlau on 24/1/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private Context context;
    private List<String> mListItems;
    private List<String> mListItems2;
    private List<String> mListItems3;

    public CardViewAdapter(Context context, List<String> listItems, List<String> listItems2, List<String> listItems3) {
        this.context=context;
        this.mListItems = listItems;
        this.mListItems2 = listItems2;
        this.mListItems3 = listItems3;
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
        textViewName.setText(mListItems.get(position));

        TextView textViewName2 = holder.textViewName2;
        textViewName2.setText(mListItems2.get(position));

        TextView textViewName3 = holder.textViewName3;
        textViewName3.setText(mListItems3.get(position));

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }
}
