package com.caicai.jiujiu.bean

enum class ErrorResult(var code: String, var message: String) {
    ERROR("00000", "未知错误"),
    SUCCESS("10000", "success"),
    ACCESS_DENIED("20002", "没有权限"),
    USERNAME_EXIST("30001", "该用户名已注册"),
    PHONE_EXIST("30002", "该手机号已注册"),
    EMAIL_EXIST("30003", "该邮箱已注册"),
    USER_LOCKED("40001", "用户被封禁"),
    USER_DISABLED("40002", "用户不可用"),
    PASSWORD_ERROR("40003", "密码错误"),
    USER_NOT_FOUND("40004", "用户不存在"),
    LOGIN_INFO_ERROR("50001", "登录信息异常"),
    LOGIN_INFO_EXPIRED("50002", "登录信息已过期"),
}