package com.sds.emp.user.services;

import java.io.Serializable;

public class UserVO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    private String password;

    private boolean enabled;


    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String param) {
        this.userId = param;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String param) {
        this.userName = param;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String param) {
        this.password = param;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    public String toString() {
        StringBuffer arguments = new StringBuffer();
        arguments.append(" userId - " + userId + "\n");
        arguments.append(" userName - " + userName + "\n");
        arguments.append(" password - " + password + "\n");

        return arguments.toString();
    }
}
