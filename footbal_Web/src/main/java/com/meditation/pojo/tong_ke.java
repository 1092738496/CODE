package com.meditation.pojo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @time: 2024/7/20 20:49
 * @description:
 */

public class tong_ke {
    private LinkedHashMap<String, List<List<String>>> About_5_shows;
    private LinkedHashMap<String, String> maps;

    public tong_ke(LinkedHashMap<String, List<List<String>>> about_5_shows, LinkedHashMap<String, String> maps) {
        About_5_shows = about_5_shows;
        this.maps = maps;
    }

    public tong_ke() {
    }

    public LinkedHashMap<String, List<List<String>>> getAbout_5_shows() {
        return About_5_shows;
    }

    public void setAbout_5_shows(LinkedHashMap<String, List<List<String>>> about_5_shows) {
        About_5_shows = about_5_shows;
    }

    public LinkedHashMap<String, String> getMaps() {
        return maps;
    }

    public void setMaps(LinkedHashMap<String, String> maps) {
        this.maps = maps;
    }

    @Override
    public String toString() {
        return "tong_ke{" +
                "About_5_shows=" + About_5_shows +
                ", maps=" + maps +
                '}';
    }
}
