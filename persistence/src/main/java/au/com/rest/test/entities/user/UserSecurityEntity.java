package au.com.rest.test.entities.user;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_security", schema = "RestExample")
@NamedQueries({
        @NamedQuery(name = "userSecurity.findByLogin", query = "SELECT u FROM UserSecurityEntity u WHERE u.userEntity.login = :login"),
        @NamedQuery(name = "userSecurity.findByAccountState", query = "SELECT u FROM UserSecurityEntity u WHERE u.accountState = :accountState")
})
public class UserSecurityEntity {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

//    @Basic
    @Column(name = "user_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    private UserEntity userEntity;
//    private Integer userId;

    @Basic
    @Column(name = "account_state", nullable = true, length = 10)
    private String accountState;

    @Basic
    @Column(name = "reason", nullable = true)
    private Integer reason;

    @Basic
    @Column(name = "last_access", nullable = true)
    private Timestamp lastAccess;

    @Basic
    @Column(name = "success_trys", nullable = true)
    private Integer successTrys;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
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

    public Timestamp getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Timestamp lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Integer getSuccessTrys() {
        return successTrys;
    }

    public void setSuccessTrys(Integer successTrys) {
        this.successTrys = successTrys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSecurityEntity that = (UserSecurityEntity) o;

        if (id != that.id) return false;
        if (userEntity != null ? !userEntity.equals(that.userEntity) : that.userEntity != null) return false;
        if (accountState != null ? !accountState.equals(that.accountState) : that.accountState != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;
        if (lastAccess != null ? !lastAccess.equals(that.lastAccess) : that.lastAccess != null) return false;
        if (successTrys != null ? !successTrys.equals(that.successTrys) : that.successTrys != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userEntity != null ? userEntity.hashCode() : 0);
        result = 31 * result + (accountState != null ? accountState.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (lastAccess != null ? lastAccess.hashCode() : 0);
        result = 31 * result + (successTrys != null ? successTrys.hashCode() : 0);
        return result;
    }
}
