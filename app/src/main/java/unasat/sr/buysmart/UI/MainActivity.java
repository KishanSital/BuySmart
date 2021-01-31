package unasat.sr.buysmart.UI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import unasat.sr.buysmart.DatabaseManager.Dao.UserDao;
import unasat.sr.buysmart.DatabaseManager.Dao.UserTypeDao;
import unasat.sr.buysmart.DatabaseManager.Tables.*;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;
import unasat.sr.buysmart.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // first insert ref data.
        UserTypeDao userTypeDao = new UserTypeDao(this);
        userTypeDao.insertRefUserTypes();

        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }

}