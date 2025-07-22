package org.example.user.manage.domain.po;

import java.util.Date;

/**
 * 组权限表
 */
public class GroupPermissionRel {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

    private Long groupId;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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