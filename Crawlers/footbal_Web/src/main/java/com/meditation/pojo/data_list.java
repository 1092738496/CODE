package com.meditation.pojo;

/**
 * @time: 2024/8/1 0:19
 * @description:
 */

public class data_list {
    private String date;
    private String time;
    private String historyDate;
    private String homeTeam;
    private String id;
    private String league;
    private String matchType;
    private int orderNum;
    private String score;
    private String visitingTeam;
    private String Bscore;
    private String Z_forthwith_mean;
    private String H_forthwith_mean;
    private String K_forthwith_mean;

    public data_list() {
    }

    public data_list(String date, String time, String historyDate, String homeTeam, String id, String league,
                     String matchType, int orderNum, String score, String visitingTeam, String bscore,
                     String z_forthwith_mean, String h_forthwith_mean, String k_forthwith_mean) {
        this.date = date;
        this.time = time;
        this.historyDate = historyDate;
        this.homeTeam = homeTeam;
        this.id = id;
        this.league = league;
        this.matchType = matchType;
        this.orderNum = orderNum;
        this.score = score;
        this.visitingTeam = visitingTeam;
        Bscore = bscore;
        Z_forthwith_mean = z_forthwith_mean;
        H_forthwith_mean = h_forthwith_mean;
        K_forthwith_mean = k_forthwith_mean;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(String visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public String getBscore() {
        return Bscore;
    }

    public void setBscore(String bscore) {
        Bscore = bscore;
    }

    public String getZ_forthwith_mean() {
        return Z_forthwith_mean;
    }

    public void setZ_forthwith_mean(String z_forthwith_mean) {
        Z_forthwith_mean = z_forthwith_mean;
    }

    public String getH_forthwith_mean() {
        return H_forthwith_mean;
    }

    public void setH_forthwith_mean(String h_forthwith_mean) {
        H_forthwith_mean = h_forthwith_mean;
    }

    public String getK_forthwith_mean() {
        return K_forthwith_mean;
    }

    public void setK_forthwith_mean(String k_forthwith_mean) {
        K_forthwith_mean = k_forthwith_mean;
    }

    @Override
    public String toString() {
        return "data_list{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", historyDate='" + historyDate + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", id='" + id + '\'' +
                ", league='" + league + '\'' +
                ", matchType='" + matchType + '\'' +
                ", orderNum=" + orderNum +
                ", score='" + score + '\'' +
                ", visitingTeam='" + visitingTeam + '\'' +
                ", Bscore='" + Bscore + '\'' +
                ", Z_forthwith_mean='" + Z_forthwith_mean + '\'' +
                ", H_forthwith_mean='" + H_forthwith_mean + '\'' +
                ", K_forthwith_mean='" + K_forthwith_mean + '\'' +
                '}';
    }
}
