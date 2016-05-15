package com.example.madus.appointments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by madus on 4/20/2016.
 */
public class Thesaurus  {


    final String endpoint = "http://thesaurus.altervista.org/thesaurus/v1";

    public ArrayList<String> SendRequest(String word) {
        ArrayList<String> synonyms = new ArrayList() ;
        try {
            URL serverAddress = new URL(endpoint + "?word="+word+"&language=en_US&key=JM2OaZOML9nkbuav67Dd&output=xml");
            HttpURLConnection connection = (HttpURLConnection)serverAddress.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try{
                    String result = line.substring(line.indexOf("<synonyms>") +10, line.indexOf("</synonyms>"));
                    String [] temp = result.split(Pattern.quote("|"));
                    for(int i =0; i<temp.length;i++){
                        synonyms.add(temp[i]);
                    }

                    //System.out.println(result);
                }catch(Exception e){}

            }


            connection.disconnect();
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.ProtocolException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

       /* System.out.println("");
        System.out.println("");
        System.out.println("");
        for(String s:synonyms){
            System.out.println(s);
        }*/
        return synonyms;

    }


}
