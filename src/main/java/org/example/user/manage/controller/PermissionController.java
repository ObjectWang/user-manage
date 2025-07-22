package org.example.user.manage.controller;


import org.example.user.manage.domain.bo.MenuBo;
import org.example.user.manage.common.result.ResultResponse;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.vo.MenuVo;
import org.example.user.manage.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@RestController
@RequestMapping("/manage/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/list")
    public ResultResponse selectPermissionAll() {

        List<MenuBo> menuBos = permissionService.selectPermissionAll();

        List<MenuVo> vos = menuBos.stream()
                .map(UserConvert.INSTANCE::convertMenuBo2Vo)
                .collect(Collectors.toList());

        return ResultResponse.success(vos);
    }

}
