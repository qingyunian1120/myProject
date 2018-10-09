package com.dev.doods.omvremote2.Storage.FileSystems;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.doods.omvremote2.Storage.Smart.SmartController;
import com.dev.doods.omvremote2.Storage.Smart.SmartDevices;
import com.owncloud.android.R;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Client.Call;
import Client.CallbackImpl;
import Client.Response;
import Controllers.SystemController;
import Interfaces.IUpdateActivity;
import Models.Result;
import Models.SettingsNetwork;
import OMV.Base.NavigationBaseActivity;
import utils.Util;

public class FileSystemsActivity extends NavigationBaseActivity implements IUpdateActivity {
    private DisckController mController = new DisckController(this);
    private RecyclerView recyclerView;
    private List<FileSystem> _lst = new ArrayList<FileSystem>();
    private FileSystemsAdapter _adapter;
//Revo:stvelzhang start
    TextView mDevice_name,mDevice_totalsize,mDevice_freesize;
    private SettingsNetwork _mSettingsNetwork;
    private SystemController mSystemController = new SystemController(this);
    private SmartController mSmartController = new SmartController(this);
    private DisckController mDisckController = new DisckController(this);
    private List<SmartDevices> mSmartDevices= new ArrayList<SmartDevices>();
//Revo:stvelzhang end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NavigationId = R.id.nav_disk;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_systems);
/*
        mController.getListDevies(new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<Device> res = response.GetResultObject(new TypeToken<Result<Device>>(){});
                mHandler.post(new Runnable(){
                    public void run() {
                        showDevices(res.getData());
                    }
                });
            }
        });
*/
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        _adapter =new FileSystemsAdapter(FileSystemsActivity.this,mController,_lst);
        recyclerView.setAdapter(_adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        update();
        //Revo:stvelzhang start
        final ViewGroup navigationHeader = (ViewGroup) findNavigationViewChildById(R.id.nav_header_home_view);
        setNavigationHeaderView(navigationHeader);
        //Revo:stvelzhang end
    }

    public void update()
    {
        mController.getListFilesSystems(new CallbackImpl(this) {
            @Override
            public void onResponse(Call call, Response response) throws IOException,InterruptedException {
                super.onResponse(call,response);
                final Result<FileSystem> res = response.GetResultObject(new TypeToken<Result<FileSystem>>(){});
                mHandler.post(new Runnable(){
                    public void run() {ShowFileSystem(res.getData());
                    }
                });
            }
        });
    }

    public List<FileSystem> GetNeedData(List<FileSystem> list, String target) {
        List<FileSystem> tep_lst = new ArrayList<FileSystem>();
        tep_lst.clear();
        tep_lst.addAll(list);
        for (FileSystem item : tep_lst) {
            if( ! item.getType().equals(target)){
                tep_lst.remove(item);
            }
        }
        Log.d("stvelzhang","tep_lst_size" + tep_lst.size());
       return tep_lst;
    }

    public List<FileSystem>  remove32(List<FileSystem> list, String target){
        Iterator<FileSystem> iter = list.iterator();
        while (iter.hasNext()) {
            FileSystem item = iter.next();
            if (! item.getType().equals(target)) {
                iter.remove();
            }
        }
        return list;
    }




    private void ShowFileSystem(List<FileSystem> lst)
    {
        _lst.clear();
        Log.d("stvelzhang","lst_size" + lst.size());
       // _lst.addAll(lst);
        _lst.addAll(remove32(lst,"ext4"));
        _adapter.notifyDataSetChanged();
       // recyclerView.setAdapter(adapter);

    }
/*
    private void showDevices(List<Device> lst)
    {

    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.file_systems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
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

    public List<FileSystem>  remove_mmcblk(List<FileSystem> list, String target){
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
