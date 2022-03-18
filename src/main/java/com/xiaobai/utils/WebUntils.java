package com.xiaobai.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 终于白发始于青丝
 * @create 2022-03-18 下午 21:35
 * @Version 1.0
 * @ClassName WebUntils
 * @Description 类方法说明：
 */
public class WebUntils {

    /**
     * @author 终于白发始于青丝
     * @Methodname renderString
     * @Description 类方法说明：将字符串渲染到客户端
     * @Return 返回值：java.lang.String
     * @Params javax.servlet.http.HttpServletResponse response：渲染对象
     * @Params java.lang.String string：待渲染的字符串
     * @Date 2022/2/4 上午 0:01
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}