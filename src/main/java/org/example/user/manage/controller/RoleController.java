package org.example.user.manage.controller;

import com.github.pagehelper.PageInfo;

import org.example.user.manage.common.exception.ExceptionEnum;
import org.example.user.manage.common.result.ResultResponse;
import org.example.user.manage.common.utils.PageUtil;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.param.RoleAddParam;
import org.example.user.manage.domain.param.RoleEditParam;
import org.example.user.manage.domain.param.RolePageRequestParam;
import org.example.user.manage.domain.po.Role;
import org.example.user.manage.domain.vo.RoleInfoVo;
import org.example.user.manage.domain.vo.RoleVo;
import org.example.user.manage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@RestController
@RequestMapping("/manage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    public ResultResponse<PageInfo<RoleVo>> selectPageList(@RequestBody RolePageRequestParam rolePageRequestParam) {

        RolePageRequestBo rolePageRequestBo = UserConvert.INSTANCE.convertRolePageRequestParam2Dto(rolePageRequestParam);

        // 获取分页数据
        PageInfo<RolePageResultBo> bos = roleService.selectRoleAll(rolePageRequestBo);

        PageInfo<RoleVo> vos = PageUtil.PageInfo2PageInfoVo(bos);

        return ResultResponse.success(vos);
    }

    @PostMapping("/all")
    public ResultResponse<List<RoleInfoVo>> selectList() {

        List<Role> roles = roleService.selectList();
        //2vo
        List<RoleInfoVo> vos = roles.stream()
                .map(UserConvert.INSTANCE::convertRole2RoleInfoVo)
                .collect(Collectors.toList());

        return ResultResponse.success(vos);
    }


    /**
     * 添加角色
     *
     * @param roleAddDto
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResultResponse insert(@RequestBody @Validated RoleAddParam roleAddParam, HttpServletRequest request) {

        RoleAddBo roleAddBo = UserConvert.INSTANCE.convertRoleAddParam2Dto(roleAddParam);
        //检查角色名是否重复
        if (!roleService.checkNameUnique(roleAddParam.getName())) {
            return ResultResponse.error(ExceptionEnum.ROLE_NAME_EXIST);
        }
        // 添加
        roleService.insertRole(roleAddBo);

        return ResultResponse.success();
    }

    /**
     * 删除角色
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/remove")
    public ResultResponse deleteById(@NotNull Long id, HttpServletRequest request) {

        // 删除
        roleService.deleteById(id);

        return ResultResponse.success();
    }

    /**
     * 修改角色
     *
     * @param roleEditParam
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public ResultResponse update(@RequestBody @Validated RoleEditParam roleEditParam, HttpServletRequest request) {

        RoleEditBo roleEditBo = UserConvert.INSTANCE.convertRoleEditParam2Bo(roleEditParam);
        // 检查角色是否存在
        if (!roleService.checkRoleExist(roleEditParam.getId())) {
            return ResultResponse.error(ExceptionEnum.ROLE_NOT_EXIST);
        }
        //检查角色名是否重复
        if (!roleService.checkNameUnique(roleEditParam.getId(), roleEditParam.getName())) {
            return ResultResponse.error(ExceptionEnum.ROLE_NAME_EXIST);
        }
        //修改角色
        roleService.update(roleEditBo);

        return ResultResponse.success();
    }

    /**
     * 查询角色
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/get")
    public ResultResponse selectById(@NotNull Long id, HttpServletRequest request) {

        RoleBo roleBo = roleService.selectById(id);

        RoleVo roleVo = UserConvert.INSTANCE.convertRoleBo2Vo(roleBo);

        return ResultResponse.success(roleVo);
    }

}
