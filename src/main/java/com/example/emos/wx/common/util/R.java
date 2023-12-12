package com.example.emos.wx.common.util;


import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    public R() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    //重写put()  ---》目的是为了可以形成链式调用
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    //声明一些工厂方法，每次我们在调用R对象的时候，都需要自己new，然后再往里面put数据，有的时候不方便，那我们能不能提供一些定义好的工厂方法，让我们去调用呢
    //静态工厂方法
    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }


}
