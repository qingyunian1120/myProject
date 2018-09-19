package OMVFragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.google.gson.reflect.TypeToken;
import com.owncloud.android.R;

import java.io.IOException;

import Client.Call;
import Client.Callback;
import Client.Response;
import Controllers.ServicesController;
import Interfaces.IUpdateFragment;
import Interfaces.OnFragmentInteractionListener;
import Models.Errors;
import Models.FTPSettings;

public class FTPFragment extends Fragment implements IUpdateFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ServicesController mController ;
    Handler handler;
    private FTPSettings mFTPSettings;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public FTPFragment(){
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FTPFragment newInstance(String param1, String param2) {
        FTPFragment fragment = new FTPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Switch mSwitchEnable;
    private EditText mPort;
    private EditText mMaxClients;
    private EditText mMaxConnectionSperHost;
    private EditText mMaxLoginAttempts;
    private EditText mTimeOutIdle;
    private Switch mSwitchAnonymous;
    private EditText mDisplayLogin;
    private Switch mRootLogin;
    private Switch mRequireValidShell;
    private Switch mLimitTransferRate;
    private EditText mMaxUpTransferRate;
    private EditText mMaxDownTransferRate;
    private Switch mUsePassivePorts;
    private EditText mMinPassivePorts;
    private EditText mMaxPassivePorts;
    private EditText mMasqueradeAddress;
    private EditText mDynmasqreFresh;
    private Switch mAllowForeignAddress;
    private Switch mAllowRestart;
    private Switch mIdentLookups;
    private Switch mUserEversedns;
    private Switch mTransferlog;
    private EditText mExtraOptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_ftp,container,false);
        handler = new Handler();
        mSwitchEnable = rootView.findViewById(R.id.swithEnable);
        mPort = rootView.findViewById(R.id.etPort);
        mMaxClients = rootView.findViewById(R.id.maxclients);
        mMaxConnectionSperHost = rootView.findViewById(R.id.maxconnectionsperhost);
        mMaxLoginAttempts = rootView.findViewById(R.id.maxloginattempts);
        mTimeOutIdle = rootView.findViewById(R.id.timeoutidle);
        mSwitchAnonymous = rootView.findViewById(R.id.anonymous);
        mDisplayLogin = rootView.findViewById(R.id.displaylogin);
        mRootLogin = rootView.findViewById(R.id.rootlogin);
        mRequireValidShell = rootView.findViewById(R.id.requirevalidshell);
        mLimitTransferRate = rootView.findViewById(R.id.limittransferrate);
        mMaxUpTransferRate = rootView.findViewById(R.id.maxuptransferrate);
        mMaxDownTransferRate = rootView.findViewById(R.id.maxdowntransferrate);
        mUsePassivePorts = rootView.findViewById(R.id.usepassiveports);
        mMinPassivePorts = rootView.findViewById(R.id.minpassiveports);
        mMaxPassivePorts = rootView.findViewById(R.id.maxpassiveports);
        mMasqueradeAddress = rootView.findViewById(R.id.masqueradeaddress);
        mDynmasqreFresh = rootView.findViewById(R.id.dynmasqrefresh);
        mAllowForeignAddress = rootView.findViewById(R.id.allowforeignaddress);
        mAllowRestart = rootView.findViewById(R.id.allowrestart);
        mIdentLookups = rootView.findViewById(R.id.identlookups);
        mUserEversedns = rootView.findViewById(R.id.usereversedns);
        mTransferlog = rootView.findViewById(R.id.transferlog);
        mExtraOptions = rootView.findViewById(R.id.extraoptions);

        mController.getService("FTP", new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {
                Log.d("xuzhenyue","onFailure == " + e);

            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                Log.d("xuzhenyue","OnOMVServeurError = " +error);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                Log.d("xuzhenyue","onResponse");
                mFTPSettings = response.GetResultObject(new TypeToken<FTPSettings>(){});

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("xuzhenyue","onResponse  post");
                        mSwitchEnable.setChecked(mFTPSettings.getEnable());
                        mPort.setText(mFTPSettings.getPort().toString());
                        mMaxClients.setText(mFTPSettings.getMaxclients().toString());
                        mMaxConnectionSperHost.setText(mFTPSettings.getMaxconnectionsperhost().toString());
                        mMaxLoginAttempts.setText(mFTPSettings.getMaxloginattempts().toString());
                        mTimeOutIdle.setText(mFTPSettings.getTimeoutidle().toString());
                        mSwitchAnonymous.setChecked(mFTPSettings.getAnonymous());
                        mDisplayLogin.setText(mFTPSettings.getDisplaylogin().toString());
                        mRootLogin.setChecked(mFTPSettings.getRootlogin());
                        mRequireValidShell.setChecked(mFTPSettings.getRequirevalidshell());
                        mLimitTransferRate.setChecked(mFTPSettings.getLimittransferrate());
                        mMaxUpTransferRate.setText(mFTPSettings.getMaxuptransferrate().toString());
                        mMaxDownTransferRate.setText(mFTPSettings.getMaxdowntransferrate().toString());
                        mUsePassivePorts.setChecked(mFTPSettings.getUsepassiveports());
                        mMinPassivePorts.setText(mFTPSettings.getMinpassiveports().toString());
                        mMaxPassivePorts.setText(mFTPSettings.getMaxpassiveports().toString());
                        mMasqueradeAddress.setText(mFTPSettings.getMasqueradeaddress().toString());
                        mDynmasqreFresh.setText(mFTPSettings.getDynmasqrefresh().toString());
                        mAllowForeignAddress.setChecked(mFTPSettings.getAllowforeignaddress());
                        mAllowRestart.setChecked(mFTPSettings.getAllowrestart());
                        mIdentLookups.setChecked(mFTPSettings.getIdentlookups());
                        mUserEversedns.setChecked(mFTPSettings.getUsereversedns());
                        mTransferlog.setChecked(mFTPSettings.getTransferlog());
                        mExtraOptions.setText(mFTPSettings.getExtraoptions().toString());
                    }
                });
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public void OnMessage(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessage(msg);
        }
    }

    public void OnError(String msg)
    {
        if (mListener != null) {
            mListener.onFragmentMessageError(msg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mController = new ServicesController(getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void Update() {

        Log.d("xuzhenyue","Update()");
        mFTPSettings.setEnable(mSwitchEnable.isChecked());
        mFTPSettings.setPort(Integer.valueOf(mPort.getText().toString()));
        mFTPSettings.setMaxclients(Integer.valueOf(mMaxClients.getText().toString()));
        mFTPSettings.setMaxconnectionsperhost(Integer.valueOf(mMaxConnectionSperHost.getText().toString()));
        mFTPSettings.setMaxloginattempts(Integer.valueOf(mMaxLoginAttempts.getText().toString()));
        mFTPSettings.setTimeoutidle(Integer.valueOf(mTimeOutIdle.getText().toString()));
        mFTPSettings.setAnonymous(mSwitchAnonymous.isChecked());
        mFTPSettings.setDisplaylogin(mDisplayLogin.getText().toString());
        mFTPSettings.setRootlogin(mRootLogin.isChecked());
        mFTPSettings.setRequirevalidshell(mRequireValidShell.isChecked());
        mFTPSettings.setLimittransferrate(mLimitTransferRate.isChecked());
        mFTPSettings.setMaxuptransferrate(Integer.valueOf(mMaxUpTransferRate.getText().toString()));
        mFTPSettings.setMaxdowntransferrate(Integer.valueOf(mMaxDownTransferRate.getText().toString()));
        mFTPSettings.setUsepassiveports(mUsePassivePorts.isChecked());
        mFTPSettings.setMinpassiveports(Integer.valueOf(mMinPassivePorts.getText().toString()));
        mFTPSettings.setMaxpassiveports(Integer.valueOf(mMaxPassivePorts.getText().toString()));
        mFTPSettings.setMasqueradeaddress(mMasqueradeAddress.getText().toString());
        mFTPSettings.setDynmasqrefresh(Integer.valueOf(mDynmasqreFresh.getText().toString()));
        mFTPSettings.setAllowforeignaddress(mAllowForeignAddress.isChecked());
        mFTPSettings.setAllowrestart(mAllowRestart.isChecked());
        mFTPSettings.setIdentlookups(mIdentLookups.isChecked());
        mFTPSettings.setUsereversedns(mUserEversedns.isChecked());
        mFTPSettings.setTransferlog(mTransferlog.isChecked());
        mFTPSettings.setExtraoptions(mExtraOptions.getText().toString());

        mController.setService(mFTPSettings, new Callback() {
            @Override
            public void onFailure(Call call, Exception e) {
                OnMessage(mFTPSettings.getServiceName()+"  fail");
            }

            @Override
            public void OnOMVServeurError(Call call, Errors error) {
                OnError(error.toString());
                Log.d("xuzhenyue","OnOMVServeurError 111" + error.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException, InterruptedException {
                Log.d("xuzhenyue","onResponse setservice");
                OnMessage(mFTPSettings.getServiceName()+": config saved");
            }
        });
    }

    @Override
    public String getServiceName() {
        return mFTPSettings.getServiceName();
    }
}
