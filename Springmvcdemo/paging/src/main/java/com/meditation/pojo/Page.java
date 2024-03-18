package com.meditation.pojo;

import java.util.List;

/**
 * @description:
 * @author: Andy
 * @time: 2021/2/7 20:43
 */


public class Page {
    private Integer thisPage; // 当前页数
    private Integer pageSize;  //页面大小,每页显示多少数据
    private List<Book> limitbook; //查询结果
    private Integer totalbook;  //数据总数
    private Integer totalPage;  //共有多少页

    public Page(Integer thisPage, Integer pageSize, List<Book> limitbook, Integer totalbook) {
        this.thisPage = thisPage;
        this.pageSize = pageSize;
        this.limitbook = limitbook;
        this.totalbook = totalbook;
    }

    public Page() {
    }

    public Integer getThisPage() {
        return thisPage;
    }

    public void setThisPage(Integer thisPage) {
        this.thisPage = thisPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Book> getLimitbook() {
        return limitbook;
    }

    public void setLimitbook(List<Book> limitbook) {
        this.limitbook = limitbook;
    }

    public Integer getTotalbook() {
        return totalbook;
    }

    public void setTotalbook(Integer totalbook) {
        this.totalbook = totalbook;
    }

    public Integer getTotalPage() {
        this.totalPage = 0;
        if (null != totalbook & null != pageSize) {
            this.totalPage = this.totalbook % this.pageSize == 0 ? this.totalbook / this.pageSize :
                    this.totalbook / this.pageSize + 1;
        }
        return totalPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "thisPage=" + thisPage +
                ", pageSize=" + pageSize +
                ", limitbook=" + limitbook +
                ", totalbook=" + totalbook +
                ", totalPage=" + totalPage +
                '}';
    }
}
