package com.apps101.aleixpm.visualtest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    TextView outputURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputURLs = (TextView) findViewById(R.id.outputURLs);
        UnitTest();
    }
    /**
     * This method is used for unit testing
     * @author aleixpm
     * @version 1.0
     */
    public boolean UnitTest(){
        Log.d("Jsoup","Inside UnitTest");
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
