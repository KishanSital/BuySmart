/*
package unasat.sr.buysmart.DatabaseManager.SQLiteConfig;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import unasat.sr.buysmart.DatabaseManager.Tables.UserTable;
import unasat.sr.buysmart.DatabaseManager.Tables.UserTypeTable;
import unasat.sr.buysmart.Entities.UserType;

public class SQLiteConfig extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "buysmart.db";
    public static int DATABASE_VERSION = 2;
    public Context context;

    public SQLiteConfig(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        context = context;
    }

    public SQLiteConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        context = context;
    }

    public SQLiteConfig(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SQLiteConfig(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
        context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserTable.create_sql_userTable);
        db.execSQL(UserTypeTable.create_sql_userTypeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertReferenceData() {

    }
}
*/
