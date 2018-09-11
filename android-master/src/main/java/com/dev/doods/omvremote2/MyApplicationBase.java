package com.dev.doods.omvremote2;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/*import com.dev.doods.omvremote2.Billing.IabBroadcastReceiver;
import com.dev.doods.omvremote2.Billing.IabHelper;
import com.dev.doods.omvremote2.Billing.IabResult;
import com.dev.doods.omvremote2.Billing.Inventory;
import com.dev.doods.omvremote2.Billing.Purchase;*/
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.owncloud.android.BuildConfig;
//import com.microsoft.azure.mobile.MobileCenter;
//import com.microsoft.azure.mobile.analytics.Analytics;
//import com.microsoft.azure.mobile.crashes.Crashes;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.metrics.MetricsManager;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.Util;

//import org.acra.ACRA;
//import org.acra.ReportField;
//import org.acra.ReportingInteractionMode;
//import org.acra.annotation.ReportsCrashes;

import OMV.Classe.MyCustomCrashManagerListener;

//import static com.dev.doods.omvremote2.Billing.IabHelper.SKU_PREMUIM_YEARLY;

/**
 * Created by thiba on 21/10/2016.
 */

/*@ReportsCrashes(mailTo = "doods.dev@gmail.com",
        customReportContent = {ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)*/
public class MyApplicationBase extends Application {

    // The helper object
//    IabHelper mHelper;
    private static Context context;
    public static AdRequest mAdRequest;
    public static AdView mAdViewSmall;
    public static AdView mAdViewSmallHaeder;
    public static boolean light;
    public boolean mIsPremium;
    public static NativeExpressAdView mNativeExpressAdView;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override

    public void onCreate() {
        super.onCreate();
//        checkForPremium();

        //ACRA.init(this);
        //MobileCenter.setLogLevel(Log.VERBOSE);

        MyApplicationBase.context = getApplicationContext();
        checkForCrashes();
        HockeyLog.setLogLevel(Log.DEBUG);
        //MobileCenter.start(this, "e7034db4-8db6-474a-a022-66771fb1e3f6",
        //        Analytics.class, Crashes.class);
        MetricsManager.register(this);
        MetricsManager.trackEvent("GET_LOGS_FILE");

       // if(light) {
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-4922361220283829/4711179794");
            MobileAds.initialize(getApplicationContext(), "ca-app-pub-4922361220283829/7664646197");
            mAdRequest = new AdRequest.Builder().build();

            mAdViewSmall = new AdView(this);
            mAdViewSmall.setAdSize(AdSize.BANNER);
            mAdViewSmall.setAdUnitId("ca-app-pub-4922361220283829/4711179794");
            mAdViewSmall.loadAd(new AdRequest.Builder().build());

            mAdViewSmallHaeder = new AdView(this);
            mAdViewSmallHaeder.setAdSize(AdSize.BANNER);
            mAdViewSmallHaeder.setAdUnitId("ca-app-pub-4922361220283829/3076009396");
            mAdViewSmallHaeder.loadAd(new AdRequest.Builder().build());

            mNativeExpressAdView = new NativeExpressAdView(this);
            mNativeExpressAdView.setAdSize(new AdSize(360, 132));
            mNativeExpressAdView.setAdUnitId("ca-app-pub-4922361220283829/7664646197");
            mNativeExpressAdView.loadAd(new AdRequest.Builder().build());
       // }
    }





    private void checkForCrashes() {

        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
           String str = (String) bundle.get("omg.doods.dev.omremote.base.appIdentifier");
            //light = str.equals("light");
        } catch (PackageManager.NameNotFoundException e) {
            //light = false;
            e.printStackTrace();
        }
        String appIdentifier = Util.getAppIdentifier(context);

        if (!BuildConfig.DEBUG) {
            CrashManager.register(context, appIdentifier, new MyCustomCrashManagerListener());
            //CrashManager.register(this);
        }


    }


    public static Context getAppContext() {
        return MyApplicationBase.context;
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}