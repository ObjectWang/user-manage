package org.example.user.manage.service.Impl;



import org.example.user.manage.common.constant.UserConstant;
import org.example.user.manage.domain.bo.MenuBo;
import org.example.user.manage.domain.po.Menu;
import org.example.user.manage.domain.dto.PermissionDto;
import org.example.user.manage.mapper.MenuMapper;
import org.example.user.manage.mapper.PermissionMapper;
import org.example.user.manage.mapper.PermissionResourceRelMapper;
import org.example.user.manage.service.PermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private PermissionResourceRelMapper permissionResourceRelMapper;

    @Override
    public List<MenuBo> selectPermissionAll() {
        // 声明
        List<Menu> menus = menuMapper.selectList();
        // 获取菜单树
        List<MenuBo> menuBos = transferMenu(menus, 0L);
        // 对其中所有叶子节点查询权限
        for (MenuBo menuBo : menuBos){
            if (menuBo.getNodeType().equals(UserConstant.LEAF_MENU)){
                List<PermissionDto> permissions = permissionResourceRelMapper.selectPermissionByMenuId(menuBo.getMenuId());
                menuBo.setChildren(permissions);
            }
        }

        return menuBos;
    }

    private List<Menu> selectAllMenu() {
        List<Menu> menus = menuMapper.selectList();
        return menus;
    }

    private List<MenuBo> transferMenu(List<Menu> allMenu, Long parentId) {
        List<MenuBo> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(allMenu)) {
            for (Menu source : allMenu){
                if (parentId.longValue() == source.getParentId().longValue()){
                    MenuBo menuBo = new MenuBo();
                    BeanUtils.copyProperties(source, menuBo);
                    menuBo.setMenuName(source.getName());
                    menuBo.setMenuId(source.getId());
                    // 对其中所有叶子节点查询权限
                    if (source.getNodeType().equals(UserConstant.LEAF_MENU)){
                        List<PermissionDto> permissions = permissionResourceRelMapper.selectPermissionByMenuId(menuBo.getMenuId());
                        menuBo.setChildren(permissions);
                    }else {
                        //递归查询
                        List<MenuBo> childList  = transferMenu(allMenu, source.getId());
                        if (!CollectionUtils.isEmpty(childList)){
                            menuBo.setChildMenus(childList);
                        }
                    }
                    resultList.add(menuBo);
                }
            }
        }
        return resultList;
    }
}
