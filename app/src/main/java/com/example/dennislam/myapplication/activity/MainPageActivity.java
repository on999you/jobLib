package com.example.dennislam.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dennislam.myapplication.R;

public class MainPageActivity extends BaseActivity {
//NONONONON
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main_page, null, false);
        mDrawer.addView(contentView, 0);

        Log.v("Testing steps", "Main Page : Get Udid from global class = " + globalVariable.getUdid());
    }

    public void PassToJobList(View v){
        Intent intent = new Intent(getBaseContext(), JobListActivity.class);
        startActivity(intent);
    }

    public void PassToSalary(View v){
        Intent intent = new Intent(getBaseContext(), SalaryCheckSearchActivity.class);
        startActivity(intent);
    }

    public void PassToAppliedJob(View v){
        Intent intent = new Intent(getBaseContext(), AppliedJobActivity.class);
        startActivity(intent);
    }

    public void PassToCV(View v){
        Intent intent = new Intent(getBaseContext(), CvActivity.class);
        startActivity(intent);
    }

    public void PassToFAQ(View v){
        Intent intent = new Intent(getBaseContext(), FeedbackActivity.class);
        startActivity(intent);
    }


    public void exitapp(View v){
        createDialog();
    }

    public void onBackPressed() {
        createDialog();
    }

    private void createDialog() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Are you sure you want to exit?");
        alertDlg.setCancelable(false); // We avoid that the dialog can be cancelled, forcing the user to choose one of the options
        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDlg.create().show();
    }




}
