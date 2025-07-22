package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.user.manage.domain.dto.GroupMemberDto;
import org.example.user.manage.domain.dto.GroupPageRequestDto;
import org.example.user.manage.domain.dto.GroupPageResultDto;
import org.example.user.manage.domain.po.Group;
import org.example.user.manage.domain.vo.GroupPageResultVo;
import org.example.user.manage.domain.vo.GroupVo;

import java.util.List;

/**
 * <p>
 * 组别表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface GroupMapper {

    List<GroupPageResultDto> selectGroupAll(GroupPageRequestDto groupPageRequestDto);

    int insertGroup(Group group);

    int deleteById(Long id);

    GroupVo selectById(Long id);

    List<Group> selectList();

    Group selectByName(String name);

    int update(Group group);

    Group selectGroupById(Long id);

    List<GroupMemberDto> selectAllMember();
}
