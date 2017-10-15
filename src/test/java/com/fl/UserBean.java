package com.fl;

import java.io.Serializable;

/**
 * Created by chenfeilong on 2017/10/13.
 */
public class UserBean implements Serializable {
    private String userName;
    private String roleName;

    public UserBean(String userName, String roleName) {
        this.userName = userName;
        this.roleName = roleName;
    }

    public UserBean() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
