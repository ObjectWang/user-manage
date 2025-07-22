package org.example.user.manage.domain.po;

import java.util.Date;

/**
 * 组角色表
 */
public class GroupRoleRel {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

    private Long groupId;

    private Long roleId;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}