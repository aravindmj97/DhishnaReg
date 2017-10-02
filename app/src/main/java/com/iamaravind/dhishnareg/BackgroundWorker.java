package com.iamaravind.dhishnareg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

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

import es.dmoral.toasty.Toasty;


public class BackgroundWorker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    String type, eidresult,n, fromqr;
    BackgroundWorker(Context ctx){
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String name = params[1];
        String password = params[2];
         eidresult = params[2];
        fromqr = params[1];
        String event = params[1];
         n = params[2];
        type = params[0];
        String login_url = "http://u1701374.nettech.firm.in/login.php";
        String checkin_url = "http://u1701374.nettech.firm.in/checkin.php";
        DataBaseHelper db = new DataBaseHelper(context);
        if (type.equals("login")){
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput((true));
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("app_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("app_pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result ="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
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
        else if (type.equals("checkin")){
            try {
                URL url = new URL(checkin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput((true));
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("parti_id","UTF-8")+"="+URLEncoder.encode(fromqr,"UTF-8")+"&"
                        +URLEncoder.encode("org_id","UTF-8")+"="+URLEncoder.encode(eidresult,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result ="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
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
     //alertDialog = new AlertDialog.Builder(context).create();
     //alertDialog.setTitle("Login Status");

    }

    @Override
    protected void onPostExecute(String result) {
       // alertDialog.setMessage(result);
       // alertDialog.show();
        String error = "Wrong Credentials";
        if (result.equals("NO")&&(type.equals("login")))
        {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
            alertDialog.setMessage(error);
            alertDialog.show();

        }
        else if(type.equals("login")){
            Intent eid = new Intent(context,ScannerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putString("eidd", result);
            eid.putExtras(bundle);
            context.startActivity(eid);
        }
        else if(type.equals("checkin")){
            if (result.equals("1")){
                Intent checkin_succ = new Intent(context,CheckinsuccessActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("eidd", eidresult);
                bundle.putString("pid", fromqr);
                checkin_succ.putExtras(bundle);
                context.startActivity(checkin_succ);
            }
            else if (result.equals("0")){
                Intent checkin_fail = new Intent(context,CheckinfailedActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("eidd", eidresult);
                bundle.putString("pid", fromqr);
                checkin_fail.putExtras(bundle);
                context.startActivity(checkin_fail);
            }
            else {
                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                configToasty();
                Toasty.error(context,result, Toast.LENGTH_LONG,true).show();
                onResume();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void onResume(){
        Intent back2scan = new Intent(context,ScannerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("eidd", eidresult);
        back2scan.putExtras(bundle);
        context.startActivity(back2scan);

    }
    public void configToasty()
    {
        Toasty.Config.getInstance().
                setErrorColor(ContextCompat.getColor(context, R.color.errorColor))
                .apply();
    }
}
