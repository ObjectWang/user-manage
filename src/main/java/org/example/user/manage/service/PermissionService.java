package org.example.user.manage.service;


import org.example.user.manage.domain.bo.MenuBo;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
public interface PermissionService {

    List<MenuBo> selectPermissionAll();
}
