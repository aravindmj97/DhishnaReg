package com.iamaravind.dhishnareg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity {

    ImageView imageView ;
    EditText name, passwd;
    Integer clickCountt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText)findViewById(R.id.unamee);
        passwd = (EditText)findViewById(R.id.password);
        configToasty();
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 //Toast.makeText(LoginActivity.this, "You Clicked it", Toast.LENGTH_LONG).show();
                Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(adminIntent);
            }
        });



    }
    public void onLogin(View view)
    {
        String regname = name.getText().toString();
        String regpass = passwd.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, regname, regpass);
    }
    int backButtonCount;
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
          // Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            Toasty.warning(this,"Press the back again to Exit.", Toast.LENGTH_SHORT,true).show();
            backButtonCount++;
        }
    }
    public void configToasty()
    {
        Toasty.Config.getInstance().
        setWarningColor(ContextCompat.getColor(this, R.color.warningColor))
        .apply();
    }
}
