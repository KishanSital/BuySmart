
package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;


public class GlobalDAO extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "buysmart.db";
    private static final int DATABASE_VERSION = 1;

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

    public static final String USER_TYPES_TABLE = "user_types";
    public static final String USER_TYPES_ID = "user_types_id";
    public static final String USER_TYPES_NAME = "name";


    public static final int ADMIN = 1;


    private static final String SQL_USER_TABLE_QUERY = String.format("create table %s " +
                    "(%s INTEGER PRIMARY KEY , %s STRING NOT NULL UNIQUE , %s STRING NOT NULL UNIQUE,  %s STRING NOT NULL, " +
            "%s STRING NOT NULL, %s STRING NOT NULL, %s INTEGER NOT NULL , %s INTEGER, %s STRING NOT NULL , %s INTEGER)",
            USER_TABLE, USER_ID, USER_EMAIL, USER_USERNAME, USER_PASSWORD,  USER_FIRSTNAME, USER_LASTNAME,
            USER_PHONE_NUMBER1, USER_PHONE_NUMBER2 ,USER_NATIONALITY, USER_TYPE_ID) ;

    private static final String SQL_USER_TYPE_QUERY = String.format("create table %s" +
            " (%s INTEGER PRIMARY KEY, %s STRING NOT NULL)",USER_TYPES_TABLE, USER_TYPES_ID, USER_TYPES_NAME);

    public GlobalDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /*public void setDefaultUserTypes(){
        UserType type = findByUserType("Admin");
        UserType type1 = findByUserType("Customer");
        if (type !=null && type1 !=null){
            return;
        } else {
            List<ContentValues> records = new ArrayList<>();
            ContentValues record1 = new ContentValues();
            record1.put(USER_TYPES_ID, 1);
            record1.put(USER_TYPES_NAME, "Admin");
            records.add(record1);
            ContentValues record2 = new ContentValues();
            record2.put(USER_TYPES_ID, 2);
            record2.put(USER_TYPES_NAME, "Customer");
            records.add(record2);
            insertMultipleRecord(USER_TYPES_TABLE, records);

        }
    }


    public void setAdminCredentials() {
        User user = findByUsername("owner");
        if (user != null) {
            return;
        }
        //Set default username and password
        ContentValues values = new ContentValues();
        values.put(USER_ID, 1);
        values.put(USER_EMAIL, "owner@owner.com");
        values.put(USER_USERNAME, "owner");
        values.put(USER_PASSWORD, "owner");
        values.put(USER_FIRSTNAME, "owner");
        values.put(USER_LASTNAME, "owner");
        values.put(USER_PHONE_NUMBER1, 123456);
        values.put(USER_PHONE_NUMBER2, 123459);
        values.put(USER_NATIONALITY, "Suriname");
        values.put(USER_TYPE_ID, 1);
        insertOneRecord(USER_TABLE, values);

    }*/

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
    }

    public void updateUsers(User userClass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME,userClass.getUsername());
        contentValues.put(USER_PASSWORD,userClass.getPassword());
        db.update(USER_TABLE,contentValues,USER_USERNAME + " = ?" ,
                new String[]{userClass.getUsername()});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_USER_TABLE_QUERY);
        db.execSQL(SQL_USER_TYPE_QUERY);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_USER_TABLE_QUERY);
        db.execSQL(SQL_USER_TYPE_QUERY);

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


    public long insertOneRecord(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(tableName, null, contentValues);
        db.close();
        //return the row ID of the newly inserted row, or -1 if an error occurred
        return rowId;
    }



    public boolean insertMultipleRecord(String tableName, List<ContentValues> contentValuesList) {
        SQLiteDatabase db = getWritableDatabase();
        long countOnSucces = 0;
        long rowId = 0;
        for (ContentValues contentValues : contentValuesList) {
            rowId = db.insert(tableName, null, contentValues);
            countOnSucces = (rowId == 1 ? countOnSucces++ : countOnSucces);
        }
        boolean isSuccess = (countOnSucces > 0 && contentValuesList.size() == countOnSucces);
        db.close();
        //return the true id all inserts where succesfull
        return isSuccess;
    }

    public User findByUsername(String username) {
        User user = new User();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?", USER_USERNAME);
        String[] whereArgs = {username};
        cursor = db.query(USER_TABLE, null, whereClause, whereArgs ,null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            user.setEmail(cursor.getString(cursor.getColumnIndex(USER_EMAIL)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(USER_USERNAME)));
            user.setFirstname(cursor.getString(cursor.getColumnIndex(USER_FIRSTNAME)));
            user.setLastname(cursor.getString(cursor.getColumnIndex(USER_LASTNAME)));
            user.setPhoneNumber1(cursor.getInt(cursor.getColumnIndex(USER_PHONE_NUMBER1)));
            user.setPhoneNumber2(cursor.getInt(cursor.getColumnIndex(USER_PHONE_NUMBER2)));
            user.setNationality(cursor.getString(cursor.getColumnIndex(USER_NATIONALITY)));
        }
        return user;
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


}

