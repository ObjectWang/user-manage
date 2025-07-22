package org.example.user.manage.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限
     */
    private List<Long> permissionIds;

}
