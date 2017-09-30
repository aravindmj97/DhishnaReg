package com.iamaravind.dhishnareg;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    DataBaseHelper db = new DataBaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        final EditText eid = (EditText)findViewById(R.id.eid);
        final EditText ename = (EditText)findViewById(R.id.ename);
        Button save = (Button)findViewById(R.id.save);
        Button load = (Button)findViewById(R.id.load);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean res = db.insertDb(eid.getText().toString(), ename.getText().toString());
                if (res == true)
                    Toast.makeText(AdminActivity.this,"Data Inserted Successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AdminActivity.this,"Data Not Inserted", Toast.LENGTH_LONG).show();
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = db.getdataa();
                if (result.getCount() == 0) {
                    showMessage("Error!!!", "Data Base is Empty!!");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (result.moveToNext()){
                    stringBuffer.append("EID = "+ result.getString(0)+"\n");
                    stringBuffer.append("EName = "+ result.getString(1)+"\n");
                }
                showMessage("Event Data From DB", stringBuffer.toString());
            }
        });

    }
    public  void showMessage(String title, String message)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
