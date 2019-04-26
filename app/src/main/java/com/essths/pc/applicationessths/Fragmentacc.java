package com.essths.pc.applicationessths;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmentacc extends Fragment {
    ListView listvflux;
    ArrayList<String> titles;
   public ArrayList<String> links;

    public Fragmentacc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_fragmentacc, container, false);
        listvflux = (ListView)v.findViewById(R.id.listvflux);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        listvflux.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        new ProcessInBackground().execute();
        return v;

    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        Exception exception = null;
        @Override
        protected Exception doInBackground(Integer... params) {
            try
            {
                //URL url = new URL("http://feeds.24.com/articles/Fin24/News/
               // URL url = new URL("http://www.lefigaro.fr/rss/figaro_international.xml");
               // URL url = new URL("http://172.16.51.73/ghassen/rssfeed.xml");
               // URL url = new URL("http://192.168.56.1/ghassen/Class.xml");
                //URL url = new URL("http://192.168.1.5/ghassen/class.xml");
                URL url = new URL("http://172.16.51.73/ghassen/class.xml");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if ((xpp.getName().equalsIgnoreCase("AllStudent"))||(xpp.getName().equalsIgnoreCase("AllSchool")))
                        {

                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                titles.add(xpp.nextText());
                            }
                        }

                    }
                    else if ((eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("AllStudent"))||(eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("AllSchool")))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                    ///////

                   /* if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                titles.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                links.add(xpp.nextText());
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();*/
                }
            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, titles);
            listvflux.setAdapter(adapter);
        }
    }


}
