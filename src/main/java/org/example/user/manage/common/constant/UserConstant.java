package org.example.user.manage.common.constant;

public class UserConstant {

    /**
     * 用户状态启用
     */
    public static final int STATUS_START = 1;

    /**
     * 用户状态禁用
     */
    public static final int STATUS_STOP = 0;


    /**
     * 密码8-20位
     */
    public static final String PASSWORD_REGEX = "^[0-9A-Za-z!@#$%^&*(),.?\\\":{}|<>]{8,20}$";

    /**
     * token过期时间
     */
    public static final long TOKEN_EXPIRE_TIME = 1440L;

    /**
     * 已删除
     */
    public static final int IS_DELETED = 1;

    /**
     * 已删除
     */
    public static final int NOT_DELETED = 0;

    /**
     * 启用
     */
    public static final int NOT_BANNED = 1;

    /**
     * 停用
     */
    public static final int IS_BANNED = 0;

    /**
     * 根目录层级
     */
    public static final int ROOT_MENU = 0;

    /**
     * 非叶子节点菜单
     */
    public static final int PARENT_MENU = 1;

    /**
     * 叶子节点菜单
     */
    public static final int LEAF_MENU = 2;

}
