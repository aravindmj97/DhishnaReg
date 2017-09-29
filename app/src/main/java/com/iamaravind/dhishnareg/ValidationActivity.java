package com.iamaravind.dhishnareg;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ValidationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        Bundle bundle = getIntent().getExtras();
        String result = bundle.getString("result");
        TextView pass = (TextView)findViewById(R.id.result_pass);
        pass.setText(result);

    }
}