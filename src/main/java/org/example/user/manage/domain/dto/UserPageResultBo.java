package org.example.user.manage.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserPageResultBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long userId;

    /**
     * 工号
     */
    private String workId;

    /**
     * 姓名
     */
    private String nickName;

    private String group;

    private String role;

    /**
     * 状态 1：启用 0：禁用
     */
    private Integer status;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
