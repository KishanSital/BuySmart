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

              //  insertProduct();
                return true;

            }catch (SQLiteException e){
                return false;
            }


           // return true;
        }

        protected void onPostExecute(Boolean success) {
            if (!success){
                Toast toast = Toast.makeText(SplashScreenActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

   /*     private void insertProduct() {
            ProductType productType = globalDAO.findProductTypeByName("Phones");

            Product product1 = new Product("Iphone 12 Pro Max", 20000, productType.getProductTypeId());
            Product product2 = new Product("Samsung A30", 4000, productType.getProductTypeId());
            Product product3 = new Product("CAT S41", 6000, productType.getProductTypeId());
            Product product4 = new Product("Nokia Lumia 1520", 900, productType.getProductTypeId());
          *//*  Product product5 = new Product("Product5", 100, productType.getProductTypeId());
            Product product6 = new Product("Product6", 100, productType.getProductTypeId());
            Product product7 = new Product("Product7", 100, productType.getProductTypeId());
            Product product8 = new Product("Product8", 100, productType.getProductTypeId());
            Product product9 = new Product("Product9", 100, productType.getProductTypeId());
            Product product10 = new Product("Product10", 100, productType.getProductTypeId());
            Product product11 = new Product("Product11", 100, productType.getProductTypeId());
            Product product12 = new Product("Product12", 100, productType.getProductTypeId());
            Product product13 = new Product("Product13", 100, productType.getProductTypeId());
            Product product14 = new Product("Product14", 100, productType.getProductTypeId());
            Product product15 = new Product("Product15", 100, productType.getProductTypeId());
            Product product16 = new Product("Product16", 100, productType.getProductTypeId());
            Product product17 = new Product("Product17", 100, productType.getProductTypeId());
            Product product18 = new Product("Product18", 100, productType.getProductTypeId());
            Product product19 = new Product("Product19", 100, productType.getProductTypeId());*//*
            List<Product> products = new ArrayList<>();
            products.add(product1);
            products.add(product2);
            products.add(product3);
            products.add(product4);
         *//*   products.add(product5);
            products.add(product6);
            products.add(product7);
            products.add(product8);
            products.add(product9);
            products.add(product10);
            products.add(product11);
            products.add(product12);
            products.add(product13);
            products.add(product14);
            products.add(product15);
            products.add(product16);
            products.add(product17);
            products.add(product18);
            products.add(product19);*//*

            for(int i = 0; i <  products.size();i++) {
                if(!globalDAO.checkProduct(products.get(i).getName())) {
                    globalDAO.addProduct(products.get(i));
                }
            }
        }*/
    }

}