package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.owncloud.android.R;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.CallbackImpl;
import Client.Response;
import Controllers.ConfigController;
import Controllers.SystemController;
import Interfaces.IYesNoListenerDialog;
import Models.Errors;
import Models.SettingsNetwork;
import OMV.Base.NavigationBaseActivity;
import OMVFragment.Dialogs.YesNoDialog;

public class HostNameActivity extends NavigationBaseActivity implements IYesNoListenerDialog {
    private SettingsNetwork mSettingsNetwork;
    private SystemController controller;
    Handler handler;

    private EditText _mHostNameView;
    private EditText _mDomainNameView;
    private FloatingActionButton _mSaveView;
    ConfigController mConfigController = new ConfigController(this);
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
                ApplyChange();

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
    private void ApplyChange(){
        mConfigController.isDirty(new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {

                boolean b = response.GetResultObject(new TypeToken<Boolean>(){});

                if(b)
                {
                    ShowPopUpDirty();
                }
            }
        });
    }
    private void ShowPopUpDirty()
    {

        DialogFragment dialog = new YesNoDialog();
        Bundle args = new Bundle();
        args.putString("title", getString(R.string.ApplyChanges));
        args.putString("message", getString(R.string.ApplyChangesMessage));
        dialog.setArguments(args);
        //dialog.setTargetFragment(OMVSystemActivity.this, YesNoDialog.YES_NO_CALL);
        dialog.show(getSupportFragmentManager(), "tag");
    }
    @Override
    public void onYesNoActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode ==  Activity.RESULT_OK)
        {
            mConfigController.applyChangesBg(null);
        }
        else
            finish();


    }
    private String LastErrors;
    private Boolean CanSave()
    {
        return (mSettingsNetwork != null);
    }
}
