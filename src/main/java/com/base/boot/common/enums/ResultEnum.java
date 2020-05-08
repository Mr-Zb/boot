package com.base.boot.common.enums;

import lombok.Getter;

/**
 * @author zbang
 * @description: 返回的错误码枚举类
 */
@Getter
public enum ResultEnum {

    SUCCESS(200, "成功"),
    FAILURE(500, "失败"),
    USER_NEED_AUTHORITIES(201, "用户未登录"),
    USER_LOGIN_FAILED(202, "用户账号或密码错误"),
    USER_NULL(203, "账号不能为空"),
    USER_NOT(204, "用户不存在"),
    USER_LOGIN_SUCCESS(205, "用户登录成功"),
    USER_NO_ACCESS(206, "用户无权访问"),
    USER_LOGOUT_SUCCESS(207, "用户登出成功"),
    TOKEN_IS_NOTSURE(208, "此token不正确"),
    LOGIN_IS_OVERDUE(209, "此token已过期"),
    LOGIN_IS_NULL(210, "token不能为空"),
    IMG_ERR_CODE(301, "图片验证码错误"),
    IMG_NULL_CODE(302, "验证码不能为空"),
    URL_NOT_EXIST(404, "路径不存在，请检查路径是否正确"),
    DATA_IS_EXIST(405, "数据库中已存在该记录"),
    SYS_ERR_CODE(501, "系统内部异常"),
    SQL_ERR_CODE(502, "SQL异常"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @deprecation:通过code返回枚举
     */
    public static ResultEnum parse(int code) {
        ResultEnum[] values = values();
        for (ResultEnum value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new RuntimeException("Unknown code of ResultEnum");
    }
}
