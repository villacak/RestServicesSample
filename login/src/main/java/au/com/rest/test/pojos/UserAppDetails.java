package au.com.rest.test.pojos;

public class UserAppDetails extends UserApp {

    private int id;
    private int userEntity;
    private String fullName;;
    private String email;
    private String phone;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(int userEntity) {
        this.userEntity = userEntity;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
