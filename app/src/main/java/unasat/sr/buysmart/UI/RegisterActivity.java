package unasat.sr.buysmart.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import unasat.sr.buysmart.DatabaseManager.Dao.UserDao;
import unasat.sr.buysmart.DatabaseManager.Dao.UserTypeDao;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;
import unasat.sr.buysmart.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumber1editText;
    private EditText phoneNumber2editText;
    private EditText nationalityEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumber1editText = findViewById(R.id.phoneNumber1editTextPhone);
        phoneNumber2editText = findViewById(R.id.phoneNumber2editTextPhone2);
        nationalityEditText = findViewById(R.id.nationalityEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rePasswordEditText = findViewById(R.id.reEnterPasswordEditText);
    }

    public void registerUser (View view) {

        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(emailEditText.getText().toString());

        if (firstNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide your first name.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (lastNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide your last name.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (phoneNumber1editText.getText().toString().isEmpty() && phoneNumber2editText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide a least 1 phone number.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (nationalityEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide a nationality.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (usernameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide a username.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide a E-mailadres.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Please provide a password.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (rePasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Re-enter your password.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if ( passwordEditText.getText().toString().isEmpty() != rePasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Passwords need to be the same.",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!matcher.find()) {
            Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
        }

        UserDao userDao = new UserDao(this);
        UserTypeDao userTypeDao = new UserTypeDao(this);
        UserType userType = userTypeDao.getUserType(null,"Customer");
        User user = userDao.getUserByUsername(usernameEditText.getText().toString(), emailEditText.getText().toString());
        if (user != null){
            Toast.makeText(getApplicationContext(),"User already exists!!",Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            User newUser = new User(firstNameEditText.getText().toString()
                                    , lastNameEditText.getText().toString()
                                    , Integer.parseInt(phoneNumber1editText.getText().toString())
                                    , Integer.parseInt(phoneNumber2editText.getText().toString())
                                    , nationalityEditText.getText().toString()
                                    , userType.getId()
                                    , emailEditText.getText().toString()
                                    , usernameEditText.getText().toString()
                                    , passwordEditText.getText().toString()
            );

            userDao.addUser(newUser);
        }
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }
}