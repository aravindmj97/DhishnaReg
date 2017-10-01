package com.iamaravind.dhishnareg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

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

import es.dmoral.toasty.Toasty;

/**
 * Created by ARAVIND on 01-10-2017.
 */

public class BackgroundWorker2 extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    String type, eidresult,n;
    BackgroundWorker2(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        type = params[0];
        String json_url = "http://u1701374.nettech.firm.in/tojson.php";
        DataBaseHelper db = new DataBaseHelper(context);
        if (type.equals("getjson")){
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput((true));
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result ="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                //      return result;
                boolean res = true;
                JSONArray jsonArray = null;
                try {

                    jsonArray = new JSONArray(result);
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);

                        String tempEid = jsonObject.getString("eid");

                        String tempEname = jsonObject.getString("ename");

                        res = db.insertDb(tempEid, tempEname);
                    }
                    return String.valueOf(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String result) {
        if(type.equals("getjson")){
            configToasty();
            if(result == "true")
                Toasty.info(context,"Successfully Fetched JSON Event List Filled", Toast.LENGTH_LONG,true).show();
            else if (result == "false")
                Toasty.error(context,"Error while Fetching JSON Contact Dev!!", Toast.LENGTH_LONG,true).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void configToasty()
    {
        Toasty.Config.getInstance().
                setErrorColor(ContextCompat.getColor(context, R.color.errorColor))
                .apply();
    }
}

