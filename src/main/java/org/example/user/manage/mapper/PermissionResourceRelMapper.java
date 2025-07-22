package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.user.manage.domain.dto.PermissionDto;

import java.util.List;

/**
 * <p>
 * 权限资源表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface PermissionResourceRelMapper {


    List<PermissionDto> selectPermissionByMenuId(Long menuId);
}
