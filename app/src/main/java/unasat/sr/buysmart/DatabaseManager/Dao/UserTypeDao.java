/*
package unasat.sr.buysmart.DatabaseManager.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import unasat.sr.buysmart.DatabaseManager.SQLiteConfig.SQLiteConfig;
import unasat.sr.buysmart.DatabaseManager.Tables.UserTable;
import unasat.sr.buysmart.DatabaseManager.Tables.UserTypeTable;
import unasat.sr.buysmart.Entities.User;
import unasat.sr.buysmart.Entities.UserType;

public class UserTypeDao {

    SQLiteConfig dao;

    public UserTypeDao(Context context) {
        dao = new SQLiteConfig(context);
    }

    public boolean addUserType(UserType userType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTypeTable.columnName, userType.getName());
        SQLiteDatabase db = dao.getWritableDatabase();
        return db.insert(UserTypeTable.TableName, null, contentValues) != -1;
    }

    public UserType getUserType(@Nullable Long userTypeId, String userTypeName) {
        UserType userType = null;
        SQLiteDatabase db = dao.getReadableDatabase();
        String sql;
        if(userTypeId != null && userTypeName == null) {
            sql = String.format("select * from %s where %s = '%s'", UserTypeTable.TableName, UserTypeTable.columnId, userTypeId);
        }
        else {
            sql = String.format("select * from %s where %s = '%s'", UserTypeTable.TableName, UserTypeTable.columnName, userTypeName);
        }
        Cursor cursor = db.rawQuery(sql, null);
        System.out.println(cursor.getCount());
        if (cursor.moveToFirst()) {
            userType = new UserType(cursor.getLong(0)
                                    ,cursor.getString(1)
                            );
        }
        db.close();
        return userType;
    }

    public void insertRefUserTypes() {
        UserType adminUserType = new UserType("Admin");
        UserType customerUserType = new UserType("Customer");
        List<UserType> listRefUserTypes= new ArrayList<UserType>(2);
        listRefUserTypes.add(adminUserType);
        listRefUserTypes.add(customerUserType);

        for (int i = 0; i < listRefUserTypes.size(); i++) {
            if(this.getUserType(null, listRefUserTypes.get(i).getName()) == null) {
                System.out.println("inserting usertype");
                this.addUserType(listRefUserTypes.get(i));
            }
            else {
                System.out.println(this.getUserType(null, listRefUserTypes.get(i).getName()).toString());
            }
        }
    }
}
*/
