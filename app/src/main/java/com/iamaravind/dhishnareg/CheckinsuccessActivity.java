package com.iamaravind.dhishnareg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckinsuccessActivity extends AppCompatActivity {

    String eidresult;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinsuccess);
        Bundle bundle = getIntent().getExtras();
        eidresult = bundle.getString("eidd");
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
}
