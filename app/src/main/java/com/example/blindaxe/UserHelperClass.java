package com.example.blindaxe;
public class UserHelperClass {
    String email, username,diamondCount,heartCount,moneyCount;
    public UserHelperClass() {
    }
    public UserHelperClass(String email, String username, String diamondCount, String heartCount, String moneyCount) {
        this.email = email;
        this.username = username;
        this.diamondCount = diamondCount;
        this.heartCount = heartCount;
        this.moneyCount = moneyCount;
    }
    public String getDiamondCount() {
        return diamondCount;
    }
    public void setDiamondCount(String diamondCount) {
        this.diamondCount = diamondCount;
    }
    public String getHeartCount() {
        return heartCount;
    }
    public void setHeartCount(String heartCount) {
        this.heartCount = heartCount;
    }
    public String getMoneyCount() {
        return moneyCount;
    }
    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}