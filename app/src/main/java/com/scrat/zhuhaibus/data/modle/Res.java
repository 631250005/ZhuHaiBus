package com.scrat.zhuhaibus.data.modle;

public class Res<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public Res setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Res setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }
}
