package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.owncloud.android.R;

import java.io.IOException;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.SystemController;
import Models.SettingsNetwork;
import OMV.Base.NavigationBaseActivity;
import utils.CheckDirty;

public class HostNameActivity extends NavigationBaseActivity {
    private SettingsNetwork mSettingsNetwork;
    private SystemController controller;
    Handler handler;

    private EditText _mHostNameView;
    private EditText _mDomainNameView;
    private FloatingActionButton _mSaveView;
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
}
