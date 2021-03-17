package unasat.sr.buysmart.Entities;

public class User {

    private int userId;
    private String email;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int phoneNumber1;
    private int phoneNumber2;
    private String nationality;
    private int userTypeId;


    public User () {

    }


    public User( String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String email, String username, String password, String firstname, String lastname, int phoneNumber1,
                int phoneNumber2, String nationality) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.nationality = nationality;
    }
    public User(int userId, String email, String username, String password, String firstname, String lastname, int phoneNumber1,
                int phoneNumber2, String nationality, int userTypeId) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.nationality = nationality;
        this.userTypeId = userTypeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(int phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public int getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(int phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber1=" + phoneNumber1 +
                ", phoneNumber2=" + phoneNumber2 +
                ", nationality='" + nationality + '\'' +
                ", userTypeId=" + userTypeId +
                '}';
    }

    public String toStringPhone1() {
        return ""+ phoneNumber1;
    }
    public String toStringPhone2() {
        return ""+ phoneNumber1;
    }
}




/*public class User {

    private Long id;
    private String firstname;
    private String lastname;
    private Integer phoneNumber1;
    private Integer phoneNumber2;
    private String nationality;
    private String userType;
    private Long userTypeId;
    private String email;
    private String username;
    private String password;

    //Constructor with string usertype
    public User(String firstname, String lastname, Integer phoneNumber1, Integer phoneNumber2, String nationality, String userType, String email, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.nationality = nationality;
        this.userType = userType;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    //Constructor with int usertype used to inesrt
    public User(String firstname, String lastname, Integer phoneNumber1, Integer phoneNumber2, String nationality, Long userTypeId, String email, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.nationality = nationality;
        this.userTypeId = userTypeId;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(Integer phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public Integer getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(Integer phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phoneNumber1=" + phoneNumber1 +
                ", phoneNumber2=" + phoneNumber2 +
                ", nationality='" + nationality + '\'' +
                ", userType='" + userType + '\'' +
                ", userTypeId=" + userTypeId +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}*/


