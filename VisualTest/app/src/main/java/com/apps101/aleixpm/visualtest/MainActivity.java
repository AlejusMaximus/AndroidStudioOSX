package com.apps101.aleixpm.visualtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
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

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    // Debugging
    private static final String TAG = "Visual_Engin";
    private static final boolean D = true;

    TextView outputURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connecting to the Network:
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(this, "Network available", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");
        outputURLs = (TextView) findViewById(R.id.outputURLs);

        UnitTest();

    }

    /**
     * This method is used for unit testing
     * @author aleixpm
     * @version 1.0
     * @param TODO
     * @return true or false
     */
    public boolean UnitTest(){
        Log.d("Jsoup", "Inside UnitTest");
        //TODO:
        //http://jsoup.org/cookbook/extracting-data/dom-navigation
        //http://www.survivingwithandroid.com/2014/04/parsing-html-in-android-with-jsoup.html
        String website = "http://www.visual-engin.com";
        Document doc = null;
        try {
            doc = Jsoup.connect(website).get();
            //http://jsoup.org/cookbook/input/load-document-from-url
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        Element content = doc.getElementById("content");
        Elements links = content.getElementsByTag("a");
        for (Element link : links) {
            String linkHref = link.attr("href");
            outputURLs.append(linkHref +"\n");
            String linkText = link.text();
        }
        return true;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
