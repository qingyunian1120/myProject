package com.dev.doods.omvremote2;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
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
import Client.Callback;
import Client.CallbackImpl;
import Client.Response;
import Controllers.SystemController;
import Interfaces.IHandlerCallback;
import Models.Errors;
import Models.Result;
import Models.SettingsNetwork;
import utils.Util;

public class NavHeaderViewUtil {
    private SystemController mSystemController;
    private SmartController mSmartController;
    private DisckController mDisckController;
    private TextView mDevice_name,mDevice_totalsize,mDevice_freesize;
    private List<SmartDevices> mSmartDevices= new ArrayList<SmartDevices>();
    private List<FileSystem> _lst = new ArrayList<FileSystem>();

    public NavHeaderViewUtil(ViewGroup navigationHeader,Activity activity){

        SystemController mSystemController = new SystemController(activity);
        SmartController mSmartController = new SmartController(activity);
        DisckController mDisckController = new DisckController(activity);
        if (navigationHeader != null) {
            mDevice_name = navigationHeader.findViewById(R.id.device_name);
            mDevice_totalsize = navigationHeader.findViewById(R.id.device_totalsize);
            mDevice_freesize = navigationHeader.findViewById(R.id.device_freesize);
        }

    }

    public  void setNavigationHeaderView(ViewGroup navigationHeader,Activity activity) {

         Handler mHandler = new Handler();


        if (navigationHeader != null) {


            mDevice_totalsize.setText("250GIB");




            mSystemController.getGeneralSettingsNetwork(new Callback() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    SettingsNetwork _mSettingsNetwork =response.GetResultObject( new TypeToken<SettingsNetwork>(){});

                    Log.d("stvelzhang","getPowermngSettings---ishere---_mPowermngSettings:"+ _mSettingsNetwork);
                    mHandler.post(new Runnable(){
                        public void run() {

                            Log.d("stvelzhang","getPowermngSettings---ishere---getHostname:"+ _mSettingsNetwork.getHostname());
                            mDevice_name.setText(_mSettingsNetwork.getHostname());
                        }
                    });
                }
            });


            mSmartController.getListDevices(new Callback() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    final Result<SmartDevices> res = response.GetResultObject(new TypeToken<Result<SmartDevices>>(){});

                    mHandler.post(new Runnable(){
                        public void run() {
                            populateViews(res.getData());
                        }
                    });
                }
            });

            mDisckController.getListFilesSystems(new Callback() {
                @Override
                public void onFailure(Call call, Exception e) {

                }

                @Override
                public void OnOMVServeurError(Call call, Errors error) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                    final Result<FileSystem> res = response.GetResultObject(new TypeToken<Result<FileSystem>>(){});
                    mHandler.post(new Runnable(){
                        public void run() {

                            ShowFileSystem(res.getData());
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

    public  void ShowFileSystem(List<FileSystem> lst)
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

    public void populateViews(List<SmartDevices> smartDevices)
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


}
