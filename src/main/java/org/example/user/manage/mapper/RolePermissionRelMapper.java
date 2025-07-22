package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.user.manage.domain.po.RolePermissionRel;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface RolePermissionRelMapper {

    void deleteByRoleId(Long roleId);

    void batchInsert(@Param("roleId") Long roleId, @Param("permissionIds") Long[] permissionIds);

    List<RolePermissionRel> selectByRoleId(Long roleId);
}
