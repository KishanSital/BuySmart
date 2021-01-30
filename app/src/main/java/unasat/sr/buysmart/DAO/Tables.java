package unasat.sr.buysmart.DAO;

public class Tables {
}
class UserTable {

    private final static String TableName = "user";
    private final static String columnID = "id";
    private final static String columnFirstname = "firstname";
    private final static String columnLastname = "lastname";
    private final static String columnPhoneNumber1 = "phone_number1";
    private final static String columnPhoneNumber2= "phone_number2";
    private final static String columnNationality = "nationality";
    private final static String columnUserTypeId = "user_type_id";
    private final static String columnEmail = "email";
    private final static String columnUsername = "uesrname";
    private final static String columnPassword = "password";
    private final static String create_sql_userTable = "CREATE TABLE " + TableName + " (\n" +
                                                        "    " + columnID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                                                        "    " + columnFirstname + " VARCHAR(100) NOT NULL,\n" +
                                                        "    " + columnLastname + " VARCHAR(100) NOT NULL,\n" +
                                                        "    " + columnPhoneNumber1 + " INTEGER NOT NULL,\n" +
                                                        "    " + columnPhoneNumber2 + " INTEGER,\n" +
                                                        "    " + columnNationality + " INTEGER NOT NULL,\n" +
                                                        "    " + columnUserTypeId + " INTEGER NOT NULL,\n" +
                                                        "    " + columnEmail + " VARCHAR(200) NOT NULL,\n" +
                                                        "    " + columnUsername + " VARCHAR(50) NOT NULL,\n" +
                                                        "    " + columnPassword + " datetime NOT NULL,\n" +
                                                        ");";

}
