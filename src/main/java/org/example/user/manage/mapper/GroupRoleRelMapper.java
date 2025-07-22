package org.example.user.manage.mapper;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Mapper;
import org.example.user.manage.domain.po.GroupRoleRel;

@Mapper
public interface GroupRoleRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupRoleRel record);

    int insertSelective(GroupRoleRel record);

    GroupRoleRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupRoleRel record);

    int updateByPrimaryKey(GroupRoleRel record);

    int deleteByGroupId(@Param("groupId")Long groupId);

    int deleteByRoleId(@Param("roleId")Long roleId);




}