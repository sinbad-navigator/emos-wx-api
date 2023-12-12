package com.example.emos.wx.exception;

import lombok.Data;

@Data
public class EmosException extends RuntimeException {
    private String msg;
    private int code;

    public EmosException(String msg) {
        super(msg); //调用父类 RuntimeException 的构造函数
        this.msg = msg;
    }

    public EmosException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public EmosException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public EmosException(String msg, int code, Throwable e) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }


}
