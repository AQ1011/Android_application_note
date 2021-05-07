package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.User;
import com.example.myapplication.database.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignInActivity extends AppCompatActivity {

    UserDao userDao;
    AppDatabase db;
    EditText edUsername;
    EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        db = AppDatabase.getDatabase(this);

        userDao = db.userDao();

        Button btnSignIn;
        FloatingActionButton floatingSignUp;

        edUsername = (EditText) this.findViewById(R.id.editTextTextEmailAddress);
        edPassword = (EditText) this.findViewById(R.id.editTextTextPassword);

        btnSignIn = (Button) this.findViewById(R.id.btn_signin);
        AppCompatActivity activity = this;
        btnSignIn.setOnClickListener(v -> {

            AppDatabase.databaseWriteExecutor.execute(()->{

                check();

            });

        });
        floatingSignUp = (FloatingActionButton) this.findViewById(R.id.floating_signup);
        floatingSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            this.startActivity(intent);
        });

    }

    void check(){
        User u = userDao.loadByEmail(edUsername.getText().toString());
        if(u == null)
        {
            this.runOnUiThread(()->{
                Toast.makeText(getBaseContext(), "Không tồn tài khoản", Toast.LENGTH_SHORT).show();
            });
            return;
        }
        if(u.password.equals(edPassword.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);

            SharedPreferences sharedPref = getSharedPreferences("NOTE_SHARED_PREFERENCE",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username",edUsername.getText().toString());
            editor.apply();

            this.startActivity(intent);
            finish();
            
        } else {

            this.runOnUiThread(()->{

                Toast.makeText(getBaseContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();

            });
        }
    }

}
