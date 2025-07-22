package org.example.user.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.user.manage.domain.po.GroupPermissionRel;

@Mapper
public interface GroupPermissionRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupPermissionRel record);

    int insertSelective(GroupPermissionRel record);

    GroupPermissionRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupPermissionRel record);

    int updateByPrimaryKey(GroupPermissionRel record);

    int deleteByGroupId(@Param("groupId")Long groupId);

}