package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.user.manage.domain.dto.RoleDto;
import org.example.user.manage.domain.dto.RolePageRequestDto;
import org.example.user.manage.domain.po.Role;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface RoleMapper {

    List<RoleDto> selectAll(RolePageRequestDto rolePageRequestDto);

    int insert(Role role);

    int deleteById(Long id);

    void update(Role role);

    Role selectById(Long id);

    List<Role> selectList();

    Role selectByName(String name);

    List<Role> selectByIds(@Param("roleIds") Long[] roleIds);
}
