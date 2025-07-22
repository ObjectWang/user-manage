package org.example.user.manage.controller;

import com.github.pagehelper.PageInfo;
import org.example.user.manage.common.utils.PageUtil;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.dto.*;
import org.example.user.manage.domain.param.LoginParam;
import org.example.user.manage.domain.param.UserAddParam;
import org.example.user.manage.domain.param.UserEditParam;
import org.example.user.manage.domain.param.UserPageRequestParam;
import org.example.user.manage.domain.vo.LoginResultVo;
import org.example.user.manage.domain.vo.UserPageResultVo;
import org.example.user.manage.domain.vo.UserVo;
import org.example.user.manage.common.exception.ExceptionEnum;
import org.example.user.manage.common.result.ResultResponse;
import org.example.user.manage.service.GroupService;
import org.example.user.manage.service.RoleService;
import org.example.user.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@RestController
@Validated
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GroupService groupService;

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     */
    @PostMapping("/manage/user/login")
    public ResultResponse login(@Validated @RequestBody LoginParam loginParam) {

        LoginBo loginBo = UserConvert.INSTANCE.convertLoginParam2Dto(loginParam);

        LoginResultBo loginResultBo = userService.login(loginBo);

        LoginResultVo loginResultVo = UserConvert.INSTANCE.convertLoginResultBo2Vo(loginResultBo);

        return ResultResponse.success(loginResultVo);
    }

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping("/manage/user/logout")
    public ResultResponse logout() {

        Boolean logout = userService.logout();
        if (!logout) {
            return ResultResponse.error("当前无法登出");
        }

        return ResultResponse.success();
    }

    /**
     * 获取用户列表
     *
     * @return userVos
     */
    @PostMapping("/manage/user/list")
    public ResultResponse<PageInfo<UserPageResultVo>> selectPageList(@RequestBody UserPageRequestParam userPageRequestParam) {
        UserPageRequestBo bo = UserConvert.INSTANCE.convertUserPageRequestParam2Bo(userPageRequestParam);

        // 获取分页数据
        PageInfo<UserPageResultBo> bos = userService.selectUserAll(bo);

        //bo2vo
        PageInfo<UserPageResultVo> vo = PageUtil.PageInfo2PageInfoVo(bos);

        return ResultResponse.success(vo);
    }


    /**
     * 添加用户
     *
     * @param userAddParam
     * @param request
     * @return
     */
    @PostMapping("/manage/user/add")
    public ResultResponse insert(@RequestBody @Validated UserAddParam userAddParam, HttpServletRequest request) {

        // 检查用户名是否重复
        if (!userService.checkUserNameUnique(userAddParam.getUserName())) {
            return ResultResponse.error(ExceptionEnum.USER_NAME_EXIST);
        }
        // 检查角色是否存在
        if (!roleService.checkRolesExist(userAddParam.getRoleIds())) {
            return ResultResponse.error(ExceptionEnum.ROLE_NOT_EXIST);
        }
        // 检查组别是否存在
        if (userAddParam.getGroupId() != null && !groupService.checkGroupExist(userAddParam.getGroupId())) {
            return ResultResponse.error(ExceptionEnum.GROUP_NOT_EXIST);
        }
        // param2bo
        UserAddBo userAddBo = UserConvert.INSTANCE.userAddParam2bo(userAddParam);
        // 添加用户
        userService.insertUser(userAddBo);

        return ResultResponse.success();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @PostMapping("/manage/user/remove")
    public ResultResponse deleteById(@NotNull Long id) {

        userService.deleteUserById(id);

        return ResultResponse.success();
    }

    /**
     * 修改用户
     *
     * @param userEditParam
     * @return
     */
    @PostMapping("/manage/user/edit")
    public ResultResponse update(@RequestBody @Validated UserEditParam userEditParam) {

        // 检查用户是否存在
        if (!userService.checkUserExist(userEditParam.getId())) {
            return ResultResponse.error(ExceptionEnum.USER_NOT_EXIST);
        }
        // 检查角色是否存在
        if (!roleService.checkRolesExist(userEditParam.getRoleIds())) {
            return ResultResponse.error(ExceptionEnum.ROLE_NOT_EXIST);
        }
        // 检查组别是否存在
        if (userEditParam.getGroupId() != null && !groupService.checkGroupExist(userEditParam.getGroupId())) {
            return ResultResponse.error(ExceptionEnum.GROUP_NOT_EXIST);
        }

        UserEditBo userEditBo = UserConvert.INSTANCE.convertUserEditParam2Bo(userEditParam);

        // 修改
        userService.updateUser(userEditBo);

        return ResultResponse.success();
    }

    /**
     * 查询用户
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/manage/user/get")
    public ResultResponse<UserVo> selectById(@NotNull Long id, HttpServletRequest request) {

        UserBo userBo = userService.selectById(id);

        UserVo userVo = UserConvert.INSTANCE.convertUserBo2Vo(userBo);

        return ResultResponse.success(userVo);
    }


    /**
     * 启用用户
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/manage/user/start")
    public ResultResponse start(@NotNull Long id, HttpServletRequest request) {

        userService.start(id);

        return ResultResponse.success();
    }

    /**
     * 停用用户
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/manage/user/stop")
    public ResultResponse stop(@NotNull Long id, HttpServletRequest request) {

        userService.stop(id);

        return ResultResponse.success();
    }

    /**
     * 重置密码
     *
     * @param pwdDto
     * @param request
     * @return
     */
    @PostMapping("/manage/user/resetPwd")
    public ResultResponse resetPwd(@RequestBody @Validated PwdDto pwdDto, HttpServletRequest request) {
        String msg = userService.resetPwd(pwdDto, request);

        if (msg == null) {
            return ResultResponse.success();
        } else {
            return ResultResponse.error(msg);
        }
    }

}
