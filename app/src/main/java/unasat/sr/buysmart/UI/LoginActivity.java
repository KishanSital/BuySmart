/*
package unasat.sr.buysmart.UI;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import unasat.sr.buysmart.Activities.DashboardActivity;
import unasat.sr.buysmart.DatabaseManager.Dao.UserDao;
import unasat.sr.buysmart.DatabaseManager.Dao.UserTypeDao;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;
import unasat.sr.buysmart.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
    }

    public void authenticateUser (View view) {
        Boolean validCred = false;
        User user;
        String loginStringUsername = null;
        String loginStringEmail = null;
        // check how user trying us trying to login.
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(usernameEditText.getText().toString());
        if(matcher.find()){
            loginStringEmail = usernameEditText.getText().toString();
        }
        else {
            loginStringUsername = usernameEditText.getText().toString();
        }

        UserDao userDao = new UserDao(this);
        if (usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter your credentials!",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            user = userDao.getUserByUsername(loginStringUsername, loginStringEmail);
        }

        if (user != null) {
            if (user.getPassword().equals(passwordEditText.getText().toString())) {
                validCred = true;
                UserTypeDao userTypeDao = new UserTypeDao(this);
                UserType userType = userTypeDao.getUserType(null, user.getUserType());
                Intent dashBoardActivityIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                dashBoardActivityIntent.putExtra("userType", userType.getName());
                startActivity(dashBoardActivityIntent);
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Wrong Username/password",Toast.LENGTH_SHORT).show();
        }
    }

    public void registerUser(View view) {
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }
}*/
