package com.iamaravind.dhishnareg;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    Toolbar toolbar;
    TextView pass,t;
    private ZXingScannerView zXingScannerView;
    String eidresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        t = (TextView)findViewById(R.id.eid);
        Typeface customType = Typeface.createFromAsset(getAssets(),"fonts/BungeeShade-Regular.ttf");
        t.setTypeface(customType);
        Bundle bundle = getIntent().getExtras();
        eidresult = bundle.getString("eidd");
        pass = (TextView)findViewById(R.id.eid);
        //pass.setText(eidresult);
        getEvent(eidresult);
        toolbar = (Toolbar)findViewById(R.id.tolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("@string/app_name");
    }

    public void scan(View view){
        zXingScannerView =new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //  Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        String str = result.getText();
        String type = "checkin";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str, eidresult);
       /* Bundle bundle = new Bundle();
        bundle.putString("result", str);
        valid.putExtras(bundle);*/
    }
    int backButtonCount;
    @Override
    public void onBackPressed()
    {
        backButtonCount++;
        Toast.makeText(ScannerActivity.this,"Want to exit? Press The Home Button!!", Toast.LENGTH_SHORT).show();
    }
        public void getEvent(String eid) {
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor result = db.getdata(eid);
        if (result.getCount() == 0) {
            pass.setText("Not Event with this ID");
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (result.moveToNext()){
            stringBuffer.append(result.getString(0));
        }
        pass.setText(stringBuffer.toString());
    }
}