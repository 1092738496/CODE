package com.meditation.pojo;

/**
 * @time: 2024/8/1 0:18
 * @description:
 */

public class data_status<T> {
    private int code;
    private String msg;
    private T data;

    public data_status(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public data_status() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "data_status{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
