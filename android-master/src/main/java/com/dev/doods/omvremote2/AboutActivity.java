package com.dev.doods.omvremote2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


import net.hockeyapp.android.FeedbackManager;
import net.hockeyapp.android.Tracking;
import net.hockeyapp.android.utils.Util;

import com.owncloud.android.MainApp;
import com.owncloud.android.R;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Models.Errors;
import OMV.Base.AppCompatBaseActivity;
import utils.CallBackAsyncTask;
import utils.CallBackTask;
import utils.SendLogsTask;
import utils.SnackBarError;
//import static org.acra.ACRA.LOG_TAG;

public class AboutActivity extends AppCompatBaseActivity {

//    // The helper object
//    IabHelper mHelper;
//    // Provides purchase notification while this app is running
//    IabBroadcastReceiver mBroadcastReceiver;

    private Button tryToPurchaseBtn;
    // Does the user have the premium upgrade?
    boolean mIsPremium = false;

    // Tracks the currently owned infinite gas SKU, and the options in the Manage dialog
    String mPremiumSku = "";
    // Will the subscription auto-renew?
    boolean mAutoRenewEnabled = false;
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;


    private TextView mVersionView;
    private TextView mVersionCodeView;
    private Button mSendLogBtn;
    private Button mSendFeedbackBtn;
    private WebView mWebView;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mIsPremium = !MainApp.light;
        super.onCreate(savedInstanceState);
        setContentView(com.owncloud.android.R.layout.activity_about);

        FeedbackManager.register(this);
        init();
        mVersionView = (TextView)  findViewById(R.id.tvVersion);
        mVersionCodeView= (TextView)  findViewById(R.id.tvCodeVersion);
        mSendLogBtn = (Button) findViewById(R.id.SendLogBtn);
        mSendFeedbackBtn =(Button) findViewById(R.id.SendFeedbackBtn);



        mSendLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ACRA.getErrorReporter().handleException(null);
                sendFeedback();
            }
        });

        mSendFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackManager.showFeedbackActivity(AboutActivity.this);
            }
        });

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        setClickableSpan();
        FindVersionCode();
        ParsingChangeLogs();
    }

    private void setClickableSpan()
    {
        TextView translate = (TextView)  findViewById(R.id.click_translate);


        SpannableString spannableString = new SpannableString(getString(R.string.action_click_translate));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.getlocalization.com/OMV_REMOTE")));
            }
        };
        spannableString.setSpan(clickableSpan, spannableString.length() -20,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        translate.setText(spannableString, TextView.BufferType.SPANNABLE);
        translate.setMovementMethod(LinkMovementMethod.getInstance());


        translate = (TextView)  findViewById(R.id.click_forum);


        spannableString = new SpannableString(getString(R.string.action_click_forum));
        clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.openmediavault.org/index.php/Thread/15658-unofficial-OMV-APP-for-Android/")));
            }
        };
        spannableString.setSpan(clickableSpan, spannableString.length() -16,
                spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        translate.setText(spannableString, TextView.BufferType.SPANNABLE);
        translate.setMovementMethod(LinkMovementMethod.getInstance());


    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private void FindVersionCode()
    {
        try {

            PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);

           String code = ""+pinfo.versionCode;
            String name = pinfo.versionName;

            mVersionView.setText(name);
            mVersionCodeView.setText(code);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void ParsingChangeLogs()
    {
        try {

            InputStream is = getAssets().open("changeslogs.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("release");

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            sb.append("<head>");
            sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"bootstrap.css\" />");
            sb.append("</head>");


            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    String version = element2.getAttribute("version");

                    sb.append("<h1>VERSION ");
                    sb.append(version);
                    sb.append("</h1>");
                    NodeList nodeList = element2.getElementsByTagName("change");
                    sb.append("<ul>");
                    for (int j=0; j<nodeList.getLength(); j++) {

                        Node changeNode = nodeList.item(j);
                        sb.append("<li>");
                        sb.append(changeNode.getChildNodes().item(0).getNodeValue());
                        sb.append("</li>");

                    }
                    sb.append(" </ul>");
                    element2.getAttribute("versioncode");
                    //tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    //tv1.setText(tv1.getText()+"Surname : " + getValue("surname", element2)+"\n");
                    //tv1.setText(tv1.getText()+"-----------------------");
                }
            }

            sb.append("<script src=\"bootstrap.js\"></script>");
            sb.append("</body></html>");
            mWebView.loadDataWithBaseURL("file:///assets/", sb.toString(), "text/html", "UTF-8", null);
        }
        catch (Exception e) {e.printStackTrace();}

    }
    protected void init() {
        // Create the helper, passing it our context and the public key to verify signatures with

    }



    private void sendFeedback()
    {
        if (!Util.isConnectedToNetwork(this)) {
            Snackbar.make(mSendLogBtn,R.string.error_no_network_message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return;
        }


        AsyncTask task = new SendLogsTask(this);
        new CallBackAsyncTask(task, new CallBackTask() {
            @Override
            public void handleMessageError(@Nullable Errors error) {
                new SnackBarError(mSendLogBtn,error.getMessage(),false);
            }

            @Override
            public void handleFinich() {
                Snackbar.make(mSendLogBtn,R.string.send, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }).run();

    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
