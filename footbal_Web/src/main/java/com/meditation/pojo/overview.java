package com.meditation.pojo;

/**
 * @time: 2024/7/20 12:17
 * @description:
 */

public class overview {
    private String zlarge_ball;
    private String zSum;
    private String zLittle_ball;
    private String kLarge_ball;
    private String kSum;
    private String kLittle_ball;

    public overview() {
    }

    public overview(String zlarge_ball, String zSum, String zLittle_ball, String kLarge_ball, String kSum,
                    String kLittle_ball) {
        this.zlarge_ball = zlarge_ball;
        this.zSum = zSum;
        this.zLittle_ball = zLittle_ball;
        this.kLarge_ball = kLarge_ball;
        this.kSum = kSum;
        this.kLittle_ball = kLittle_ball;
    }

    public String getZlarge_ball() {
        return zlarge_ball;
    }

    public void setZlarge_ball(String zlarge_ball) {
        this.zlarge_ball = zlarge_ball;
    }

    public String getzSum() {
        return zSum;
    }

    public void setzSum(String zSum) {
        this.zSum = zSum;
    }

    public String getzLittle_ball() {
        return zLittle_ball;
    }

    public void setzLittle_ball(String zLittle_ball) {
        this.zLittle_ball = zLittle_ball;
    }

    public String getkLarge_ball() {
        return kLarge_ball;
    }

    public void setkLarge_ball(String kLarge_ball) {
        this.kLarge_ball = kLarge_ball;
    }

    public String getkSum() {
        return kSum;
    }

    public void setkSum(String kSum) {
        this.kSum = kSum;
    }

    public String getkLittle_ball() {
        return kLittle_ball;
    }

    public void setkLittle_ball(String kLittle_ball) {
        this.kLittle_ball = kLittle_ball;
    }

    @Override
    public String toString() {
        return "overview{" +
                "zlarge_ball='" + zlarge_ball + '\'' +
                ", zSum='" + zSum + '\'' +
                ", zLittle_ball='" + zLittle_ball + '\'' +
                ", kLarge_ball='" + kLarge_ball + '\'' +
                ", kSum='" + kSum + '\'' +
                ", kLittle_ball='" + kLittle_ball + '\'' +
                '}';
    }
}
