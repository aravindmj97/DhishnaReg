package com.iamaravind.dhishnareg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker2 extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    String type, eidresult;

     BackgroundWorker2(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        eidresult = params[2];
        String fromqr = params[1];
        type = params[0];
        String checkin_url = "http://u1701374.nettech.firm.in/checkin.php";
        if (type.equals("checkin")) {
            try {
                URL url = new URL(checkin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput((true));
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("parti_id", "UTF-8") + "=" + URLEncoder.encode(fromqr, "UTF-8") + "&"
                        + URLEncoder.encode("org_id", "UTF-8") + "=" + URLEncoder.encode(eidresult, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                return result;
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
      //  alertDialog = new AlertDialog.Builder(context).create();
       // alertDialog.setTitle("Checkin Status");

    }

    @Override
    protected void onPostExecute(String result) {
        // alertDialog.setMessage(result);
        // alertDialog.show();
        String error = "Wrong Credentials";
      if (type.equals("checkin")) {
            if (result.equals("1")) {
                Intent checkinsucc = new Intent(context,CheckinsuccessActivity.class);
            //           .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("eidd", eidresult);
                checkinsucc.putExtras(bundle);
                context.startActivity(checkinsucc);
            } else if (result.equals("0")) {
                Intent checkinfail = new Intent(context, CheckinfailedActivity.class);
              //          .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("eidd", eidresult);
                checkinfail.putExtras(bundle);
                context.startActivity(checkinfail);
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}