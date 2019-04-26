package com.essths.pc.applicationessths;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
public class Fragmentnot extends Fragment {

    ListView listvfluxnot;
    ImageView img;
    ArrayList<String> titless;
    ArrayList<String> category;
    ArrayList<String> date;
    ArrayList<String> cible;

    public Fragmentnot() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_fragmentnot, container, false);

        listvfluxnot = (ListView)v.findViewById(R.id.listvfluxnot);
        img= (ImageView)v.findViewById(R.id.iv);

        titless = new ArrayList<String>();
        category = new ArrayList<String>();
        date = new ArrayList<String>();
        cible = new ArrayList<String>();



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
               // URL url = new URL("http://www.lefigaro.fr/rss/figaro_international.xml");
                //URL url = new URL("https://www.shemsfm.net/fr/rss");
               // URL url = new URL("http://192.168.56.1/ghassen/Class.xml");
               // URL url = new URL("http://192.168.1.5/ghassen/class.xml");
                URL url = new URL("http://172.16.51.73/ghassen/class.xml");




                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem1 = false;
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("lfsi3"))
                            {
                            insideItem1 = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem1)
                            {
                                titless.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("Date"))
                        {
                            if (insideItem1)
                            {
                                category.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("Description"))
                        {
                            if (insideItem1)
                            {
                                date.add(xpp.nextText());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("Cible"))
                        {
                            if (insideItem1)
                            {
                                cible.add(xpp.nextText());
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("lfsi3"))
                    {
                        insideItem1 = false;
                    }
                    eventType = xpp.next();
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
           /* ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, titless);
            listvfluxnot.setAdapter(adapter1);*/
           customAdapter customAdapter1=new customAdapter();
            listvfluxnot.setAdapter(customAdapter1);





        }
    }

    class customAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titless.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.notcustomlay,null);

           // view=getLayoutInflater().inflate(R.layout.notcustomlay,null);
            TextView tvtitle=(TextView) view.findViewById(R.id.tv1);
            TextView tvauthor=(TextView) view.findViewById(R.id.tv2);
            TextView tvdate=(TextView) view.findViewById(R.id.tv3);
            ImageView imagev=(ImageView) view.findViewById(R.id.iv);

            tvtitle.setText(titless.get(position));
            tvauthor.setText(category.get(position));
            tvdate.setText(date.get(position));


           /* if((cible.get(position)).equals("lfsi3")){
                tvtitle.setText(titless.get(position));
            tvauthor.setText(category.get(position));
            tvdate.setText(date.get(position));
                }/*else{position=position-1;}

           /* if((category.get(position)).equals("Le figaro.fr")){
            imagev.setImageResource(R.drawable.res);}
            else{imagev.setImageResource(R.drawable.doc);}*/


            return view;
        }
    }

}
