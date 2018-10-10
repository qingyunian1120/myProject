package com.dev.doods.omvremote2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
import Client.Host;
import Client.JSONRPCClient;
import Client.Response;
import Controllers.SystemController;
import DAL.HostsDAO;
import Interfaces.OutputListener;
import Models.PowermngSettings;
import Models.Result;
import Models.SettingsNetwork;
import OMV.Base.NavigationBaseActivity;
import OMVFragment.Dialogs.YesNoDialog;
import Interfaces.IYesNoListenerDialog;
import utils.CheckDirty;
import utils.Util;

public class ShutDownDeviceActivity extends NavigationBaseActivity implements OutputListener, IYesNoListenerDialog {
    private SystemController controller;
    private SystemController mSystemController = new SystemController(this);
    private CheckDirty mCheckDirty;
    private Spinner _mrebootdeviceView;
    private Switch _mMonitoringView;
    private EditText _mHostNameView;
    private PowermngSettings _mPowermngSettings;
   // private String[] powbtnarrays = new String[] {"nothing","wakeup","reboot","standby","shutdown"};

    Handler handler;
//Revo:stvelzhang start
    TextView mDevice_name,mDevice_totalsize,mDevice_freesize;
    private SettingsNetwork _mSettingsNetwork;
    private SmartController mSmartController = new SmartController(this);
    private DisckController mDisckController = new DisckController(this);
    private List<SmartDevices> mSmartDevices= new ArrayList<SmartDevices>();
    private List<FileSystem> _lst = new ArrayList<FileSystem>();
//Revo:stvelzhang end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_shutdowndevice;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shutdowndevice);
        controller = new SystemController(this);
        handler= new Handler();
        mCheckDirty = new CheckDirty(ShutDownDeviceActivity.this);
//Revo:stvelzhang start
        final ViewGroup navigationHeader = (ViewGroup) findNavigationViewChildById(R.id.nav_header_home_view);
        // setNavigationHeaderView(navigationHeader);
        NavHeaderViewUtil mNavHeaderViewUtil = new NavHeaderViewUtil(navigationHeader,this);
        mNavHeaderViewUtil.setNavigationHeaderView(navigationHeader,this);
//Revo:stvelzhang end
        _mrebootdeviceView = (Spinner) findViewById(R.id.spinnerpowerbutton);


        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.reboot_device, R.layout.simple_custom_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _mrebootdeviceView.setAdapter(arrayAdapter);


        FloatingActionButton _mSaveView = (FloatingActionButton) findViewById(R.id.fab);
        _mSaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("stvelzhang","myclick here");
               // if(!IsFinalized(true))return;
                savedone();


            }
        });

/*
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.LstCron);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mCronAdapter = new CronAdapter(this,mCronList,mController);
        recyclerView.setAdapter(mCronAdapter);
*/

/*
        controller.getPowermngSettings(new CallbackImpl(this) {

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call,response);
                _mPowermngSettings =response.GetResultObject( new TypeToken<PowermngSettings>(){});
                Log.d("stvelzhang","getPowermngSettings---ishere---_mPowermngSettings:"+ _mPowermngSettings);
                handler.post(new Runnable(){
                    public void run() {
//
                        _mMonitoringView.setChecked(_mPowermngSettings.getCpufreq());
                        int i = 0;
                        String getpowbtn = _mPowermngSettings.getPowerbtn();
                        Log.d("stvelzhang","getpowbtn is ::" + getpowbtn);
                        if(getpowbtn.equals("Nothing")){
                            i = 0;
                        }else if(getpowbtn.equals("Shutdown")){
                            i=1;
                        }else if(getpowbtn.equals("Standby")){
                            i=2;
                        }else{

                            i=0;
                        }
                        _mPowerkeydownView.setSelection(i);

//
                        Log.d("stvelzhang","getPowermngSettings---ishere---getHostname:"+ _mPowermngSettings.getHostname());
                        _mHostNameView.setText(_mPowermngSettings.getHostname());
                    }
                });
            }





        });

*/


    }


    private void savedone(){

  //      _mPowermngSettings.setCpufreq(_mMonitoringView.isChecked());

/*

        int i = _mPowerkeydownView.getSelectedItemPosition();
        Log.d("stvelzhang","Selected is ::" + i + "--powbtnarrays[i]::" + powbtnarrays[i]);
        _mPowermngSettings.setPowerbtn(powbtnarrays[i]);
*/
/*
        _mPowermngSettings.setHostname(_mHostNameView.getText().toString());


        controller.setPowermngSettings(_mPowermngSettings, new CallbackImpl(this){

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                super.onResponse(call, response);
                Log.d("stvelzhang","myclick response::" + response.toString());
            }
        });

        */
        int i = _mrebootdeviceView.getSelectedItemPosition();
        Log.d("stvelzhang","Selected is ::" + i + "--powbtnarrays[i]::" + _mrebootdeviceView.getSelectedItem());

        String selected_action =  _mrebootdeviceView.getSelectedItem().toString();

        Log.d("stvelzhang","Selected is ::" + i + "--selected_action::" + selected_action);


        if(selected_action.equals(getString(R.string.action_reboot)))
        {
            mSystemController.Reboot(null);
            this.showInfo(getString(R.string.Reboot_send));
        }
        else if(selected_action.equals(getString(R.string.action_shutdown)))
        {
            mSystemController.Shutdown(null);
            this.showInfo(getString(R.string.Shutdown_send));
        }
        else if(selected_action.equals(getString(R.string.action_standby)))
        {
            mSystemController.Standby(null);
            this.showInfo(getString(R.string.Standby_send));
        }/*else if(selected_action.equals(getString(R.string.action_wakeup)))
        {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            Host h = jsonRpc.GetHost();
            if( h.getMacAddr() == null || h.getMacAddr().equals("") )
            {
                String strMac = Util.getMacFromArpCache(h.getAddr());

                if(strMac == null)
                {
                    DialogFragment dialog = new YesNoDialog();
                    Bundle args = new Bundle();

                    args.putString("title",getString(R.string.no_mac));
                    args.putString("message",getString(R.string.set_mac));
                    dialog.setArguments(args);
                    //dialog.setTargetFragment(OMVSystemActivity.this, YesNoDialog.YES_NO_CALL);
                    dialog.show(getSupportFragmentManager(), "tag");
                    return;
                }
                h.setMacAddr(strMac);
                HostsDAO datasource = new HostsDAO(this);
                datasource.open();
                datasource.UpdateHost(h);
                datasource.close();

            }

            mSystemController.Wakeup();
            this.showInfo(getString(R.string.Wakeup_send));
        }*/




    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mCheckDirty.Check();

    }

/*
    private void ShowCrons(List<Cron> lst) {
        mCronList.clear();
        mCronList.addAll(lst);

        mCronAdapter.notifyDataSetChanged();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_power, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == R.id.action_reboot)
        {
            mSystemController.Reboot(null);
            this.showInfo(getString(R.string.Reboot_send));
        }
        else if(id == R.id.action_shutdown)
        {
            mSystemController.Shutdown(null);
            this.showInfo(getString(R.string.Shutdown_send));
        }
        /*else if(id == R.id.action_wakeup)
        {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            Host h = jsonRpc.GetHost();
            if( h.getMacAddr() == null || h.getMacAddr().equals("") )
            {
                String strMac = Util.getMacFromArpCache(h.getAddr());

                if(strMac == null)
                {
                    DialogFragment dialog = new YesNoDialog();
                    Bundle args = new Bundle();

                    args.putString("title",getString(R.string.no_mac));
                    args.putString("message",getString(R.string.set_mac));
                    dialog.setArguments(args);
                    //dialog.setTargetFragment(OMVSystemActivity.this, YesNoDialog.YES_NO_CALL);
                    dialog.show(getSupportFragmentManager(), "tag");
                    Log.d("stvelzhang","wakeup---strMac==null");
                    return true;
                }
                Log.d("stvelzhang","wakeup---strMac!=null");
                h.setMacAddr(strMac);
                HostsDAO datasource = new HostsDAO(this);
                datasource.open();
                datasource.UpdateHost(h);
                datasource.close();

            }
            Log.d("stvelzhang","wakeup---getMacAddr!=null ---- " + h.getMacAddr() );
            mSystemController.Wakeup();
            this.showInfo(getString(R.string.Wakeup_send));
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnFinich() {

    }

    @Override
    public void OnCanceled() {

    }

    @Override
    public void OnStarted() {

    }

    @Override
    public void onYesNoActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode ==  Activity.RESULT_OK)
        {
            JSONRPCClient jsonRpc = JSONRPCClient.getInstance();
            Host mHost = jsonRpc.GetHost();
            Bundle bundle = new Bundle();
            bundle.putSerializable("host", mHost);
            Intent intent = new Intent(ShutDownDeviceActivity.this, com.dev.doods.omvremote2.LoginActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

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

//Revo:stvelzhang end
}
