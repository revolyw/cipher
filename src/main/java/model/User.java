package model;

import util.StringUtil;
import java.util.Date;

/**
 * Created by Willow on 16/11/19.
 */
//@Entity(name = "User")
//@Table(name = "user")
public class User {
    private Integer id;
    private String loginName;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private Integer status;
    private Date createTime;

    public User() {
    }

//    @Id
//    @GeneratedValue(generator = "increment")
//    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encryptPassword();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String encryptPassword() {
        if (StringUtil.isEmpty(this.password))
            return "";
        return StringUtil.md5(this.password);
    }
}
