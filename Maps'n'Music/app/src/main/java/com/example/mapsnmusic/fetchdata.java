package com.example.mapsnmusic;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class fetchdata extends AsyncTask<Void,Void,Void> {
    String data;
    String line = "";
    String temperature;
    String music;
    String mood;
    public String idnum;
    @Override
    public Void doInBackground(Void... voids) {
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
            String[] tempr=info[11].split(",");
            temperature=tempr[0];
            String[] id = info[5].split(",");
            this.idnum = id[0];
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
    public void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //MainActivity.loc.setText(this.idnum);
        MainActivity.mood = this.mood;
        HashMap<Integer, String> dictionary = new HashMap<>();
        idtoConditions itc = new idtoConditions();
        itc.loadDictionary(dictionary);
        String condition = itc.getWeather(Integer.parseInt(idnum),dictionary);
        int first = Integer.parseInt(idnum);
        int firstnum = first%10;

        MainActivity.cond.setText("It's " + condition);
        MainActivity.temp.setText(this.temperature);

    }

}

