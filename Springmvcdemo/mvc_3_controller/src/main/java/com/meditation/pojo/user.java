package com.meditation.pojo;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/22 22:18
 */

public class user {
    String userid;
    String username;

    public user(String userid, String username) {
        this.userid = userid;
        this.username = username;
    }

    public user() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "user{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
