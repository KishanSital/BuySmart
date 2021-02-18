/*
package unasat.sr.buysmart.UI;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import unasat.sr.buysmart.DatabaseManager.Dao.UserDao;
import unasat.sr.buysmart.DatabaseManager.Dao.UserTypeDao;
import unasat.sr.buysmart.Entities.Country;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;
import unasat.sr.buysmart.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String COUNTRIES = "https://restcountries.eu/rest/v2/all";
    private static final String EMAIL_PATTERN = "[a-z-]+@[a-z]+\\.+[a-z]+";
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumber1editText;
    private EditText phoneNumber2editText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText rePasswordEditText;
    private AutoCompleteTextView autoCompleteTextView;
    private List<Country> countriesList;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumber1editText = findViewById(R.id.phoneNumber1editTextPhone);
        phoneNumber2editText = findViewById(R.id.phoneNumber2editTextPhone2);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rePasswordEditText = findViewById(R.id.reEnterPasswordEditText);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        init();
    }

    public void registerUser(View view) {
        if (fieldValidation()) {
            UserDao userDao = new UserDao(this);
            UserTypeDao userTypeDao = new UserTypeDao(this);
            UserType userType = userTypeDao.getUserType(null, "Customer");
            User user = userDao.getUserByUsername(usernameEditText.getText().toString(), emailEditText.getText().toString());
            if (user != null) {
                Toast.makeText(getApplicationContext(), "User already exists!!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                User newUser = new User(firstNameEditText.getText().toString()
                        , lastNameEditText.getText().toString()
                        , Integer.parseInt(phoneNumber1editText.getText().toString())
                        , Integer.parseInt(phoneNumber2editText.getText().toString())
                        , autoCompleteTextView.getText().toString()
                        , userType.getId()
                        , emailEditText.getText().toString()
                        , usernameEditText.getText().toString()
                        , passwordEditText.getText().toString()
                );
                userDao.addUser(newUser);
                Toast.makeText(getApplicationContext(), "User successfully registered ", Toast.LENGTH_SHORT).show();
                if (fieldValidation() && (userDao.addUser(newUser)) == true) {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        }
    }

    private void getCountryData(String countries) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, countries,
                new Response.Listener<String>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(String response) {
                        countriesList = mapJsonToCountryObject(response);
                        //Creating adapter for autoCompleteTextView
                        ArrayAdapter<Country> adapter =
                                new ArrayAdapter<Country>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, countriesList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        autoCompleteTextView.setAdapter(adapter);
                        System.out.println(countriesList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }

    private List<Country> mapJsonToCountryObject(String jsonArray) {
        ObjectMapper mapper = new ObjectMapper();
        List<Country> countryList = new ArrayList<>();
        List<Map<String, ?>> countryArray = null;
        Country country = null;
        try {
            countryArray = mapper.readValue(jsonArray, List.class);
            for (Map<String, ?> map : countryArray) {
                country = new Country((String) map.get("name"));
                countryList.add(country);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Er is wat fout gegaan bij het parsen van de json data");
        }
        return countryList;
    }

    public boolean fieldValidation() {
        if (firstNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide your first name.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (lastNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide your last name.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phoneNumber1editText.getText().toString().isEmpty() && phoneNumber2editText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide a least 1 phone number.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (autoCompleteTextView.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide a nationality.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide a E-mailadres.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (usernameEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide a username.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide a password.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (rePasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Re-enter your password.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (passwordEditText.getText().toString().isEmpty() != rePasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Passwords need to be the same.", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }


    public void emailValidation() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                email = emailEditText.getText().toString().trim();
                if (email.matches(EMAIL_PATTERN) && s.length() > 0) {
                    Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    public void init() {
        getCountryData(COUNTRIES);
        emailValidation();
    }
}*/
