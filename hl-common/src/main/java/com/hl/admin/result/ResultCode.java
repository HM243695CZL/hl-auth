package com.hl.admin.result;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(501, "参数检验失败"),
    PASSWORD_ERROR(502, "密码错误"),
    UNAUTHORIZED(401, "您未登录"),
    FORBIDDEN(403, "没有相关权限"),
    UN_KNOWN(9999, "未知异常，请联系管理员，或稍后再试!");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
