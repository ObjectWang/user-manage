package org.example.user.manage.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Data
public class RolePermissionRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 权限类型（0:可访问，1:可授权）
     */
    private Long permissionType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
