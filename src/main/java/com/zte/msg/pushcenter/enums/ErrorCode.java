package com.zte.msg.pushcenter.enums;

/**
 * description:
 *
 * @author chentong
 * @version 1.0
 * @date 2020/12/10 15:56
 */
public enum ErrorCode {

    /**
     * 鉴权
     */
    AUTHORIZATION_CHECK_FAIL(401, "authorization.check.fail"),

    AUTHORIZATION_IS_OVERDUE(401, "authorization.is.overdue"),

    AUTHORIZATION_INVALID(401, "authorization.invalid"),

    AUTHORIZATION_EMPTY(401, "authorization.empty"),
    /**
     * 该用户无相关资源操作权限
     */
    RESOURCE_AUTH_FAIL(32000001, "resource.authority.error"),

    /**
     * 参数错误
     */
    PARAM_ERROR(32000002, "param.error"),

    /**
     * 参数超过范围
     */
    PARAM_OUT_OF_RANGE(32000003, "param.range.error"),

    /**
     * 错误的枚举值
     */
    ENUM_VALUE_ERROR(32000004, "enum.value.error"),

    /**
     * 字段不符合要求，仅限中英文字母、数字、中划线和下划线，且长度在4-32之间
     */
    PARAM_PATTERN_INCOMPATIBLE(32000005, "param.pattern.incompatible"),

    /**
     * 参数不能为空
     */
    PARAM_NULL_ERROR(32000006, "param.null.error"),

    /**
     * 资源配置初始化失败
     */
    RESOURCE_INIT_ERROR(32000007, "resource.init.error"),

    /**
     * 参数小于最小值
     */
    PARAM_MIN_ERROR(32000008, "param.min"),

    DATA_EXIST(32000009, "data.exist"),

    INSERT_ERROR(31000001, "insert.error"),

    SELECT_ERROR(31000002, "select.error"),

    UPDATE_ERROR(31000003, "update.error"),

    DELETE_ERROR(31000004, "delete.error"),

    /**
     * 参数不在枚举范围中
     */
    NOT_IN_ENUM(32000010, "not.in.enum"),

    /**
     * 资源不存在
     */
    RESOURCE_NOT_EXIST(32000012, "resource.not.exist"),

    /**
     * 指定的推送方式不存在
     */
    PUSH_TYPE_NOT_EXIST(32000013, "push.type.not.exist"),

    SMS_PUSH_ERROR(32000015, "sms.push.error"),

    MAIL_PUSH_ERROR(32000016, "mail.push.error"),

    WECHAT_PUSH_ERROR(32000017, "wechat.push.error"),

    PROVIDER_NOT_EXIST(32000018, "provider.not.exist"),

    TEMPLATE_EXIST(32000019, "template.exist"),


    /**
     * 邮箱
     */
    MAIL_PARAM_EMPTY(32000020, "mail.param.empty"),

    /**
     * 第三方供应商服务
     */
    PROVIDER_EXIST(3200040, "provider.exist"),

    USER_NOT_EXIST(3200050, "user.not.exist"),

    LOGIN_PASSWORD_ERROR(3200051, "login.password.error"),

    USER_PWD_CHANGE_FAIL(320052, "user.pwd.change.fail"),

    APP_ROLE_EDIT_ERROR(320053,"app.role.edit.error");

    private Integer code;

    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String messageOf(Integer code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.code.equals(code)) {
                return errorCode.message;
            }
        }
        return "";
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
