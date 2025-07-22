package org.example.user.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.dto.GroupAddDto;
import org.example.user.manage.domain.dto.GroupEditDto;
import org.example.user.manage.domain.dto.GroupPageRequestDto;
import org.example.user.manage.domain.po.Group;
import org.example.user.manage.domain.vo.GroupPageResultVo;
import org.example.user.manage.domain.vo.GroupVo;


import java.util.List;

/**
 * <p>
 * 组别表 服务类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
public interface GroupService {

    PageInfo<GroupPageResultBo> selectGroupAll(GroupPageRequestBo groupPageRequestBo);

    void insertGroup(GroupAddBo groupAddBo);

    void deleteById(Long id);

    void update(GroupEditBo groupEditBo);

    List<GroupBo> selectById(Long id);

    List<Group> selectList();

    boolean checkNameUnique(String name);

    boolean checkNameUnique(Long id, String name);

    boolean checkGroupExist(Long id);

    List<GroupMemberBo> selectMember();

}
