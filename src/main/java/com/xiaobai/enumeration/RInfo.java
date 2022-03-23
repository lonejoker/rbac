package com.xiaobai.enumeration;

/**
 * 返回状态枚举类
 */
public enum RInfo {
    // 成功状态
    SUCCESS(200, "操作成功"),
    SUCCESSLOGOUT(200, "退出成功"),
    // 失败状态
    ERROR2xx(201, "其他200错误"),
    ERROR400(400, "（错误请求） 服务器不理解请求的语法"),
    ERROR4xx(444, "其他错误"),
    ERROR412(412, "文件已存在，不满足"),
    ERROR401(401, "（未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应"),
    ERROR403(403, "（禁止） 服务器拒绝请求（可能没有权限）"),
    ERROR404(404, "（未找到） 服务器找不到请求的网页"),
    ERROR500(500, "（服务器内部错误） 服务器遇到错误，无法完成请求");

    private final Integer code;
    private final String msg;

    RInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
