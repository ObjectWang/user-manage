package org.example.user.manage.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户组表
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Data
public class UserGroupRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组别id
     */
    private Long groupId;

    /**
     * 级别1：组长 0：组员
     */
    private Integer level;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
