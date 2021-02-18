package unasat.sr.buysmart.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.Country;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.MyDialogFragment;
import unasat.sr.buysmart.R;
import unasat.sr.buysmart.Validation.InputValidation;


public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    private static final String COUNTRIES = "https://restcountries.eu/rest/v2/all";
    private EditText email;
    private EditText firstname;
    private EditText lastname;
    private EditText phoneNumber1;
    private EditText phoneNumber2;
    private EditText username;
    private EditText password;
    private EditText rePassword;
    private AutoCompleteTextView nationality;
    private List<Country> countriesList;
    private GlobalDAO globalDAO;
    private InputValidation inputValidation;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.et_email);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        rePassword = findViewById(R.id.et_re_password);
        firstname = findViewById(R.id.et_firstname);
        lastname = findViewById(R.id.et_lastname);
        phoneNumber1 = findViewById(R.id.et_phoneNumber1);
        phoneNumber2 = findViewById(R.id.et_phoneNumber2);
        nationality = findViewById(R.id.at_nationality);

        getCountryData(COUNTRIES);
        nationality.setOnItemClickListener(this);
        nationality.setOnItemSelectedListener(this);
        nationality.setOnFocusChangeListener(this::onFocusChange);

        initObjects();
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        globalDAO = new GlobalDAO(this);
        inputValidation = new InputValidation(this);
        user = new User();

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
                                new ArrayAdapter<Country>(RegistrationActivity.this, android.R.layout.simple_list_item_1, countriesList);
                        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        nationality.setAdapter(adapter);
                        System.out.println(countriesList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Bundle bundle = new Bundle();
                bundle.putString("error",getString(R.string.error_internet));
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.setArguments(bundle);
                myDialogFragment.show(getSupportFragmentManager(),"MyFragment");
                //  Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
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
            System.out.println(getString(R.string.json_error));
        }
        return countryList;
    }



    public void Submit(View view) {

        if (!inputValidation.isInputEditTextFilled(firstname, getString(R.string.error_message_first_name))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(lastname, getString(R.string.error_message_last_name))) {
            return;
        }

        if (!inputValidation.isInputEditTextEmail(email, getString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(username, getString(R.string.error_message_name))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(phoneNumber1, getString(R.string.error_message_phone1))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(nationality, getString(R.string.error_message_nationality))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(password, getString(R.string.error_message_password))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(rePassword, getString(R.string.error_message_re_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(password, rePassword,
                getString(R.string.error_password_match))) {
            return;
        }

        if (!globalDAO.checkUser(username.getText().toString().trim())) {
            user.setFirstname(firstname.getText().toString().trim());
            user.setLastname(lastname.getText().toString().trim());
            user.setEmail(email.getText().toString().trim());
            user.setUsername(username.getText().toString().trim());
            user.setPhoneNumber1(Integer.parseInt(phoneNumber1.getText().toString().trim()));
            user.setPhoneNumber2(Integer.parseInt( phoneNumber2.getText().toString().trim()));
            user.setNationality(nationality.getText().toString().trim());
            user.setPassword(password.getText().toString());
            user.setUserTypeId(2); // 2 = customer, will be filled in automatically when registering. 1 = Admin (one admin will automatically be inserted on startup)
            globalDAO.addUser(user);
            System.out.println(user.toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    RegistrationActivity.this
            );
            builder.setIcon(R.drawable.ic_check);
            builder.setTitle(getString(R.string.register_success));
            builder.setMessage(getString(R.string.registration_message));
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                    Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                    loginIntent.putExtra("USERNAME", username.getText().toString().trim());
                    loginIntent.putExtra("PASSWORD", password.getText().toString().trim());
                    emptyInputEditText();
                    startActivity(loginIntent);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else {
            Bundle bundle = new Bundle();
            bundle.putString("error",getString(R.string.error_user_already_exists));
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.setArguments(bundle);
            myDialogFragment.show(getSupportFragmentManager(),"MyFragment");
        }
    }

    private void emptyInputEditText() {
        username.setText(null);
        email.setText(null);
        firstname.setText(null);
        lastname.setText(null);
        password.setText(null);
        rePassword.setText(null);
        nationality.setText(null);
        phoneNumber1.setText(null);
        phoneNumber2.setText(null);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long row) {
        Country  country = (Country) nationality.getAdapter().getItem(position);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long row) {
        Country  country = (Country) nationality.getAdapter().getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}