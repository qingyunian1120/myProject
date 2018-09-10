package OMV.Base;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.os.Handler;
import com.owncloud.android.R;

import net.hockeyapp.android.Tracking;


import Interfaces.IHandlerCallback;
import utils.SnackBarError;
import utils.SnackBarStartActivity;

/**
 * Created by Ividata7 on 29/01/2017.
 */

public class AppCompatBaseActivity  extends AppCompatActivity implements IHandlerCallback {


    protected String TAG ="AppCompatBaseActivity";

    private View mContent;
    private boolean IsFinalized;
    private boolean IsOnError;
    private ProgressBar mProgress;
    public String LastError = "";
    public int BusyCount = 0;
    protected  Handler mHandler = new Handler();

    public void AddBusy()
    {
        if(!Isbusy())
            SetProgresVisibility(View.VISIBLE);
        BusyCount++;

    }

    public void SuppBusy()
    {
        BusyCount--;
        if(BusyCount>0) BusyCount = 0;
        if(!Isbusy())
            SetProgresVisibility(View.GONE);
    }

    public Boolean Isbusy()
    {
        return BusyCount > 0;
    }


    private void SetProgresVisibility(final int i)
    {

        mHandler.post(new Runnable(){
            public void run() {

                if(mProgress != null)
                {
                    mProgress.setVisibility(i);
                }
            }
        });
    }

    @Override
    public void SetFinalized(boolean finalized)
    {
        this.IsFinalized = finalized;
        //SetProgresVisibility(View.GONE);

    }
    @Override
    public void SetOnError(boolean onError)
    {
        IsOnError = onError;
        //SetProgresVisibility(View.GONE);
    }
    @Override
    public void ShowSnackError(String msg,boolean cansendLogs)
    {
        LastError = msg;
        IsOnError = true;
        //SetProgresVisibility(View.GONE);
        new SnackBarError(mContent,msg,cansendLogs);
    }

    public void SnackBarStartActivity(String msg,Class<?> cls)
    {
        new SnackBarStartActivity(mContent,msg,cls);
    }

    public void showInfo(String info)
    {
        Snackbar.make(mContent, info, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public AppCompatBaseActivity()
    {

        Class<?> enclosingClass = getClass().getEnclosingClass();
        if (enclosingClass != null) {
           TAG = enclosingClass.getName();
        } else {
            TAG = getClass().getName();
        }

    }

    protected boolean IsFinalized(boolean repeatError )
    {

        if(IsOnError && repeatError)
        {
            ShowSnackError(LastError,false);
        }

        return !this.IsOnError && IsFinalized;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent=  findViewById(android.R.id.content);
    }


    @Override
    protected void onStart() {
         mProgress = (ProgressBar)findViewById(R.id.app_progress);
        if(!IsFinalized)
            SetProgresVisibility(View.VISIBLE);
        super.onStart();
        /*
        ProgressBar pb = new ProgressBar(this);
        pb.setEnabled(true);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);





        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        //RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)pb.getLayoutParams();
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_END);
        params2.setMargins(0,22,0,0);
        //android:layout_gravity="bottom|end"



        addContentView(pb, params2);
        //addContentView(pb);
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tracking.startUsage(this);
        // Further statements
        // ...
    }

    @Override
    protected void onPause() {
        // Further statements
        // ...
        super.onPause();
        Tracking.stopUsage(this);

    }



    protected void complain(String message) {
        Log.e(TAG, "**** OMVRemote Error: " + message);
        alert("Error: " + message);
    }

    protected void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }


//xzy add for back error
    @Override
    public void onBackPressed() {

        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        Log.d("xuzhenyue"," name = " + name);
        if(!name.equals("com.dev.doods.omvremote2.HomeActivity")){
            Intent movhomeIntent = new Intent(getApplicationContext(), com.dev.doods.omvremote2.HomeActivity.class);
            startActivity(movhomeIntent);
        }else{
            super.onBackPressed();
        }
    }
}
