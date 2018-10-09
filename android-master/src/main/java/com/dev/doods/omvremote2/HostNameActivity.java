package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dev.doods.omvremote2.Storage.FileSystems.DisckController;
import com.dev.doods.omvremote2.Storage.FileSystems.FileSystem;
import com.dev.doods.omvremote2.Storage.Smart.SmartController;
import com.dev.doods.omvremote2.Storage.Smart.SmartDevices;
import com.google.gson.reflect.TypeToken;
import com.owncloud.android.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.SystemController;
import Models.Result;
import Models.SettingsNetwork;
import OMV.Base.NavigationBaseActivity;
import utils.CheckDirty;
import utils.Util;

public class HostNameActivity extends NavigationBaseActivity {
    private SettingsNetwork mSettingsNetwork;
    private SystemController controller;
    Handler handler;

    private EditText _mHostNameView;
    private EditText _mDomainNameView;
    private FloatingActionButton _mSaveView;
//Revo:stvelzhang start
    TextView mDevice_name,mDevice_totalsize,mDevice_freesize;
    private SettingsNetwork _mSettingsNetwork;
    private SystemController mSystemController = new SystemController(this);
    private SmartController mSmartController = new SmartController(this);
    private DisckController mDisckController = new DisckController(this);
    private List<SmartDevices> mSmartDevices= new ArrayList<SmartDevices>();
    private List<FileSystem> _lst = new ArrayList<FileSystem>();
//Revo:stvelzhang end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_name;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_name);
        _mHostNameView = (EditText) findViewById(R.id.etHostName);
        //_mDomainNameView =(EditText) findViewById(R.id.etDomainName);
        _mSaveView= (FloatingActionButton) findViewById(R.id.fab_Save);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//Revo:stvelzhang start
        final ViewGroup navigationHeader = (ViewGroup) findNavigationViewChildById(R.id.nav_header_home_view);
        setNavigationHeaderView(navigationHeader);
//Revo:stvelzhang end
        _mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!IsFinalized(true))return;

                mSettingsNetwork.setHostname(_mHostNameView.getText().toString());
                //mSettingsNetwork.setDomainname(_mDomainNameView.getText().toString());

                controller.setGeneralSettings(mSettingsNetwork,new CallbackImpl(HostNameActivity.this));
                new CheckDirty(HostNameActivity.this).Check();
            }
        });


        handler= new Handler();
        controller = new SystemController(this);

        controller.getGeneralSettingsNetwork(new CallbackImpl(HostNameActivity.this) {


            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                mSettingsNetwork = response.GetResultObject( new TypeToken<SettingsNetwork>(){});
                handler.post(new Runnable(){
                    public void run() {
                        _mHostNameView.setText(mSettingsNetwork.getHostname());
                        //_mDomainNameView.setText(mSettingsNetwork.getDomainname());

                    }
                });
            }
        });

    }
    private String LastErrors;
    private Boolean CanSave()
    {
        return (mSettingsNetwork != null);
    }
    
//Revo:stvelzhang start
    /**
     * Finds a view that was identified by the id attribute from the drawer header.
     *
     * @param id the view's id
     * @return The view if found or <code>null</code> otherwise.
     */
    private View findNavigationViewChildById(int id) {
        NavigationView view = findViewById(R.id.nav_view);

        if (view != null) {
            return view.getHeaderView(0).findViewById(id);
        } else {
            return null;
        }
    }
    private void setNavigationHeaderView(ViewGroup navigationHeader) {
        if (navigationHeader != null) {
            mDevice_name = navigationHeader.findViewById(R.id.device_name);
            mDevice_totalsize = navigationHeader.findViewById(R.id.device_totalsize);
            mDevice_freesize = navigationHeader.findViewById(R.id.device_freesize);
            // mDevice_name.setText("revoNAa");
            mDevice_totalsize.setText("250GIB");
            // mDevice_freesize.setText("");


            mSystemController.getGeneralSettingsNetwork(new CallbackImpl(this) {

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    super.onResponse(call,response);
                    _mSettingsNetwork =response.GetResultObject( new TypeToken<SettingsNetwork>(){});

                    Log.d("stvelzhang","getPowermngSettings---ishere---_mPowermngSettings:"+ _mSettingsNetwork);
                    mHandler.post(new Runnable(){
                        public void run() {

                            Log.d("stvelzhang","getPowermngSettings---ishere---getHostname:"+ _mSettingsNetwork.getHostname());
                            mDevice_name.setText(_mSettingsNetwork.getHostname());
                        }
                    });
                }
            });

            mSmartController.getListDevices(new CallbackImpl(this){

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    super.onResponse(call,response);

                    final Result<SmartDevices> res = response.GetResultObject(new TypeToken<Result<SmartDevices>>(){});

                    mHandler.post(new Runnable(){
                        public void run() {
                            populateViews(res.getData());
                        }
                    });

                }
            });



            mDisckController.getListFilesSystems(new CallbackImpl(this) {
                @Override
                public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                    super.onResponse(call,response);
                    final Result<FileSystem> res = response.GetResultObject(new TypeToken<Result<FileSystem>>(){});
                    mHandler.post(new Runnable(){
                        public void run() {

                            Showfreesize(res.getData());
                        }
                    });
                }
            });



        }
    }

    public List<FileSystem> remove_mmcblk(List<FileSystem> list, String target){
        Iterator<FileSystem> iter = list.iterator();
        while (iter.hasNext()) {
            FileSystem item = iter.next();
            if (item.getDevicefile().contains(target)) {
                iter.remove();
            }
        }
        return list;
    }

    private void Showfreesize(List<FileSystem> lst)
    {
        _lst.clear();

        _lst.addAll(remove_mmcblk(lst,"mmcblk"));

        Iterator<FileSystem> iter = _lst.iterator();
        while (iter.hasNext()) {
            FileSystem item = iter.next();
            Double availableLong = Double.parseDouble(item.getAvailable());
            String availableSize = Util.humanReadableByteCount(availableLong,false);

            mDevice_freesize.setText("Available: " + availableSize);

        }

    }

    private void populateViews(List<SmartDevices> smartDevices)
    {
        mSmartDevices.clear();
        mSmartDevices.addAll(smartDevices);
        mSmartDevices.size();
        Log.d("stvelzhang","HomeActivity---populateViews--smartDevices_size::" + mSmartDevices.size());
        for(int i =0; i<mSmartDevices.size(); i++){
            SmartDevices data = mSmartDevices.get(i);
            mDevice_totalsize.setText("Total: " + Util.humanReadableByteCount(Double.parseDouble(data.getSize()),false) +
                    "   Temperature: "  + data.getTemperature());

        }

    }
//Revo:stvelzhang end
}
