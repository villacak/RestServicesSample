package au.com.rest.test.pojos;

import java.util.Date;

public class UserAppSecurity extends UserApp {

    private int id;
    private int userEntity;
    private String accountState;
    private Integer reason;
    private Date lastAccess;
    private Integer successTrys;


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

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Integer getSuccessTrys() {
        return successTrys;
    }

    public void setSuccessTrys(Integer successTrys) {
        this.successTrys = successTrys;
    }
}
