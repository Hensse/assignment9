package com.example.assignment9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_hello extends AppCompatActivity {

    TextView tv;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        logout = findViewById(R.id.logoutID);
        tv = findViewById(R.id.tvID);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            tv.setText(extras.getString("User info"));
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_hello.this,"Logged out",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
