package unasat.sr.buysmart.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.Activities.MainActivity;
import unasat.sr.buysmart.DatabaseManager.Dao.GlobalDAO;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.ProductType;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;
import unasat.sr.buysmart.R;
import unasat.sr.buysmart.Validation.InputValidation;

public class SplashScreenActivity extends AppCompatActivity {


    private GlobalDAO globalDAO;
    private InputValidation inputValidation;
    private User user;
    private UserType userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObjects();

       /* if(!globalDAO.checkUser("owner") || !globalDAO.checkUserType("Admin") ||!globalDAO.checkUserType("Customer")){

        }*/

        startActivity(new Intent(this, MainActivity.class));

       /* if (!globalDAO.checkUser("owner")) {
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
*/
        new insertOwnerTask().execute();

        finish();
    }
    private void initObjects(){
        globalDAO = new GlobalDAO(this);
        inputValidation = new InputValidation(this);
        user = new User();
        userType = new UserType();

    }



    private class insertOwnerTask extends AsyncTask<Integer, Void, Boolean> {

        private   GlobalDAO globalDAO;
        private   InputValidation inputValidation;
        private   User user;
        private   UserType userType;
        private ProductType productType;

        protected void onPreExecute() {
            globalDAO = new GlobalDAO(SplashScreenActivity.this);
            user = new User();
            userType = new UserType();
            productType = new ProductType();
        }

        protected Boolean doInBackground(Integer... credentials) {

            try{

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


                if (!globalDAO.checkUser("Kishan")) {
                    user = new User();
                    user.setFirstname("Kishan");
                    user.setLastname("Sital");
                    user.setEmail("Kishansital1@gmail.com");
                    user.setUsername("Kishan");
                    user.setPhoneNumber1(12345);
                    user.setPhoneNumber2(123456);
                    user.setNationality("Suriname");
                    user.setPassword("1234");
                    user.setUserTypeId(2); // 2 = customer, will be filled in automatically when registering. 1 = Admin (one admin will automatically be inserted on startup)
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

                if (!globalDAO.checkProductType("Phones")){
                    System.out.println("Inserting Phones product type");
                    productType = new ProductType();
                    productType.setName("Phones");
                    globalDAO.addProductType(productType);
                    System.out.println(productType.toString());
                }

                return true;
            }catch (SQLiteException e){
                return false;
            }
        }
        protected void onPostExecute(Boolean success) {
            if (!success){
                Toast toast = Toast.makeText(SplashScreenActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}