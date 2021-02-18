package unasat.sr.buysmart.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import unasat.sr.buysmart.Activities.MainActivity;
import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;
import unasat.sr.buysmart.Validation.InputValidation;

public class SplashScreenActivity extends AppCompatActivity {

    GlobalDAO globalDAO;
    InputValidation inputValidation;
    User user;
    UserType userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObjects();
        startActivity(new Intent(this, MainActivity.class));

        if (!globalDAO.checkUser("owner")) {
            user = new User();
            user.setFirstname("owner");
            user.setLastname("owner");
            user.setEmail("owner@owner.com");
            user.setUsername("owner");
            user.setPhoneNumber1(12345);
            user.setPhoneNumber2(123456);
            user.setNationality("Suriname");
            user.setPassword("owner");
            user.setUserTypeId(1); // 2 = customer, will be filled in automatically when registering. 1 = Admin (one admin will automatically be inserted on startup)
            globalDAO.addUser(user);
            System.out.println(user.toString());
        }

        if (!globalDAO.checkUserType("Admin")){
            userType = new UserType();
            userType.setUserTypeId(1);
            userType.setName("Admin");
            globalDAO.addUserType(userType);
            System.out.println(userType.toString());
        }

        if (!globalDAO.checkUserType("Customer")){
            userType = new UserType();
            userType.setUserTypeId(2);
            userType.setName("Customer");
            globalDAO.addUserType(userType);
            System.out.println(userType.toString());
        }

        finish();
    }

    private void initObjects(){
        globalDAO = new GlobalDAO(this);
        inputValidation = new InputValidation(this);
        user = new User();
        userType = new UserType();
    }
}
