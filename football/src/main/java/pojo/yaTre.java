package pojo;

import java.util.ArrayList;

/**
 * @time: 2024/5/13 8:09
 * @description:
 */

public class yaTre {
    private String home;
    private String guest;
    private String time;
    private String score;
    ArrayList<ArrayList<String>> lists;

    public yaTre() {
    }

    public yaTre(String home, String guest, String time, String score, ArrayList<ArrayList<String>> lists) {
        this.home = home;
        this.guest = guest;
        this.time = time;
        this.score = score;
        this.lists = lists;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public ArrayList<ArrayList<String>> getLists() {
        return lists;
    }

    public void setLists(ArrayList<ArrayList<String>> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "yaTre{" +
                "home='" + home + '\'' +
                ", guest='" + guest + '\'' +
                ", time='" + time + '\'' +
                ", score='" + score + '\'' +
                ", lists=" + lists +
                '}';
    }
}
