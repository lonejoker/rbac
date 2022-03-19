package com.xiaobai.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xiaobai.enumeration.RInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 终于白发始于青丝
 * @create 2022-01-24 下午 14:01
 * @program BlogProject
 * @Version 1.0
 * @ClassName R
 * @Description 类方法说明：统一返回数据格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class R {
    // 返回状态码
    private Integer code;
    // 返回信息
    private String msg;
    // 返回数据
    private Object data;

    public static R successCm() {
        return new R(RInfo.SUCCESS.getCode(), RInfo.SUCCESS.getMsg());
    }

    public static R successCmd(Object data) {
        return new R(RInfo.SUCCESS.getCode(), RInfo.SUCCESS.getMsg(), data);
    }

    public static R error(Integer code, String msg) {
        return new R(code, msg);
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
}
