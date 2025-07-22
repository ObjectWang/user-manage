package org.example.user.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.user.manage.domain.po.UserPermissionRel;

@Mapper
public interface UserPermissionRelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPermissionRel record);

    int insertSelective(UserPermissionRel record);

    UserPermissionRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPermissionRel record);

    int updateByPrimaryKey(UserPermissionRel record);
}