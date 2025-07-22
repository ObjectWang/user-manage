package org.example.user.manage.controller;

import com.github.pagehelper.PageInfo;
import org.example.user.manage.common.exception.ExceptionEnum;
import org.example.user.manage.common.result.ResultResponse;
import org.example.user.manage.common.utils.PageUtil;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.param.GroupAddParam;
import org.example.user.manage.domain.param.GroupEditParam;
import org.example.user.manage.domain.param.GroupPageRequestParam;
import org.example.user.manage.domain.po.Group;
import org.example.user.manage.domain.vo.*;
import org.example.user.manage.service.GroupService;
import org.example.user.manage.service.UserService;
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
 * 组别表 前端控制器
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@RestController
@RequestMapping("/manage/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public ResultResponse<PageInfo<GroupPageResultVo>> selectPageList(@RequestBody GroupPageRequestParam groupPageRequestParam) {


        GroupPageRequestBo groupPageRequestBo = UserConvert.INSTANCE.convertGroupPageRequestParam2Bo(groupPageRequestParam);

        // 获取分页数据
        PageInfo<GroupPageResultBo> bos = groupService.selectGroupAll(groupPageRequestBo);

        PageInfo<GroupPageResultVo> vos = PageUtil.PageInfo2PageInfoVo(bos);

        return ResultResponse.success(vos);
    }

    @PostMapping("/all")
    public ResultResponse<List<GroupInfoVo>> selectList() {

        List<Group> groups = groupService.selectList();

        List<GroupInfoVo> vos = groups.stream()
                .map(UserConvert.INSTANCE::convertGroup2GroupInfoVo)
                .collect(Collectors.toList());

        return ResultResponse.success(vos);
    }

    @PostMapping("/member")
    public ResultResponse<List<GroupMemberVo>> selectMember() {

        List<GroupMemberBo> bos = groupService.selectMember();

        List<GroupMemberVo> res = bos.stream()
                .map(UserConvert.INSTANCE::convertGroupMemberBo2Vo)
                .collect(Collectors.toList());

        //未分组人员也算一组
        GroupMemberVo ungrouped = new GroupMemberVo();
        ungrouped.setGroupId(-1L);
        ungrouped.setMembers(userService.selectUngrouped());
        res.add(ungrouped);

        return ResultResponse.success(res);
    }


    /**
     * 添加组
     *
     * @param groupAddParam
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResultResponse insertGroup(@RequestBody @Validated GroupAddParam groupAddParam, HttpServletRequest request) {

        GroupAddBo groupAddBo = UserConvert.INSTANCE.convertGroupAddParam2Bo(groupAddParam);

        //检查组名是否重复
        if (!groupService.checkNameUnique(groupAddParam.getName())) {
            return ResultResponse.error(ExceptionEnum.GROUP_NAME_EXIST);
        }

        // 添加组
        groupService.insertGroup(groupAddBo);

        return ResultResponse.success();
    }

    /**
     * 删除组
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/remove")
    public ResultResponse deleteById(@NotNull Long id, HttpServletRequest request) {

        groupService.deleteById(id);

        return ResultResponse.success();
    }

    /**
     * 修改组
     *
     * @param groupEditParam
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public ResultResponse updateGroup(@RequestBody @Validated GroupEditParam groupEditParam, HttpServletRequest request) {

        GroupEditBo groupEditBo = UserConvert.INSTANCE.convertGroupEditParam2Bo(groupEditParam);

        // 检查组别是否存在
        if (!groupService.checkGroupExist(groupEditParam.getId())) {
            return ResultResponse.error(ExceptionEnum.GROUP_NOT_EXIST);
        }

        //检查组名是否重复
        if (!groupService.checkNameUnique(groupEditParam.getId(), groupEditParam.getName())) {
            return ResultResponse.error(ExceptionEnum.GROUP_NAME_EXIST);
        }

        //修改组别
        groupService.update(groupEditBo);

        return ResultResponse.success();
    }

    /**
     * 查询组
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/get")
    public ResultResponse<List<GroupVo>> selectById(@NotNull Long id, HttpServletRequest request) {

        List<GroupBo> bos = groupService.selectById(id);

        List<GroupVo> vos = bos.stream()
                .map(UserConvert.INSTANCE::convertGroupBo2Vo)
                .collect(Collectors.toList());

        return ResultResponse.success(vos);
    }

}
