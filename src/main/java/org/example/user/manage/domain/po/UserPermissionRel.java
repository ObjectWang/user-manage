package org.example.user.manage.domain.po;

import java.util.Date;

/**
 * 用户权限表
 */
public class UserPermissionRel {
    /**
    * 主键
    */
    private Long id;

    private Long userId;

    private Long permissionId;

    /**
    * 权限类型（0:可访问，1:可授权）
    */
    private Short permissionType;

    /**
    * 创建时间
    */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Short getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Short permissionType) {
        this.permissionType = permissionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}