package unasat.sr.buysmart.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;

import android.widget.EditText;

import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Fragments.DashboardFragment;
import unasat.sr.buysmart.Fragments.MyDialogFragment;
import unasat.sr.buysmart.R;
import unasat.sr.buysmart.Services.LoggedInService;
import unasat.sr.buysmart.Validation.InputValidation;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername,etPassword;
    private GlobalDAO globalDAO;
    private InputValidation inputValidation;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        initObjects();
        Bundle extras = getIntent().getExtras();
        if (extras!= null){
            etUsername.setText(extras.getString("USERNAME"));
            etPassword.setText(extras.getString("PASSWORD"));
        }
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        globalDAO = new GlobalDAO(this);
        inputValidation = new InputValidation(this);
    }

    public void Submit(View view) {

        if (!inputValidation.isInputEditTextFilledLogin( etUsername, etPassword,  getString(R.string.error_login_message))) {
            return;
        }
        if ( globalDAO.checkIfUserAdmin(etUsername.getText()
                .toString().trim(), etPassword.getText().toString().trim())) {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );
            builder.setIcon(R.drawable.ic_check);
            builder.setTitle(getString(R.string.login_success));
            builder.setMessage( getString(R.string.welcome_message_owner));
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                    String input = getString(R.string.welcome)+ " " + etUsername.getText().toString() + getString(R.string.logged_in) ;
                    Intent serviceIntent = new Intent(getApplicationContext(), LoggedInService.class);
                    serviceIntent.putExtra("inputExtra", input);
                    ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
                    // startService(serviceIntent);


                    //issue hiermee, probeer op de dashboard fragment gebruiker gegevens te tonen,maar lukt niet
                    // bij de menu komt hij wel tevoorschijn, maar bij de fragment doet hij gek
              /*  Bundle bundle1 = new Bundle();
                bundle1.putString("username",etUsername.getText().toString().trim());
                DashboardFragment dashboardFragment = new DashboardFragment();
                dashboardFragment.setArguments(bundle1);*/


//                        Intent dashboardIntent = new Intent(MainActivity.this, DashboardActivity.class);
//                        dashboardIntent.putExtra("username", etUsername.getText().toString().trim());
//                        startActivity(dashboardIntent);
//
//                        emptyInputEditText();
//                        finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if(globalDAO.checkUser(etUsername.getText().toString().trim()
                , etPassword.getText().toString().trim())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this
            );
            builder.setIcon(R.drawable.ic_check);
            builder.setTitle(getString(R.string.login_success));
            builder.setMessage( getString(R.string.welcome_message));
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                    String input = getString(R.string.welcome)+ " " + etUsername.getText().toString() + getString(R.string.logged_in) ;
                    Intent serviceIntent = new Intent(getApplicationContext(), LoggedInService.class);
                    serviceIntent.putExtra("inputExtra", input);
                    ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
                   // startService(serviceIntent);


                    //issue hiermee, probeer op de dashboard fragment gebruiker gegevens te tonen,maar lukt niet
                    // bij de menu komt hij wel tevoorschijn, maar bij de fragment doet hij gek
              /*  Bundle bundle1 = new Bundle();
                bundle1.putString("username",etUsername.getText().toString().trim());
                DashboardFragment dashboardFragment = new DashboardFragment();
                dashboardFragment.setArguments(bundle1);*/


                    Intent dashboardIntent = new Intent(MainActivity.this, DashboardActivity.class);
                    dashboardIntent.putExtra("username", etUsername.getText().toString().trim());
                    startActivity(dashboardIntent);

                    emptyInputEditText();
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else
         {
            Bundle bundle = new Bundle();
            bundle.putString("error",getString(R.string.error_message));
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            myDialogFragment.setArguments(bundle);
            myDialogFragment.show(getSupportFragmentManager(),"MyFragment");
            // Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG);
        }
    }

    public void Register(View view) {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void emptyInputEditText() {
        etUsername.setText(null);
        etPassword.setText(null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!AppState.getSingleInstance().isLoggingOut()) {
            finish();
        } else {
            AppState.getSingleInstance().setLoggingOut(false);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

}