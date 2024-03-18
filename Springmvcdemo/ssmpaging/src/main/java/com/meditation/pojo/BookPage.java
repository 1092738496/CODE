package com.meditation.pojo;

import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/4/25 23:29
 */

public class BookPage {
    private Integer NowPage;  //当前页数
    private Integer QuanlitySize; //页面大小，每页显示的条数
    private List<Book> tatolBook; //总数据
    private Integer tatolData;  //数据条数
    private Integer tatolpage; //总页数
    private String LikeBookName;//模糊查询；

    public BookPage(Integer nowPage, Integer quanlitySize, List<Book> tatolBook, Integer tatolData,
                    String LikeBookName) {
        NowPage = nowPage;
        QuanlitySize = quanlitySize;
        this.tatolBook = tatolBook;
        this.tatolData = tatolData;
        this.LikeBookName = LikeBookName;

    }

    public BookPage() {
    }

    public Integer getNowPage() {
        return NowPage;
    }

    public void setNowPage(Integer nowPage) {
        NowPage = nowPage;
    }

    public Integer getQuanlitySize() {
        return QuanlitySize;
    }

    public void setQuanlitySize(Integer quanlitySize) {
        QuanlitySize = quanlitySize;
    }

    public List<Book> getTatolBook() {
        return tatolBook;
    }

    public void setTatolBook(List<Book> tatolBook) {
        this.tatolBook = tatolBook;
    }

    public Integer getTatolData() {
        return tatolData;
    }

    public void setTatolData(Integer tatolData) {
        this.tatolData = tatolData;
    }

    public String getLikeBookName() {
        return LikeBookName;
    }

    public void setLikeBookName(String likeBookName) {
        LikeBookName = likeBookName;
    }

    public Integer getTatolpage() {
        if (null != tatolData & null!=QuanlitySize){
        this.tatolpage = tatolData % QuanlitySize == 0 ? tatolData / QuanlitySize : tatolData / QuanlitySize + 1;
        return this.tatolpage;
        }
        return 0;
    }

}
