package org.example.user.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.dto.RoleAddDto;
import org.example.user.manage.domain.dto.RoleDto;
import org.example.user.manage.domain.dto.RoleEditDto;
import org.example.user.manage.domain.dto.RolePageRequestDto;
import org.example.user.manage.domain.po.Role;
import org.example.user.manage.domain.vo.RoleVo;


import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
public interface RoleService {

    PageInfo<RolePageResultBo> selectRoleAll(RolePageRequestBo rolePageRequestBo);

    void insertRole(RoleAddBo roleAddBo);

    void deleteById(Long id);

    void update(RoleEditBo roleEditBo);

    RoleBo selectById(Long id);

    List<Role> selectList();

    boolean checkNameUnique(String name);

    boolean checkNameUnique(Long id, String name);

    boolean checkRoleExist(Long id);

    boolean checkRolesExist(Long[] roleIds);
}
