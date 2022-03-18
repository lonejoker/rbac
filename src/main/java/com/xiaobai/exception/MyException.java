package com.xiaobai.exception;

import com.xiaobai.enumeration.RInfo;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 13:07
 * @Version 1.0
 * @ClassName MyException
 * @Description 类方法说明：自定义异常
 */
public class MyException extends RuntimeException {
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public MyException(RInfo rInfo) {
        super(rInfo.getMsg());
        this.code = rInfo.getCode();
        this.msg = rInfo.getMsg();
    }
}
