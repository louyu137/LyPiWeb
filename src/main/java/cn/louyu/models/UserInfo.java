package cn.louyu.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class UserInfo {

    @Id
    @Column(name = "userName",nullable = false,length = 128)
    private String userName; //用户名
    @Column(name = "password",nullable = false,length = 128)
    private String password; //密码
    @Column(name = "userType",nullable = false)
    private int userType;//用户类型
    @Column(name = "lastLoginTime",nullable = true)
    private Date lastLoginTime; //最后一次登录时间
    @Column(name = "lastPasswordReset",nullable = true)
    private Date lastPasswordReset; //最后一次修改密码的时间

    public UserInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserInfo(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }
}
