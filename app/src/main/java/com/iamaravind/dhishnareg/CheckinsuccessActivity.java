package com.iamaravind.dhishnareg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class CheckinsuccessActivity extends AppCompatActivity {

    String eidresult, pid;
    Toolbar toolbar;
    TextView getNametext, getCollegeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinsuccess);
        Bundle bundle = getIntent().getExtras();
        eidresult = bundle.getString("eidd");
        pid = bundle.getString("pid");
        TextView pass = (TextView)findViewById(R.id.success);
        pass.setText("Participant Successfully Checked-In to Event - "+eidresult);
        Button back = (Button)findViewById(R.id.back2);
        toolbar = (Toolbar)findViewById(R.id.tolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("@string/app_name");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back2scan = new Intent(CheckinsuccessActivity.this, ScannerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("eidd", eidresult);
                back2scan.putExtras(bundle);
                startActivity(back2scan);
                finish();
            }
        });
        String type = "getdetails";
        PdetailGetter pdet = new PdetailGetter((this));
        pdet.execute(type);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int res_id = item.getItemId();
        if (res_id==R.id.action_exit){
            //Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_SHORT).show();
            Intent logout = new Intent(CheckinsuccessActivity.this, LoginActivity.class);
            startActivity(logout);
            finish();
        }
        return true;
    }

        public class PdetailGetter extends AsyncTask<String, Void, String> {

            Context context;
            String type,n;
            PdetailGetter(Context ctx){
                context = ctx;
            }
            @Override
            protected String doInBackground(String... params) {
                String name_url = "http://u1701374.nettech.firm.in/successdetails.php";
                type = params[0];

                DataBaseHelper db = new DataBaseHelper(context);
                if (type.equals("getdetails")){
                    try {
                        URL url = new URL(name_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput((true));
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("pid_name","UTF-8")+"="+URLEncoder.encode(pid,"UTF-8");
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
                if (type.equals("getdetails"))
                {
                    String[] split = result.split(",");
                    getNametext = (TextView)findViewById(R.id.chs_name);
                    getCollegeText = (TextView)findViewById(R.id.chs_college);
                    getNametext.setText(split[0]);
                    getCollegeText.setText(split[1]);
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }
}
