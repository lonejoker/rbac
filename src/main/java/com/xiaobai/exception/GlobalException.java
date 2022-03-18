package com.xiaobai.exception;

import com.xiaobai.enumeration.RInfo;
import com.xiaobai.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 13:08
 * @Version 1.0
 * @ClassName GlobalException
 * @Description 类方法说明：全局异常处理
 */
@RestControllerAdvice
public class GlobalException {
    public void method(HandlerMethod method) {
        System.out.println("出现异常的class路径：" + method.getBean().getClass());
        System.out.println("出现异常的方法名：" + method.getMethod().getName());
    }

    @ExceptionHandler(com.xiaobai.exception.MyException.class)
    public R myException(com.xiaobai.exception.MyException e, HandlerMethod method) {
        System.out.println(e.getMessage());
        method(method);
        e.printStackTrace();
        return R.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public R exception(Exception e, HandlerMethod method) {
        System.out.println(e.getMessage());
        method(method);
        e.printStackTrace();
        return R.error(RInfo.ERROR4xx.getCode(), e.getMessage());
    }
}