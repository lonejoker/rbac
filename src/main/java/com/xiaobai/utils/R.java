package com.xiaobai.utils;

import com.xiaobai.enumeration.RInfo;

/**
 * @author 终于白发始于青丝
 * @create 2022-01-24 下午 14:01
 * @program BlogProject
 * @Version 1.0
 * @ClassName unified
 */
// 统一返回数据格式
public class R {
    // 返回状态码
    private Integer code;
    // 返回信息
    private String msg;
    // 返回数据
    private Object data;

    public static R successCM() {
        //return new R(200, "操作成功");
        return new R(RInfo.SUCCESS.getCode(), RInfo.SUCCESS.getMsg());
    }

    public static R logoutsuccessCM(Integer code, String msg) {
        //return new R(200, "操作成功");
        return new R(code, msg);
    }

    public static R successCMD(Object data) {
        //return new R(200, "操作成功", data);
        return new R(RInfo.SUCCESS.getCode(), RInfo.SUCCESS.getMsg(), data);
    }

    public static R error(Integer code, String msg) {
        return new R(code, msg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public R() {
    }

    public R(Integer code) {
        this.code = code;
    }

    public R(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public R(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
