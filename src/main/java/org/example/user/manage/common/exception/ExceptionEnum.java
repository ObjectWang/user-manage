package org.example.user.manage.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
//    账号被停用(100001)、账号被锁定(100002)、密码错误(100003)、工号不存在(100004)、工号重复(100005)、
//    认证失败（AUTHENTICATION_FAILED）、授权失败（AUTHORIZATION_FAILED）、
//    角色名称重复(100011)、组别名称重复(100021)、
//    请求方式异常（400001）、参数校验异常（400002）、用户处于会话中（400101）、
//    默认话术重复(500001)
//    常用语重复(600001)
//    未知异常（999999）

    SUCCESS("100000", "成功"),
    
    ACCOUNT_FORBIDDEN_ERROR("100001", "账户已停用，请联系管理员。"),

    ACCOUNT_LOCK_ERROR("100002", "账号被锁定"),

    PASSWORD_INCORRECT_ERROR("100003", "工号或密码错误"),

    WORK_ID_NOT_EXIST("100004", "工号或密码错误"),

    USER_NAME_EXIST("100005", "用户名重复"),

    AUTHENTICATION_FAILED("100006", "认证失败"),

    AUTHORIZATION_FAILED("100007", "授权失败"),

    USER_NOT_EXIST("100008", "用户不存在"),

    ROLE_NAME_EXIST("100011", "角色名称重复"),

    ROLE_NOT_EXIST("100012", "角色不存在"),

    GROUP_NAME_EXIST("100021", "组别名称重复"),

    GROUP_NOT_EXIST("100022", "组别不存在"),

    MANUAL_SERVICE_SWITCH_EXIST("200001", "人工服务强制开关已存在"),

    MANUAL_SERVICE_SWITCH_TIME_INVALID("200002", "开始时间不能选择过去时间"),

    FORBIDDEN_WORD_EMPTY("300001", "内容不能为空"),

    FORBIDDEN_WORD_REPEATED("300002", "关键词已重复"),

    FORBIDDEN_WORD_EXISTED("300003", "以上词组已配置为敏感词/违禁词，请检查后重新录入"),

    FORBIDDEN_WORD_LIMIT_EXCEED("300004","单个词组长度不得超过20个汉字，请修改后重新输入。"),

    METHOD_NOT_ALLOWED("400001", "请求方式异常"),

    VALIDATION_ERROR("400002", "参数校验异常"),

    USER_IN_SESSION("400101", "用户处于会话中"),

    PIC_GET_ERROR("400201","验证码图片获取失败"),

    INTERNAL_SERVER_ERROR("435009", "未知异常"),

    DEFAULT_REPLY_EXIST("500001", "系统仅支持新增一条匹配方式为默认的自动回复话术"),

    GREETING_EXIST("600001","该常用语已存在"),

    LEAVE_MESSAGE_SWITCH_EXIST("700001", "留言强制开关已存在"),

    LEAVE_MESSAGE_SWITCH_TIME_INVALID("700002", "开始时间不能选择过去时间"),

    FAQ_EXIST("800001", "常见问题标题重复"),

    UNKNOW_ERROR("999999", "未知异常"),
    
    UNKNOW_PHONE_NO("200001", "手机号码不正确"),

    CAPTCHA_ERROR("200010", "验证错误"),

    FAIL("100009","失败"),

    LEAVE_TODAY_EXIST("100023","留言重复"),

    NOTICE_TYPE_NOT_EXIST("100025", "公告类型已删除，请修改公告类型。"),

    NOTICE_REVOKED("100027", "该公告已被撤回"),

    FILE_NOT_EXIST("100028", "文件不存在或已被删除"),

    NO_DATA_FOUND("99908","暂无数据"),
    ;

    private final String resultCode;
    private final String resultMsg;

    ExceptionEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

}
