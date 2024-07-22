package com.meditation.pojo;

import java.util.List;

/**
 * @time: 2024/7/18 10:49
 * @description:
 */

public class corporation {
    private overview overview;
    private List<List<String>> lists;

    private String alike;

    public corporation(com.meditation.pojo.overview overview, List<List<String>> lists, String alike) {
        this.overview = overview;
        this.lists = lists;
        this.alike = alike;
    }

    public corporation() {
    }

    public List<List<String>> getLists() {
        return lists;
    }

    public void setLists(List<List<String>> lists) {
        this.lists = lists;
    }

    public String getAlike() {
        return alike;
    }

    public void setAlike(String alike) {
        this.alike = alike;
    }

    public com.meditation.pojo.overview getOverview() {
        return overview;
    }

    public void setOverview(com.meditation.pojo.overview overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "corporation{" +
                "overview=" + overview +
                ", lists=" + lists +
                ", alike='" + alike + '\'' +
                '}';
    }
}
