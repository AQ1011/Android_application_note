package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.User;
import com.example.myapplication.database.UserDao;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AppDatabase db = AppDatabase.getDatabase(this);

        UserDao userDao = db.userDao();

        Button btnSignUp = (Button) this.findViewById(R.id.btn_signup);
        Button btnSignIn = (Button) this.findViewById(R.id.btn_signin);
        EditText edUsername = (EditText) this.findViewById(R.id.editTextSignUpEmailAddress);
        EditText edPassword = (EditText) this.findViewById(R.id.editTextSignUpPassword);
        EditText edConfirmPassword = (EditText) this.findViewById(R.id.editTextSignUpConfirmPassword);

        btnSignUp.setOnClickListener(v -> {

                AppDatabase.databaseWriteExecutor.execute(() -> {

                    if(userDao.loadByEmail(edUsername.getText().toString()) != null)
                    {
                        this.runOnUiThread(()->{
                            Toast.makeText(this, "Email đã đăng ký", Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }

                    String password = edPassword.getText().toString().trim();
                    String confirm = edConfirmPassword.getText().toString().trim();

                    if( confirm.equals(password)) {

                        User u = new User();
                        u.email = edUsername.getText().toString();
                        u.password = edPassword.getText().toString();

                        userDao.insert(u);

                        edUsername.setText("");
                        edConfirmPassword.setText("");
                        edPassword.setText("");
                        this.runOnUiThread(()->{

                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                        });

                    } else {

                        this.runOnUiThread(()->{

                            Toast.makeText(this, "Mật khẩu không giống", Toast.LENGTH_SHORT).show();

                        });

                    }

                });

        });
        btnSignIn.setOnClickListener(v->{
            finish();
        });
    }

}
