package com.rharshit.stocker.data;

import java.util.Objects;

public class User {
    private String userName;
    private String userEmail;
    private String userUid;

    public User() {

    }

    public User(String userName, String userEmail, String userUid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                userEmail.equals(user.userEmail) &&
                userUid.equals(user.userUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userEmail, userUid);
    }
}
