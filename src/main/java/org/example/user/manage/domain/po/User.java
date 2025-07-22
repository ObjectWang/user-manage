package org.example.user.manage.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 工号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 1：男；0：女
     */
    private Integer sex;

    /**
     * 婚姻状况 2：离异 1：已婚；0：未婚
     */
    private Integer marital;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 状态 1：启用 0：禁用
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除 1：已删除；0：未删除
     */
    private Integer isDelete;

}
