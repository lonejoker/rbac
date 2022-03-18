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
    ERROR401(401, "（未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应"),
    ERROR402(402, "其他错误"),
    ERROR403(403, "（禁止） 服务器拒绝请求（可能没有权限）"),
    ERROR404(404, "（未找到） 服务器找不到请求的网页"),
    ERROR405(405, "（方法禁用） 禁用请求中指定的方法"),
    ERROR408(408, "（请求超时） 服务器等候请求时发生超时"),
    ERROR410(410, "（已删除）  如果请求的资源已永久删除，服务器就会返回此响应"),
    ERROR500(500, "（服务器内部错误） 服务器遇到错误，无法完成请求"),
    ERROR501(501, "（尚未实施） 服务器不具备完成请求的功能。 例如，服务器无法识别请求方法时可能会返回此代码"),
    ERROR502(502, "（错误网关） 服务器作为网关或代理，从上游服务器收到无效响应"),
    ERROR503(503, "（服务不可用） 服务器目前无法使用（由于超载或停机维护）。 通常，这只是暂时状态"),
    ERROR504(504, "（网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求"),
    ERROR505(505, "（HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本");

    private Integer code;
    private String msg;

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
