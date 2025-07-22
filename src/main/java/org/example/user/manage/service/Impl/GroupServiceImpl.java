package org.example.user.manage.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.example.user.manage.common.constant.UserConstant;
import org.example.user.manage.common.utils.PageUtil;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.dto.*;
import org.example.user.manage.domain.po.Group;
import org.example.user.manage.domain.po.UserGroupRel;
import org.example.user.manage.domain.vo.GroupPageResultVo;
import org.example.user.manage.domain.vo.GroupVo;
import org.example.user.manage.domain.vo.UserInfoVo;
import org.example.user.manage.domain.vo.UserPageResultVo;
import org.example.user.manage.mapper.GroupMapper;
import org.example.user.manage.mapper.GroupPermissionRelMapper;
import org.example.user.manage.mapper.GroupRoleRelMapper;
import org.example.user.manage.mapper.UserGroupRelMapper;
import org.example.user.manage.service.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 组别表 服务实现类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserGroupRelMapper userGroupRelMapper;

    @Autowired
    private GroupPermissionRelMapper groupPermissionRelMapper;

    @Autowired
    private GroupRoleRelMapper groupRoleRelMapper;

    @Override
    public PageInfo<GroupPageResultBo> selectGroupAll(GroupPageRequestBo groupPageRequestBo) {
        groupPageRequestBo.check();
        PageHelper.startPage(groupPageRequestBo.getPageNum(), groupPageRequestBo.getPageSize());

        GroupPageRequestDto groupPageRequestDto = UserConvert.INSTANCE.convertGroupPageRequestBo2Dto(groupPageRequestBo);

        List<GroupPageResultDto> groupPageResultDtos = groupMapper.selectGroupAll(groupPageRequestDto);
        PageInfo<GroupPageResultDto> dtos = new PageInfo<>(groupPageResultDtos);

        PageInfo<GroupPageResultBo> bos = PageUtil.PageInfoDto2PageInfoBo(dtos);
        return bos;
    }

    @Override
    public void insertGroup(GroupAddBo groupAddBo) {
        // 插入组别表
        Group group = UserConvert.INSTANCE.convertGroupAddBo2Po(groupAddBo);
        group.setCreateBy(1L);
        group.setUpdateBy(1L);
        LocalDateTime now = LocalDateTime.now();
        group.setCreateTime(now);
        group.setUpdateTime(now);
        group.setIsDelete(UserConstant.NOT_DELETED);
        groupMapper.insertGroup(group);

        // 插入用户组表
        if (!CollectionUtils.isEmpty(groupAddBo.getMembers())) {
            List<UserGroupRel> userGroups = groupAddBo.getMembers().stream()
                    .map(o -> {
                        UserGroupRel userGroupRel = new UserGroupRel();
                        userGroupRel.setUserId(o);
                        userGroupRel.setGroupId(group.getId());
                        if (Objects.equals(groupAddBo.getLeader(), o)) {
                            userGroupRel.setLevel(1);
                        } else {
                            userGroupRel.setLevel(0);
                        }
                        return userGroupRel;
                    }).collect(Collectors.toList());
            userGroupRelMapper.batchInsert(userGroups);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        // 删除用户组别关系
        userGroupRelMapper.deleteByGroupId(id);
        // 删除组别权限关系
        groupPermissionRelMapper.deleteByGroupId(id);
        // 删除角色组别关系
        groupRoleRelMapper.deleteByGroupId(id);

        groupMapper.deleteById(id);
    }

    @Override
    public void update(GroupEditBo groupEditBo) {
        // 修改组别表
        Group group = new Group();
        BeanUtils.copyProperties(groupEditBo, group);
        //获取用户信息
        group.setUpdateBy(1L);
        group.setUpdateTime(LocalDateTime.now());
        groupMapper.update(group);

        // 删除用户组关系后插入用户组表
        userGroupRelMapper.deleteByGroupId(groupEditBo.getId());
        if (!CollectionUtils.isEmpty(groupEditBo.getMembers())) {
            List<UserGroupRel> userGroups = groupEditBo.getMembers().stream()
                    .map(o -> {
                        UserGroupRel userGroupRel = new UserGroupRel();
                        userGroupRel.setUserId(o);
                        userGroupRel.setGroupId(group.getId());
                        if (Objects.equals(groupEditBo.getLeader(), o)) {
                            userGroupRel.setLevel(1);
                        } else {
                            userGroupRel.setLevel(0);
                        }
                        return userGroupRel;
                    }).collect(Collectors.toList());
            userGroupRelMapper.batchInsert(userGroups);
        }
    }

    @Override
    public List<GroupBo> selectById(Long id) {

        List<GroupDto> groupDtos = userGroupRelMapper.selectByGroupId(id);

        List<GroupBo> bos = groupDtos.stream()
                .map(UserConvert.INSTANCE::convertGroupDto2Bo)
                .collect(Collectors.toList());

        return bos;

    }

    @Override
    public List<Group> selectList() {

        return groupMapper.selectList();
    }

    @Override
    public boolean checkNameUnique(String name) {
        Group group = groupMapper.selectByName(name);
        return group == null;
    }

    @Override
    public boolean checkNameUnique(Long id, String name) {
        Group group = groupMapper.selectByName(name);
        return group == null || group.getId().longValue() == id.longValue();
    }

    @Override
    public boolean checkGroupExist(Long id) {
        Group group = groupMapper.selectGroupById(id);
        // 如果是已删除组别,返回不存在
        if (group == null || group.getIsDelete().equals(UserConstant.IS_DELETED)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<GroupMemberBo> selectMember() {
        List<GroupMemberBo> res = new ArrayList<>();

        List<GroupMemberDto> memberRelations = groupMapper.selectAllMember();

        Map<String, List<GroupMemberDto>> collect = memberRelations.stream().collect(Collectors.groupingBy(GroupMemberDto::getGroupName));

        collect.forEach((k, v) -> {
            GroupMemberBo bo = new GroupMemberBo();
            bo.setGroupName(k);
            List<UserInfoVo> userInfoVoList = v.stream().map(o -> {
                UserInfoVo vo = new UserInfoVo();
                vo.setId(o.getId());
                vo.setNickName(o.getNickName());
                vo.setWorkId(o.getWorkId());
                bo.setGroupId(o.getGroupId());
                vo.setStatus(o.getStatus());
                return vo;
            }).collect(Collectors.toList());
            BeanUtils.copyProperties(v, bo);
            bo.setMembers(userInfoVoList);
            res.add(bo);
        });


        return res;
    }
}
