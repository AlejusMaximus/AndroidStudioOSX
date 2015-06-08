package com.apps101.aleixpm.visualtest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends Activity {

    // Debugging
    private static final String TAG = "Visual_Engin_Main";
    private static final String TAG_PARSE = "ParseURL";
    private static final boolean D = true;

    private static String FAIL = "FAIL";
    private static String PASS = "PASS";
    public TextView outputURLs;

    //Hardcoded website:
    String siteUrl = "http://www.visual-engin.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(D) Log.e(TAG, "Checking Network connection");
        ConnectivityManager myConnMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo = myConnMgr.getActiveNetworkInfo();
        if (myNetworkInfo != null && myNetworkInfo.isConnected()) {
            Toast.makeText(this, "Network available", Toast.LENGTH_LONG).show();
            if(D) Log.e(TAG, "Network Connection Available");
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            if(D) Log.e(TAG, "Network Connection NOT Available");
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");
        //Layout TextView is linked:
        outputURLs = (TextView) findViewById(R.id.outputURLs);
        if(D) Log.e(TAG, "...calling unit Test...");
        unitTest();
        /*
        boolean result = unitTest();
        if(result) {
            Toast.makeText(this, "UnitTest PASS", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "UnitTest NOT PASS", Toast.LENGTH_LONG).show();
        }
        */
    }
    /**
     * This class is used for Parse the desired URL
     * @author aleixpm
     * @version 1.0
     */
    public class ParseURL extends AsyncTask<String, Void, String>{
        /**
         * This method executes the URL parse InBackground
         * @return String which will fill the outputURLs
         */
        @Override
        protected String doInBackground(String... strings) {
            StringBuffer myStringBuffer = new StringBuffer();
            try {
                if(D) Log.e(TAG_PARSE , "Connecting to ["+strings[0]+"]");
                Document doc  = Jsoup.connect(strings[0]).get();
                if(D) Log.e(TAG_PARSE , "Connected to ["+strings[0]+"]");

                Element content = doc.getElementById("content");
                Elements links = content.getElementsByTag("a");//Results to Null pointer Exception
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    myStringBuffer.append(linkHref +"\n");
                    if(D) Log.e(TAG_PARSE , "linkHref:"+linkHref);
                    String linkText = link.text();
                    if(D) Log.e(TAG_PARSE , "linkText:"+linkText);
                }
                if(D) Log.e(TAG_PARSE , "...try finished..");

            } catch (IOException e) {
                if(D) Log.e(TAG_PARSE , "...FAIL Catched..");
                return FAIL;
            }
            if(D) Log.e(TAG_PARSE , "...myStringBuffer returned..");
            return myStringBuffer.toString();
        }
        /**
         * onPostExecute displays the results of the AsyncTask. It sets outputURLs TextView.
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(D) Log.e(TAG_PARSE , "...inside onPostExecute..");
            outputURLs.setText(result);
            if(D) Log.e(TAG_PARSE , "...onPostExecute: text set..");
        }
    }

    /**
     * This method is used for unit testing, it print the result: FAIL or PASS.
     * @author aleixpm
     * @version 1.0
     */
    public void unitTest(){
        if(D) Log.e(TAG, "+++ myUnitTest() +++");
        (new ParseURL()).execute(siteUrl);
        /*
        String checkOutputURLs = outputURLs.getText().toString();
        if(FAIL.equals(checkOutputURLs)){
            if(D) Log.e(TAG, FAIL);
            return false;
        }else {
            if (D) Log.e(TAG, PASS);
            return true;
        }
         */
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }
}
