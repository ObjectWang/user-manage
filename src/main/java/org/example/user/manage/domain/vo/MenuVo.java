package org.example.user.manage.domain.vo;

import lombok.Data;
import org.example.user.manage.domain.dto.PermissionDto;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVo implements Serializable {

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
     * 子菜单
     */
    private List<MenuVo> childMenus;

    /**
     * 权限
     */
    private List<PermissionDto> children;

}
