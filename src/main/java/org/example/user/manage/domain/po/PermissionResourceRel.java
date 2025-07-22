package org.example.user.manage.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限资源表
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Data
public class PermissionResourceRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 资源类型 2：元素 1：菜单 0：url
     */
    private Integer resourceType;

    /**
     * 资源Id
     */
    private Long resourceId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
