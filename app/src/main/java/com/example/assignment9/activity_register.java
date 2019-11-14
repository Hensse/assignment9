package com.example.assignment9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class activity_register extends AppCompatActivity {
    EditText et_username;
    EditText et_pass;
    Button btn_signup;
    Button btnlogin;
    UserDao userDao;
    List<User> mylist = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = findViewById(R.id.new_nameID);
        et_pass = findViewById(R.id.new_passID);
        btn_signup = findViewById(R.id.registerID);
        btnlogin = findViewById(R.id.backloginID);

        /////////////////////////////
        UserDataBase db = UserDataBase.getInstance(this.getApplication());
        userDao = db.userDao();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                if (username.isEmpty()|| pass.isEmpty()){
                    Toast.makeText(activity_register.this,"Enter all fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    new adduserAsyncTask(username,pass).execute();
                }

            }
        });
        new showuserAsyctask().execute();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }




    public class adduserAsyncTask extends AsyncTask<Void,Void,Boolean>
    {
        private  String username;
        private String pass;
        private User new_user;
        UserDao userDao;
        private Boolean is_new;
        public adduserAsyncTask(String username,String pass) {
            this.username = username;
            this.pass =pass;
            UserDataBase db = UserDataBase.getInstance(activity_register.this.getApplication());
            this.userDao = db.userDao();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            new_user = userDao.getUser(username,pass);
            if (new_user == null) {
                userDao.insertUser(new User(username,pass));
                is_new = true;
            }
            else {
                is_new=false;
            }
            return is_new;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (is_new)
            {
                Toast.makeText(activity_register.this,"User Added",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity_register.this,MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(activity_register.this,"User Already Exists",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public  class showuserAsyctask extends  AsyncTask<Void,Void,Void>
    {
        UserDao userDao;

        public showuserAsyctask() {
            UserDataBase db = UserDataBase.getInstance(activity_register.this.getApplication());
            this.userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            mylist = userDao.getAllUsers();
            for (int i=0;i<mylist.size();i++)
            {
                Log.d("______","id       : " + mylist.get(i).getId());
                Log.d("______","usernaem : " + mylist.get(i).getUsername());
                Log.d("______","password : " + mylist.get(i).getPassword());
            }
            return null;
        }
    }
}
