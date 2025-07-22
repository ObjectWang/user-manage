package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.user.manage.domain.dto.GroupDto;
import org.example.user.manage.domain.po.Group;
import org.example.user.manage.domain.po.UserGroupRel;
import org.example.user.manage.domain.vo.GroupDisVo;
import org.example.user.manage.domain.vo.GroupVo;
import org.example.user.manage.domain.vo.UserInfoVo;

import java.util.List;

/**
 * <p>
 * 用户组表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface UserGroupRelMapper {

    void insertMember(@Param("userId") Long userId, @Param("groupId") Long groupId);

    void deleteByUserId(Long userId);

    GroupDisVo selectByUserId(Long userId);

    int batchInsert(@Param("userGroups") List<UserGroupRel> userGroups);

    void deleteByGroupId(Long group);

    List<UserInfoVo> selectUngrouped();

    List<GroupDto> selectByGroupId(Long groupId);

    List<UserInfoVo> selectGrouped();

    List<UserGroupRel> isLeader(List<Long> id);

    List<UserGroupRel> isLeaderOne(Long id);

    UserGroupRel selectOne(Long id);
}
