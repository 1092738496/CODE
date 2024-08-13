package com.meditation.pojo;

import java.util.List;
import java.util.Map;

/**
 * @time: 2024/7/30 17:21
 * @description:
 */

public class sfp {
    private String host;
    private String guest;
    private Map<String, List<List<String>>> Ds;

    public sfp(String host, String guest, Map<String, List<List<String>>> ds) {
        this.host = host;
        this.guest = guest;
        Ds = ds;
    }

    public sfp() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Map<String, List<List<String>>> getDs() {
        return Ds;
    }

    public void setDs(Map<String, List<List<String>>> ds) {
        Ds = ds;
    }

    @Override
    public String toString() {
        return "sfp{" +
                "host='" + host + '\'' +
                ", guest='" + guest + '\'' +
                ", Ds=" + Ds +
                '}';
    }
}
