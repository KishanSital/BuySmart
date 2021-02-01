package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import unasat.sr.buysmart.DatabaseManager.SQLiteConfig.SQLiteConfig;
import unasat.sr.buysmart.DatabaseManager.Tables.*;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;

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
        contentValues.put(UserTable.columnUserTypeId, user.getUserTypeId());
        contentValues.put(UserTable.columnEmail, user.getEmail());
        contentValues.put(UserTable.columnUsername, user.getUsername());
        contentValues.put(UserTable.columnPassword, user.getPassword());
        SQLiteDatabase db = dao.getWritableDatabase();
//        db.close();
        return db.insert(UserTable.TableName, null, contentValues) != -1;
    }

    public User getUserByUsername(String username, @Nullable String email) {
        UserTypeDao userTypeDao = new UserTypeDao(this.dao.context);
        String sql;
        User user = null;
        SQLiteDatabase db = dao.getReadableDatabase();
        if(email != null && username != null) {
            sql = String.format("select * from %s where %s = '%s and %s = %s'", UserTable.TableName, UserTable.columnUsername, username, UserTable.columnEmail, email);
        }
        else if (username != null){
            sql = String.format("select * from %s where %s = '%s'", UserTable.TableName, UserTable.columnUsername, username);
        }
        else {
            sql = String.format("select * from %s where %s = '%s'", UserTable.TableName, UserTable.columnEmail, email);
        }
        Cursor cursor = db.rawQuery(sql, null);
        System.out.println(cursor.getCount());
        if (cursor.moveToFirst()) {
            System.out.println(cursor.getLong(6));
//            UserType userType = userTypeDao.getUserType(cursor.getLong(6), null);

            user = new User( cursor.getString(1)
                            ,cursor.getString(2)
                            ,cursor.getInt(3)
                            ,cursor.getInt(4)
                            ,cursor.getString(5)
                            ,cursor.getLong(6)//userTypeDao.getUserType(cursor.getLong(6), null).getName()
                            ,cursor.getString(7)
                            ,cursor.getString(8)
                            ,cursor.getString(9)
                            );
        }
        db.close();
        return user;
    }
}
