package com.laowang.pojo;

/**
 *
 *
 *
 *@time: 2023/12/12 21:42
 *@description: 
 */
public class User {
    private Integer id;

    private String userNumber;

    private String name;

    private String eMail;

    private String account;

    private String password;

    private byte[] headImage;

    private String sex;

    private Integer credits;

    private Integer gold;

    private Integer jurisdiction;

    public User() {
    }

    public User(String userNumber, String name, String eMail, String account, String password, String sex,
                Integer credits, Integer gold, Integer jurisdiction) {
        this.userNumber = userNumber;
        this.name = name;
        this.eMail = eMail;
        this.account = account;
        this.password = password;
        this.sex = sex;
        this.credits = credits;
        this.gold = gold;
        this.jurisdiction = jurisdiction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getHeadImage() {
        return headImage;
    }

    public void setHeadImage(byte[] headImage) {
        this.headImage = headImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(Integer jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userNumber=").append(userNumber);
        sb.append(", name=").append(name);
        sb.append(", eMail=").append(eMail);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", headImage=").append(headImage);
        sb.append(", sex=").append(sex);
        sb.append(", credits=").append(credits);
        sb.append(", gold=").append(gold);
        sb.append(", jurisdiction=").append(jurisdiction);
        sb.append("]");
        return sb.toString();
    }
}