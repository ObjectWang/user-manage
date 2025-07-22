package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.user.manage.domain.vo.RoleDisVo;

import java.util.List;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface UserRoleRelMapper {

    void batchInsert(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);

    void deleteByUserId(Long userId);

    List<RoleDisVo> selectByUserId(Long userId);

    void deleteByRoleId(Long id);
}
