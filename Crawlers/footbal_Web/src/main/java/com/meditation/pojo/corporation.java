package com.meditation.pojo;

import java.util.List;
import java.util.Map;

/**
 * @time: 2024/7/18 10:49
 * @description:
 */

public class corporation {
    private overview overview;
    private List<List<String>> lists;

    private String alike;
    private Map<String,Map<String,Integer>> data_transmit;

    public corporation() {
    }

    public corporation(com.meditation.pojo.overview overview, List<List<String>> lists, String alike, Map<String,
            Map<String, Integer>> data_transmit) {
        this.overview = overview;
        this.lists = lists;
        this.alike = alike;
        this.data_transmit = data_transmit;
    }

    public com.meditation.pojo.overview getOverview() {
        return overview;
    }

    public void setOverview(com.meditation.pojo.overview overview) {
        this.overview = overview;
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

    public Map<String, Map<String, Integer>> getData_transmit() {
        return data_transmit;
    }

    public void setData_transmit(Map<String, Map<String, Integer>> data_transmit) {
        this.data_transmit = data_transmit;
    }

    @Override
    public String toString() {
        return "corporation{" +
                "overview=" + overview +
                ", lists=" + lists +
                ", alike='" + alike + '\'' +
                ", data_transmit=" + data_transmit +
                '}';
    }
}
