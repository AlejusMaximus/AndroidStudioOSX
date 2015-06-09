package com.apps101.aleixpm.visualtest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;



public class MainActivity extends Activity {

    // Debugging
    private static final String TAG = "Visual_Engin_Main";
    private static final String TAG_PARSE = "ParseURL";
    private static final boolean D = true;

    private static String FAIL = "FAIL";
    private static String PASS = "PASS";

    public TextView outputURLs;

    /**
     * Hardcoded website. Comment one of the following declarations in order to get diferent Unit Test Result.
     */
    //String siteUrl = "http://www.visual-engin.com";   //Unit Test -> NOT PASS
    String siteUrl = "http://www.visual-engin.com/Web"; //Unit Test -> PASS

    // create  a signal to let us know when our task is done.
    //final CountDownLatch signal = new CountDownLatch(1);

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
        outputURLs.setMovementMethod(new ScrollingMovementMethod());

        if(D) Log.e(TAG, "...START TEST...");

        try {
            unitTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class is used for Parse the desired URL
     * @author aleixpm
     * @version 1.0
     */
    public class ParseURL extends AsyncTask <String, Void, String> {
        /**
         * This method executes the URL parse InBackground
         * @return String which will fill the outputURLs
         */
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder myStringBuilder = new StringBuilder();
            Document doc;
            try {
                if(D) Log.e(TAG_PARSE , "Connecting to ["+strings[0]+"]");
                doc  = Jsoup.connect(strings[0]).get();
                if(D) Log.e(TAG_PARSE , "Connected to ["+strings[0]+"]");
                Elements links = doc.select("a[href]");
                if(D) Log.e(TAG_PARSE , "Element links...");
                for (Element link : links) {
                    String linkHref = link.attr("abs:href");
                    myStringBuilder.append(linkHref).append("\n");
                    if(D) Log.e(TAG_PARSE , "linkHref:"+linkHref);
                    String linkText = link.text();
                    if(D) Log.e(TAG_PARSE , "linkText:"+linkText);
                }
                if(D) Log.e(TAG_PARSE , "...try finished..");

            } catch (IOException e) {
                if(D) Log.e(TAG_PARSE , "...FAIL Catched..");
                return FAIL;
            }
            if(D) Log.e(TAG_PARSE , "...myStringBuilder returned..");
            return myStringBuilder.toString();
        }
        /**
         * onPostExecute displays the results of the AsyncTask. It sets outputURLs TextView.
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(D) Log.e(TAG_PARSE , "...inside onPostExecute..");
            if(result != null){
                if(D) Log.e(TAG_PARSE , "result != null");
                outputURLs.setText(result);
                if(D) Log.e(TAG_PARSE , "...onPostExecute: text set :-)");
            }else{
                if(D) Log.e(TAG_PARSE , "result == null");
            }
            //signal.countDown();
            unitTestOnPostExecute();
        }
    }
    /**
     * This method is used for unit testing OnPostExecute method of our AsyncTask: ParseURL.
     * @author aleixpm
     * @version 1.0
     */
    private void unitTestOnPostExecute() {
        if(D) Log.e(TAG, "+++ unitTestOnPostExecute() +++");
        String checkOutputURLs = outputURLs.getText().toString();
        if("".equals(checkOutputURLs)){
            if(D) Log.e(TAG, FAIL);
            Toast.makeText(this, FAIL, Toast.LENGTH_LONG).show();
        }else {
            if (D) Log.e(TAG, PASS);
            Toast.makeText(this, PASS, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is used for unit testing doInBackground method of our AsyncTask: ParseURL.
     * @author aleixpm
     * @version 1.0
     */
    public void unitTest() throws InterruptedException {
        if(D) Log.e(TAG, "+++ unitTest() +++");
        (new ParseURL()).execute(siteUrl);
        //signal.await(10, TimeUnit.SECONDS);
        if(D) Log.e(TAG, "--- unitTest()  ---");
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
