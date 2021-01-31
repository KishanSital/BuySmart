package unasat.sr.buysmart.DatabaseManager.Tables;

public class UserTable {

    public final static String TableName = "user";
    public final static String columnId = "id";
    public final static String columnFirstname = "firstname";
    public final static String columnLastname = "lastname";
    public final static String columnPhoneNumber1 = "phone_number1";
    public final static String columnPhoneNumber2= "phone_number2";
    public final static String columnNationality = "nationality";
    public final static String columnUserTypeId = "user_type_id";
    public final static String columnEmail = "email";
    public final static String columnUsername = "username";
    public final static String columnPassword = "password";
    public final static String create_sql_userTable = "CREATE TABLE " + TableName + " (\n" +
            "    " + columnId + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
            "    " + columnFirstname + " VARCHAR(100) NOT NULL,\n" +
            "    " + columnLastname + " VARCHAR(100) NOT NULL,\n" +
            "    " + columnPhoneNumber1 + " INTEGER NOT NULL,\n" +
            "    " + columnPhoneNumber2 + " INTEGER,\n" +
            "    " + columnNationality + " INTEGER NOT NULL,\n" +
            "    " + columnUserTypeId + " INTEGER NOT NULL,\n" +
            "    " + columnEmail + " VARCHAR(200) NOT NULL,\n" +
            "    " + columnUsername + " VARCHAR(50) NOT NULL,\n" +
            "    " + columnPassword + " datetime NOT NULL\n" +
            ");";


}