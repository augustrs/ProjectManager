package com.example.projectmanagerkea.model;

public class User {
    private int userId;
    private String realName;
    private String username;
    private String password;
    private int roleId;


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public String getRealName() {
        return realName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public User(int userId, String realName, String username, String password, int roleId) {
        this.userId = userId;
        this.realName = realName;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
    public User() {

    }
}
