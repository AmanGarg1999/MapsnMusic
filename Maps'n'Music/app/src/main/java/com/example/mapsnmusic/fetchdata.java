package com.example.mapsnmusic;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchdata extends AsyncTask<Void,Void,Void> {
    String data;
    String line = "";
    String dataParsed = "";
    String temperature;
    String music;
    String mood;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=43.054304&lon=-77.606241&APPID=c2669574602fa7abeb8a87e07656a42b");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            //String singleParsed = "";
            while(line != null)
            {
                line = br.readLine();
                data= data + line;
            }

            String[] info;

            info=data.split(":");
            String[] s=info[11].split(",");
            temperature=s[0];
            //ystem.out.println(temperature);

            double high = 283.15;
            double low = 273.15;
//            if(args.length ==2){
//                try{
//                    double temp = Double.parseDouble(temperature);
//                    boolean traffic = Boolean.parseBoolean(args[1]);
//
//                    if(traffic){
//                        System.out.println("High Traffic - Playing High Traffic Music");
//                    }
//                    else{
                         double temp = Double.parseDouble(temperature);
                        if(temp>high){
                            music = "High Temperature - Playing High Temperature Music";
                            mood = "high";
                        }
                        else if(temp<low){
                            music = "Low Temperature - Playing Low Temperature Music";
                            mood = "medium";
                        }
                        else{
                            music = "Moderate Temperature - Playing Moderate Temperature Music";
                            mood = "low";
                        }
//                    }
//                }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.loc.setText(this.music);
        MainActivity.mood = this.mood;
    }


}

