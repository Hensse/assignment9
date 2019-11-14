package com.example.assignment9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button login;
    Button btn_signup;
    Date dt = new Date();
    String str = "success";
    String str2 = "failed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.nameID);
        password = findViewById(R.id.passwordID);
        login = findViewById(R.id.loginID);
        btn_signup = findViewById(R.id.regID);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                String pass;
                name = username.getText().toString().trim();
                pass = password.getText().toString().trim();

                if (name.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Enter Username and Password",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    checkAsycTask task = new checkAsycTask(MainActivity.this.getApplication(),name,pass);
                    task.execute();
                }


            }



        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),activity_register.class);
                startActivity(i);
            }
        });


    }


    public  class checkAsycTask extends AsyncTask<User,Void,User>
    {
        private UserDao userDao;
        private String name;
        private String pass;
        User user;
        Date date = new Date();
        String stringDate = DateFormat.getDateTimeInstance().format(date);

        public checkAsycTask(Application application,String name, String pass) {
            this.name = name;
            this.pass = pass;
            UserDataBase db = UserDataBase.getInstance(application);
            userDao = db.userDao();
        }

        @Override
        protected User doInBackground(User... users) {

            user = userDao.getUser(name,pass);
            return user;


        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user == null)
            {
                Toast.makeText(MainActivity.this,"User not found!",Toast.LENGTH_SHORT).show();

            }
            else {
                Intent intent = new Intent(MainActivity.this,activity_hello.class);
                intent.putExtra("User info", stringDate);
                startActivity(intent);
            }
        }
    }
}
