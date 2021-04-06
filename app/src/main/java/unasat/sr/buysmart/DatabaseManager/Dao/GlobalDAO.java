
package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unasat.sr.buysmart.Entities.Order;
import unasat.sr.buysmart.Entities.Product;
import unasat.sr.buysmart.Entities.Product2;
import unasat.sr.buysmart.Entities.ProductType;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;


public class GlobalDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "buysmart8.db";
    private static final int DATABASE_VERSION = 8;
    // constants user table
    public static final String USER_TABLE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";
    public static final String USER_EMAIL = "email";
    public static final String USER_USERNAME = "username";
    public static final String USER_PHONE_NUMBER1 = "phone_number1";
    public static final String USER_PHONE_NUMBER2 = "phone_number2";
    public static final String USER_NATIONALITY = "nationality";
    public static final String USER_PASSWORD = "password";
    public static final String USER_TYPE_ID = "user_type_id";
    // constants user_types table
    public static final String USER_TYPES_TABLE = "user_types";
    public static final String USER_TYPES_ID = "user_types_id";
    public static final String USER_TYPES_NAME = "name";
    // constants product_types table
    public static final String PRODUCT_TYPES_TABLE = "product_types";
    public static final String PRODUCT_TYPES_ID = "product_types_id";
    public static final String PRODUCT_TYPES_NAME = "name";
  /*  // constants product table
    public static final String PRODUCT_TABLE = "product";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";*/
  public static final String PRODUCT_ID = "product_id";

    // constant orders table
    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "order_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String ORDERED_DATE = "ordered_date";
    // constant product2 table
    public static final String PRODUCT2_TABLE = "product2";
    public static final String PRODUCT2_ID = "product_id2";
    public static final String PRODUCT2_NAME = "name2";
    public static final String PRODUCT2_PRICE = "price2";
    public static final String PRODUCT2_TYPES_ID = "product_types_id2";
    public static final String PRODUCT2_IMAGE = "product_image";

    public static final int ADMIN = 1;

    //product2 table create string

    private static final String SQL_PRODUCT2_QUERY = String.format("create table %s" +
            " (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s STRING NOT NULL, %s INTEGER, %s INTEGER, %s BLOB)",PRODUCT2_TABLE, PRODUCT2_ID, PRODUCT2_NAME, PRODUCT2_PRICE, PRODUCT2_TYPES_ID, PRODUCT2_IMAGE);

    // user table create string
    private static final String SQL_USER_TABLE_QUERY = String.format("create table %s " +
            "(%s INTEGER PRIMARY KEY , %s STRING NOT NULL UNIQUE , %s STRING NOT NULL UNIQUE,  %s STRING NOT NULL, " +
            "%s STRING NOT NULL, %s STRING NOT NULL, %s INTEGER NOT NULL , %s INTEGER, %s STRING NOT NULL , %s INTEGER)",
            USER_TABLE, USER_ID, USER_EMAIL, USER_USERNAME, USER_PASSWORD,  USER_FIRSTNAME, USER_LASTNAME,
            USER_PHONE_NUMBER1, USER_PHONE_NUMBER2 ,USER_NATIONALITY, USER_TYPE_ID) ;
    // user_types table string
    private static final String SQL_USER_TYPE_QUERY = String.format("create table %s" +
            " (%s INTEGER PRIMARY KEY, %s STRING NOT NULL)",USER_TYPES_TABLE, USER_TYPES_ID, USER_TYPES_NAME);
    // product_types table create string
    private static final String SQL_PRODUCT_TYPES_QUERY = String.format("create table %s" +
            " (%s INTEGER PRIMARY KEY, %s STRING NOT NULL)",PRODUCT_TYPES_TABLE, PRODUCT_TYPES_ID, PRODUCT_TYPES_NAME);
    // product table create string
//    private static final String SQL_PRODUCT_QUERY = String.format("create table %s" +
//            " (%s INTEGER PRIMARY KEY, %s STRING NOT NULL, %s INTEGER, %s INTEGER)",PRODUCT_TABLE, PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_TYPES_ID);
    // order table create string
    private static final String SQL_ORDER_QUERY = String.format("create table %s" +
            " (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER, %s STRING, %s STRING)",ORDER_TABLE, ORDER_ID, PRODUCT_ID, CUSTOMER_ID, ORDERED_DATE, CUSTOMER_NAME);

    public GlobalDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<User> getUsersList(){
        String sql = "select * from " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> users = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                users.add(new User(username,password));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USER_TABLE, USER_USERNAME + " = ? ", new String[]
                {String.valueOf(username)});
        db.delete(ORDER_TABLE, CUSTOMER_NAME + " = ?", new String[]
                {String.valueOf(username)});
    }



    public void deleteOrder (int orderId ){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM "+ ORDER_TABLE +" WHERE " +ORDER_ID+" = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,orderId);
        statement.execute();
        db.close();
    }

    public void updateUsers(User userClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME,userClass.getUsername());
        contentValues.put(USER_PASSWORD,userClass.getPassword());
        contentValues.put(USER_EMAIL, userClass.getEmail());
                contentValues.put(USER_FIRSTNAME, userClass.getFirstname());
        contentValues.put(USER_LASTNAME, userClass.getLastname());
                contentValues.put(USER_PHONE_NUMBER1, userClass.getPhoneNumber1());
        contentValues.put(USER_PHONE_NUMBER2, userClass.getPhoneNumber2());
                contentValues.put(USER_NATIONALITY, userClass.getNationality());
        db.update(USER_TABLE,contentValues,USER_USERNAME + " = ?" ,
                new String[]{userClass.getUsername()});
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FIRSTNAME, user.getFirstname());
        values.put(USER_LASTNAME, user.getLastname());
        values.put(USER_PHONE_NUMBER1, user.getPhoneNumber1());
        values.put(USER_PHONE_NUMBER2, user.getPhoneNumber2());
        values.put(USER_NATIONALITY, user.getNationality());
        values.put(USER_TYPE_ID, user.getUserTypeId());
        // updating row
        db.update(USER_TABLE, values, USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USER_TABLE_QUERY);
        db.execSQL(SQL_USER_TYPE_QUERY);
        db.execSQL(SQL_PRODUCT_TYPES_QUERY);
        //db.execSQL(SQL_PRODUCT_QUERY);
        db.execSQL(SQL_ORDER_QUERY);
        db.execSQL(SQL_PRODUCT2_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_USER_TABLE_QUERY);
        db.execSQL(SQL_USER_TYPE_QUERY);
        db.execSQL(SQL_PRODUCT_TYPES_QUERY);
        //db.execSQL(SQL_PRODUCT_QUERY);
        db.execSQL(SQL_ORDER_QUERY);
        db.execSQL(SQL_PRODUCT2_QUERY);

    }

    /**
     * This method is to create user record
     * parameter is user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(USER_ID, user.getId());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_USERNAME, user.getUsername());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FIRSTNAME, user.getFirstname());
        values.put(USER_LASTNAME, user.getLastname());
        values.put(USER_PHONE_NUMBER1, user.getPhoneNumber1());
        values.put(USER_PHONE_NUMBER2, user.getPhoneNumber2());
        values.put(USER_NATIONALITY, user.getNationality());
        values.put(USER_TYPE_ID, user.getUserTypeId());
        // Inserting Singular Row
        db.insert(USER_TABLE, null, values);
        db.close();
    }

    public void addUserType(UserType userType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
         values.put(USER_TYPES_ID, userType.getUserTypeId());
        values.put(USER_TYPES_NAME, userType.getName());
        // Inserting Singular Row
        db.insert(USER_TYPES_TABLE, null, values);
        db.close();
    }

    public void addProductType(ProductType productType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_TYPES_ID, productType.getProductTypeId());
        values.put(PRODUCT_TYPES_NAME, productType.getName());
        // Inserting Singular Row
        db.insert(PRODUCT_TYPES_TABLE, null, values);
        db.close();
    }

/*    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_TYPES_ID, product.getProductTypeId());
        // Inserting Singular Row
        db.insert(PRODUCT_TABLE, null, values);
        db.close();
    }*/

    public void insertProduct2(String name, int price, byte[] image, int productTypeId){
        SQLiteDatabase database = getWritableDatabase();
        String sql = " INSERT INTO "+ PRODUCT2_TABLE +" VALUES (NULL, ?, ?, ?, ?) ";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        //int id, String name, int price, int productTypeId, byte[] image

        statement.bindString(1, name);
        statement.bindLong(2, price);
        statement.bindLong(3, productTypeId);
        statement.bindBlob(4, image);
        statement.executeInsert();
    }
    public void insertProduct2(Product2 product2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // values.put(USER_ID, user.getId());
        values.put(PRODUCT2_NAME, product2.getName());
        values.put(PRODUCT2_PRICE, product2.getPrice());
        values.put(PRODUCT2_IMAGE, product2.getImage());
        values.put(PRODUCT2_TYPES_ID, product2.getProductTypeId());
        // Inserting Singular Row
        db.insert(PRODUCT2_TABLE, null, values);
        db.close();
    }


    public void updateProduct2(String name, int price, byte[] image, int id, int productTypeId) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE "+ PRODUCT2_TABLE +" SET "+PRODUCT2_NAME+" = ?, "+PRODUCT2_PRICE+" = ?, "+PRODUCT2_IMAGE+" = ?, "+PRODUCT2_TYPES_ID+" = ? WHERE "+PRODUCT2_ID+" = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindLong(2, price);
        statement.bindBlob(3, image);
        statement.bindLong(4, productTypeId);
        statement.bindLong(5, id);
        statement.execute();
        database.close();
    }

    public  void deleteProduct2(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM "+ PRODUCT2_TABLE +" WHERE "+PRODUCT2_ID+" = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1,id);
        statement.execute();

        String sql2 = "DELETE FROM "+ ORDER_TABLE +" WHERE " +PRODUCT_ID+" = ?";
        SQLiteStatement statement2 = database.compileStatement(sql2);
        statement.clearBindings();
        statement.bindLong(1,id);
        statement2.execute();

        database.close();
    }


    public Cursor getProduct2(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_ID, order.getCustomerId());
        values.put(CUSTOMER_NAME, order.getCustomerName());
        values.put(ORDERED_DATE, order.getOrderedDate());
        values.put(PRODUCT_ID, order.getProductId());
        // Inserting Singular Row
        db.insert(ORDER_TABLE, null, values);
        db.close();
    }



    public UserType findByUserType(String userType) {
        UserType type = new UserType();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", USER_TYPES_NAME);
        String[] whereArgs = {userType};
        cursor = db.query(USER_TYPES_TABLE, null, whereClause, whereArgs ,null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            type.setName(cursor.getString(cursor.getColumnIndex(USER_TYPES_NAME)));
        }
        return type;
    }

    public ProductType findProductTypeByName(String productTypeName) {
        ProductType productType = new ProductType();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", PRODUCT_TYPES_NAME);
        String[] whereArgs = {productTypeName};
        cursor = db.query(PRODUCT_TYPES_TABLE, null, whereClause, whereArgs ,null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            productType.setProductTypeId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_TYPES_ID))));
            productType.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_TYPES_NAME)));
        }
        return productType;
    }

/*    public Product findProductById(String productId) {
        Product product= new Product();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", PRODUCT_ID);
        String[] whereArgs = {productId.trim()};
        cursor = db.query(PRODUCT_TABLE, null, whereClause, whereArgs ,null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            product.setProductTypeId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_TYPES_ID))));
            product.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
            product.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_PRICE))));
            product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT_ID))));
        }
        return product;
    }*/

    public Product2 findProduct2ById(String productId) {
        Product2 product= new Product2();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", PRODUCT2_ID);
        String[] whereArgs = {productId.trim()};
        cursor = db.query(PRODUCT2_TABLE, null, whereClause, whereArgs ,null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            product.setProductTypeId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT2_TYPES_ID))));
            product.setName(cursor.getString(cursor.getColumnIndex(PRODUCT2_NAME)));
            product.setPrice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT2_PRICE))));
            product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(PRODUCT2_ID))));
            product.setImage(cursor.getBlob(cursor.getColumnIndex(PRODUCT2_IMAGE)));
        }
        return product;
    }



    /**
     * This method is to fetch all user and return the list of user records
     * returns a list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                USER_ID,
                USER_EMAIL,
                USER_USERNAME,
                USER_PASSWORD,
                USER_FIRSTNAME,
                USER_LASTNAME,
                USER_PHONE_NUMBER1,
                USER_PHONE_NUMBER2,
                USER_NATIONALITY,
                USER_TYPE_ID,
        };
        // sorting orders
        String sortOrder =
                USER_USERNAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT ID,USER_USERNAME,... FROM USER_TABLE ORDER BY USER_USERNAME;
         */
        Cursor cursor = db.query(USER_TABLE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(USER_ID))));
                user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(USER_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(USER_FIRSTNAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(USER_LASTNAME)));
                user.setPhoneNumber1(cursor.getInt(cursor.getColumnIndex(USER_PHONE_NUMBER1)));
                user.setPhoneNumber2(cursor.getInt(cursor.getColumnIndex(USER_PHONE_NUMBER2)));
                user.setNationality(cursor.getString(cursor.getColumnIndex(USER_NATIONALITY)));
                user.setUserTypeId(cursor.getInt(cursor.getColumnIndex(USER_TYPE_ID)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }

  /*  public List<Product> getAllProduct() {
        // array of columns to fetch
        String[] columns = {
                PRODUCT_ID,
                PRODUCT_NAME,
                PRODUCT_PRICE,
                PRODUCT_TYPES_ID
        };
        // sorting orders
        String sortOrder =
                PRODUCT_NAME + " ASC";
        List<Product>  productList= new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        *//**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT ID,USER_USERNAME,... FROM USER_TABLE ORDER BY USER_USERNAME;
         *//*
        Cursor cursor = db.query(PRODUCT_TABLE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
                product.setPrice(cursor.getInt(cursor.getColumnIndex(PRODUCT_PRICE)));
                product.setProductTypeId(cursor.getInt(cursor.getColumnIndex(PRODUCT_TYPES_ID)));
                // Adding user record to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return productList;
    }*/

    public User findByUsername(String username) {
        User user = new User();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", USER_USERNAME);
        String[] whereArgs = {username};
        cursor = db.query(USER_TABLE, null, whereClause, whereArgs ,null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            user.setUserId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(USER_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(USER_PASSWORD)));
            user.setFirstname(cursor.getString(cursor.getColumnIndex(USER_FIRSTNAME)));
            user.setLastname(cursor.getString(cursor.getColumnIndex(USER_LASTNAME)));
            user.setPhoneNumber1(cursor.getInt(cursor.getColumnIndex(USER_PHONE_NUMBER1)));
            user.setPhoneNumber2(cursor.getInt(cursor.getColumnIndex(USER_PHONE_NUMBER2)));
            user.setNationality(cursor.getString(cursor.getColumnIndex(USER_NATIONALITY)));
        }
        return user;
    }


    public List<Order> findAllOrdersByUsername(String username) {


      //  User user = findByUsername(username);
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s where %s ='%s'", ORDER_TABLE, CUSTOMER_NAME, username);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            orders.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)));
        }
        db.close();
        return orders;
    }


    public List<Order> findAllOrdersByProductId(int prodId) {
        //  User user = findByUsername(username);
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s where %s ='%s'", ORDER_TABLE, PRODUCT_ID, prodId);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            orders.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)));
        }
        db.close();
        return orders;
    }


    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s ", ORDER_TABLE, CUSTOMER_NAME);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            orders.add(new Order(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)));
        }
        db.close();
        return orders;
    }




    public List<User> findAllUserRecords(String table) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("select * from %s", table);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            users.add(new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8), cursor.getInt(9)));
        }
        db.close();
        return users;
    }

    /**
     * This method to update user record
     *
     * parameter user
     */


    /**
     * This method is to delete user record
     *
     * parameter user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(USER_TABLE, USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * parameter username
     * return true/false
     */
    public boolean checkUser(String username) {
        // array of columns to fetch
        String[] columns = {
                USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = USER_USERNAME + " = ?";
        // selection argument
        String[] selectionArgs = {username};
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT ID FROM USER_TABLE WHERE USER_USERNAME = username;
         */
        Cursor cursor = db.query(USER_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUserType(String userType) {
        // array of columns to fetch
        String[] columns = {
                USER_TYPES_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = USER_TYPES_NAME + " = ?";
        // selection argument
        String[] selectionArgs = {userType};
        // query user table with condition
        /**

         */
        Cursor cursor = db.query(USER_TYPES_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String username, String password) {
        // array of columns to fetch
        String[] columns = {
                USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = USER_USERNAME + " = ?" + " AND " + USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT ID FROM USER_TABLE WHERE USER_USERNAME = username AND USER_PASSWORD = password;
         */
        Cursor cursor = db.query(USER_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkIfUserAdmin(String username, String password) {
        // array of columns to fetch
        String[] columns = {
                USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = USER_USERNAME + " = ?" + " AND " + USER_PASSWORD + " = ?" + " AND " + USER_TYPE_ID + " = "+ ADMIN;
        // selection arguments
        String[] selectionArgs = {username, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT ID FROM USER_TABLE WHERE USER_USERNAME = username AND USER_PASSWORD = password;
         */
        Cursor cursor = db.query(USER_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkProductType(String productType) {
        // array of columns to fetch
        String[] columns = {
                PRODUCT_TYPES_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = PRODUCT_TYPES_NAME + " = ?";
        // selection argument
        String[] selectionArgs = {productType};

        Cursor cursor = db.query(PRODUCT_TYPES_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /*public boolean checkProduct(String productName) {
        // array of columns to fetch
        String[] columns = {
                PRODUCT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = PRODUCT_NAME + " = ?";
        // selection argument
        String[] selectionArgs = {productName};

        Cursor cursor = db.query(PRODUCT_TABLE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }*/
}