package au.com.rest.test.entities.user;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_details", schema = "RestExample")
@NamedQueries({
        @NamedQuery(name = "userDetails.findByLogin", query = "SELECT u FROM UserDetailsEntity u WHERE u.userEntity.login = :login"),
        @NamedQuery(name = "userDetails.findByEmail", query = "SELECT u FROM UserDetailsEntity u WHERE u.email = :email"),
        @NamedQuery(name = "userDetails.findByName", query = "SELECT u FROM UserDetailsEntity u WHERE u.fullName= :fullName")
})
public class UserDetailsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity userEntity;

    @Basic
    @Column(name = "fullName", nullable = true, length = 100)
    private String fullName;

    @Basic
    @Column(name = "email", nullable = true, length = 100)
    private String email;

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetailsEntity that = (UserDetailsEntity) o;

        if (id != that.id) return false;
        if (userEntity != null ? !userEntity.equals(that.userEntity) : that.userEntity != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userEntity != null ? userEntity.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
