package com.meditation.pojo;

import java.sql.Timestamp;

/**
 * @description:
 * @author: Andy
 * @time: 2020/12/30 2:52
 */

public class Book {
    private int id;
    private String BookName;
    private byte[] Booklmage;
    private String Author;
    private String intro;
    private Timestamp UpdateTime;
    private String BookUrl;
    private double Price;
    private double Rent;

    public Book() {
    }

    public Book(int id, String bookName, byte[] booklmage, String author, String intro, Timestamp updateTime,
                String bookUrl, double price, double rent) {
        this.id = id;
        BookName = bookName;
        Booklmage = booklmage;
        Author = author;
        this.intro = intro;
        UpdateTime = updateTime;
        BookUrl = bookUrl;
        Price = price;
        Rent = rent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public byte[] getBooklmage() {
        return Booklmage;
    }

    public void setBooklmage(byte[] booklmage) {
        Booklmage = booklmage;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Timestamp getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        UpdateTime = updateTime;
    }

    public String getBookUrl() {
        return BookUrl;
    }

    public void setBookUrl(String bookUrl) {
        BookUrl = bookUrl;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getRent() {
        return Rent;
    }

    public void setRent(double rent) {
        Rent = rent;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", BookName='" + BookName + '\'' +
                ", Author='" + Author + '\'' +
                ", intro='" + intro + '\'' +
                ", UpdateTime=" + UpdateTime +
                ", BookUrl='" + BookUrl + '\'' +
                ", Price=" + Price +
                ", Rent=" + Rent +
                '}';
    }
}
