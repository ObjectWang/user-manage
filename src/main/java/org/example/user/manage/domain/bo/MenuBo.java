package org.example.user.manage.domain.bo;


import lombok.Data;
import org.example.user.manage.domain.dto.PermissionDto;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单层级 1文件夹，2页面
     */
    private Integer nodeType;

    /**
     * 子菜单
     */
    private List<MenuBo> childMenus;

    /**
     * 权限
     */
    private List<PermissionDto> children;

}
