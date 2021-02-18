
package unasat.sr.buysmart.Entities;

public class UserType {

    private int userTypeId;
    private String name;

    public  UserType(){


    }

    public UserType(int userTypeId, String name) {
        this.userTypeId = userTypeId;
        this.name = name;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", name='" + name + '\'' +
                '}';
    }
}
/*
public class UserType {

    private Long id;
    private String name;

    public UserType(String name) {
        this.name = name;
    }

    public UserType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserType(UserType userType) {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
*/
