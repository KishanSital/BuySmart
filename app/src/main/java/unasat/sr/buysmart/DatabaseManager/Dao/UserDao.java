package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import unasat.sr.buysmart.DatabaseManager.SQLiteConfig.SQLiteConfig;
import unasat.sr.buysmart.DatabaseManager.Tables.*;
import unasat.sr.buysmart.Entities.User;

public class UserDao {

    SQLiteConfig dao;

    public UserDao(Context context) {
        dao = new SQLiteConfig(context);
    }

    public boolean addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.columnFirstname, user.getFirstname());
        contentValues.put(UserTable.columnLastname, user.getLastname());
        contentValues.put(UserTable.columnPhoneNumber1, user.getPhoneNumber1());
        contentValues.put(UserTable.columnPhoneNumber2, user.getPhoneNumber2());
        contentValues.put(UserTable.columnNationality, user.getNationality());
        contentValues.put(UserTable.columnUserTypeId, 1);//user.getUserType());
        contentValues.put(UserTable.columnEmail, user.getEmail());
        contentValues.put(UserTable.columnUsername, user.getUsername());
        contentValues.put(UserTable.columnPassword, user.getPassword());
        SQLiteDatabase db = dao.getWritableDatabase();
//        dao.close();
        return db.insert(UserTable.TableName, null, contentValues) != -1;
    }
}
