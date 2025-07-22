package org.example.user.manage.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.ArrayUtils;
import org.example.user.manage.common.constant.UserConstant;
import org.example.user.manage.common.utils.PageUtil;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.dto.RoleAddDto;
import org.example.user.manage.domain.dto.RoleDto;
import org.example.user.manage.domain.dto.RoleEditDto;
import org.example.user.manage.domain.dto.RolePageRequestDto;
import org.example.user.manage.domain.po.Role;
import org.example.user.manage.domain.po.RolePermissionRel;
import org.example.user.manage.domain.vo.RoleVo;
import org.example.user.manage.mapper.GroupRoleRelMapper;
import org.example.user.manage.mapper.RoleMapper;
import org.example.user.manage.mapper.RolePermissionRelMapper;
import org.example.user.manage.mapper.UserRoleRelMapper;
import org.example.user.manage.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleRelMapper userRoleRelMapper;

    @Autowired
    private RolePermissionRelMapper rolePermissionRelMapper;

    @Autowired
    private GroupRoleRelMapper groupRoleRelMapper;

    @Override
    public PageInfo<RolePageResultBo> selectRoleAll(RolePageRequestBo rolePageRequestBo) {

        rolePageRequestBo.check();
        PageHelper.startPage(rolePageRequestBo.getPageNum(), rolePageRequestBo.getPageSize());

        RolePageRequestDto rolePageRequestDto = UserConvert.INSTANCE.convertRolePageRequestBo2Dto(rolePageRequestBo);

        List<RoleDto> roles = roleMapper.selectAll(rolePageRequestDto);
        PageInfo<RoleDto> dtos = new PageInfo<>(roles);

        PageInfo<RolePageResultBo> bos = PageUtil.PageInfoDto2PageInfoBo(dtos);

        return bos;
    }

    @Transactional
    @Override
    public void insertRole(RoleAddBo roleAddBo) {
        //插入角色表
        Role role = UserConvert.INSTANCE.convertRoleAddBo2Po(roleAddBo);
        //获取用户信息
        role.setCreateBy(1L);
        role.setUpdateBy(1L);
        LocalDateTime now = LocalDateTime.now();
        role.setCreateTime(now);
        role.setUpdateTime(now);
        roleMapper.insert(role);
        //插入角色权限表
        if (roleAddBo.getPermissionIds() != null && roleAddBo.getPermissionIds().length > 0) {
            rolePermissionRelMapper.batchInsert(role.getId(), roleAddBo.getPermissionIds());
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        // 删除用户角色关系
        userRoleRelMapper.deleteByRoleId(id);
        // 删除角色权限关系
        rolePermissionRelMapper.deleteByRoleId(id);
        // 删除组角色关系
        groupRoleRelMapper.deleteByRoleId(id);

        roleMapper.deleteById(id);
    }

    @Override
    public void update(RoleEditBo roleEditBo) {
        // 修改角色表
        Role role = UserConvert.INSTANCE.convertRoleEditBo2Po(roleEditBo);
        //获取用户信息
        role.setUpdateBy(1L);
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.update(role);

        // 删除之前角色权限关系后插入角色权限关系
        rolePermissionRelMapper.deleteByRoleId(roleEditBo.getId());
        if (!ArrayUtils.isEmpty(roleEditBo.getPermissionIds())) {
            rolePermissionRelMapper.batchInsert(role.getId(), roleEditBo.getPermissionIds());
        }
    }

    @Override
    public RoleBo selectById(Long id) {
        // 1.Role
        Role role = roleMapper.selectById(id);
        // 2.Vo
        RoleBo roleBo = UserConvert.INSTANCE.convertRolePo2Bo(role);
        // 3.获取对应的权限
        List<RolePermissionRel> rolePermissions = rolePermissionRelMapper.selectByRoleId(id);
        List<Long> permissionIds = rolePermissions.stream().
                map(RolePermissionRel::getPermissionId)
                .collect(Collectors.toList());
        // 4.组装
        roleBo.setPermissionIds(permissionIds);

        return roleBo;
    }

    @Override
    public List<Role> selectList() {
        return roleMapper.selectList();
    }

    @Override
    public boolean checkNameUnique(String name) {
        Role role = roleMapper.selectByName(name);
        return role == null;
    }

    @Override
    public boolean checkNameUnique(Long id, String name) {
        Role role = roleMapper.selectByName(name);
        return role == null || role.getId().longValue() == id.longValue();
    }

    @Override
    public boolean checkRoleExist(Long id) {
        Role role = roleMapper.selectById(id);
        // 如果是已删除角色,返回角色不存在
        if (role == null || role.getIsDelete().equals(UserConstant.IS_DELETED)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkRolesExist(Long[] roleIds) {
        List<Role> roles = roleMapper.selectByIds(roleIds);
        return !CollectionUtils.isEmpty(roles);
    }
}
