package org.example.user.manage.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.user.manage.common.constant.RedisConstant;
import org.example.user.manage.common.constant.UserConstant;
import org.example.user.manage.common.exception.BusinessException;
import org.example.user.manage.common.exception.ExceptionEnum;
import org.example.user.manage.common.utils.CryptUtil;
import org.example.user.manage.common.utils.PageUtil;
import org.example.user.manage.common.utils.RedisCache;
import org.example.user.manage.common.utils.UserHolder;
import org.example.user.manage.domain.LoginUser;
import org.example.user.manage.domain.UserInfo;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.convert.UserConvert;
import org.example.user.manage.domain.dto.*;
import org.example.user.manage.domain.po.User;
import org.example.user.manage.domain.po.UserGroupRel;
import org.example.user.manage.domain.vo.*;
import org.example.user.manage.mapper.UserGroupRelMapper;
import org.example.user.manage.mapper.UserMapper;
import org.example.user.manage.mapper.UserPermissionRelMapper;
import org.example.user.manage.mapper.UserRoleRelMapper;
import org.example.user.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.user.manage.common.constant.RedisConstant.LOGIN_USER_KEY;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelMapper userRoleRelMapper;

    @Autowired
    private UserGroupRelMapper userGroupRelMapper;

    @Autowired
    private UserPermissionRelMapper userPermissionRelMapper;

    @Autowired
    RedisCache redisCache;


    @Override
    public LoginResultBo login(LoginBo loginBo) {
        // 根据userName查询用户
        User user = userMapper.selectByWorkId(loginBo.getUserName());
        if (user == null) {
            throw new BusinessException("工号或密码错误", ExceptionEnum.WORK_ID_NOT_EXIST);
        }
        if (user.getStatus() == UserConstant.IS_BANNED) {
            throw new BusinessException(ExceptionEnum.ACCOUNT_FORBIDDEN_ERROR.getResultMsg(), ExceptionEnum.ACCOUNT_FORBIDDEN_ERROR);
        }
        // 比对密码,如果错误，返回异常
        String plain = null;
        try {
            plain = CryptUtil.base64Decrypt(loginBo.getPassword());
        } catch (Exception e) {
            throw new BusinessException("工号或密码错误", ExceptionEnum.PASSWORD_INCORRECT_ERROR);
        }
        if (!StringUtils.equals(CryptUtil.sm3Encrypt(plain), user.getPassword())) {
            throw new BusinessException("工号或密码错误", ExceptionEnum.PASSWORD_INCORRECT_ERROR);
        }
        //查询所有url存入loginUser中
        List<String> urls = userMapper.selectUrlById(user.getId());
        LoginUser loginUser = new LoginUser(user, urls);
        //生成token并保存
        String token = UUID.randomUUID().toString();
        //LoginUser存入redis
        redisCache.setCacheObject(LOGIN_USER_KEY + token, loginUser);
        //查询所有菜单
        List<String> menuIds = userMapper.selectMenuIdById(user.getId());
        //查询所有按钮
        List<Long> elementIds = userMapper.selectelementIdsById(user.getId());
        //把相应信息响应给前端
        LoginResultBo loginResultBo = new LoginResultBo();
        loginResultBo.setToken(token);
        loginResultBo.setNickName(user.getNickName());
        loginResultBo.setMenuIds(menuIds);
        loginResultBo.setUserId(user.getId());
        loginResultBo.setElementIds(elementIds);
        return loginResultBo;
    }

    @Override
    public Boolean logout() {
        //1.获取用户信息
        UserInfo user = UserHolder.getUser();
        if (user == null) {
            throw new BusinessException(ExceptionEnum.VALIDATION_ERROR.getResultMsg(), ExceptionEnum.VALIDATION_ERROR);
        }
        //2.删除token信息
        String token = user.getToken();
        redisCache.deleteObject(LOGIN_USER_KEY + token);

        return true;
    }

    @Override
    public PageInfo<UserPageResultBo> selectUserAll(UserPageRequestBo userPageRequestBo) {

        userPageRequestBo.check();
        PageHelper.startPage(userPageRequestBo.getPageNum(), userPageRequestBo.getPageSize());

        UserPageRequestDto userPageRequestDto = UserConvert.INSTANCE.convertUserPageRequestBo2Dto(userPageRequestBo);

        List<UserPageResultDto> users = userMapper.selectUserAll(userPageRequestDto);
        PageInfo<UserPageResultDto> pageInfoDto = new PageInfo<>(users);

        PageInfo<UserPageResultBo> pageInfoBo = PageUtil.PageInfoDto2PageInfoBo(pageInfoDto);

        return pageInfoBo;
    }

    @Transactional
    @Override
    public void insertUser(UserAddBo userAddBo) {
        // 插入用户表
        User user = new User();
        user.setUserName(userAddBo.getNickName());
        // 加密密码
        user.setPassword(CryptUtil.sm3Encrypt(userAddBo.getPassword()));
        user.setMobile(userAddBo.getMobile());
        user.setEmail(userAddBo.getEmail());
        user.setSex(userAddBo.getSex());
        user.setMarital(userAddBo.getMarital());
        user.setAddress(userAddBo.getAddress());
        user.setStatus(UserConstant.STATUS_START);
        user.setCreateBy(1L);
        user.setUpdateBy(1L);
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setIsDelete(UserConstant.NOT_DELETED);
        userMapper.insertUser(user);

        // 插入用户角色表
        Long[] roleIds = userAddBo.getRoleIds();
        userRoleRelMapper.batchInsert(user.getId(), roleIds);

        // 插入用户组表
        if (userAddBo.getGroupId() != null) {
            userGroupRelMapper.insertMember(user.getId(), userAddBo.getGroupId());
        }
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        //删除用户角色关系
        userRoleRelMapper.deleteByUserId(id);
        //删除用户组别关系
        userGroupRelMapper.deleteByUserId(id);
        //删除用户权限关系
        userPermissionRelMapper.deleteByPrimaryKey(id);
        //删除用户
        userMapper.deleteUserById(id);
    }

    @Transactional
    @Override
    public void updateUser(UserEditBo userEditBo) {

        // 修改用户表
        User user = UserConvert.INSTANCE.convertUserEditBo2Po(userEditBo);
        user.setUpdateBy(1L);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUser(user);

        // 删除之前用户角色关系后插入用户角色表
        userRoleRelMapper.deleteByUserId(userEditBo.getId());
        if (!ArrayUtils.isEmpty(userEditBo.getRoleIds())) {
            userRoleRelMapper.batchInsert(userEditBo.getId(), userEditBo.getRoleIds());
        }

        // 如果修改了组别，就删除后重新插入
        UserGroupRel userGroupRel = userGroupRelMapper.selectOne(userEditBo.getId());
        if (userEditBo.getGroupId() != null) {
            if (userGroupRel == null || userEditBo.getGroupId().longValue() != userGroupRel.getGroupId().longValue()) {
                userGroupRelMapper.deleteByUserId(userEditBo.getId());
                userGroupRelMapper.insertMember(userEditBo.getId(), userEditBo.getGroupId());
            }
        } else {
            userGroupRelMapper.deleteByUserId(userEditBo.getId());
        }
    }

    @Override
    public UserBo selectById(Long id) {

        UserBo userBo;
        User user = userMapper.selectById(id);
        userBo = UserConvert.INSTANCE.convertUserPo2UserBo(user);

        List<RoleDisVo> roleList = userRoleRelMapper.selectByUserId(id);
        userBo.setRoleList(roleList);

        GroupDisVo group = userGroupRelMapper.selectByUserId(id);
        userBo.setGroup(group);

        return userBo;
    }

    @Override
    public void start(Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(UserConstant.NOT_BANNED);
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(1L);
        userMapper.updateUser(user);
    }

    @Transactional
    @Override
    public void stop(Long id) {
        User user = new User();
        user.setId(id);
        user.setStatus(UserConstant.IS_BANNED);
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(1L);
        userMapper.updateUser(user);
    }

    @Override
    public String resetPwd(PwdDto pwdDto, HttpServletRequest request) {
        // 比对原密码
        User user = userMapper.selectById(1L);
        if (user == null) {
            return "获取用户信息失败";
        }
        String plainOld = CryptUtil.base64Decrypt(pwdDto.getOldPwd());
        String plainNew = CryptUtil.base64Decrypt(pwdDto.getNewPwd());
        if (!StringUtils.equals(user.getPassword(), CryptUtil.sm3Encrypt(plainOld))) {
            return "原密码错误";
        }
        if (StringUtils.equals(user.getPassword(), CryptUtil.sm3Encrypt(plainNew))) {
            return "新密码与原密码一致，请重新输入";
        }
        // 设置新密码
        user.setPassword(CryptUtil.sm3Encrypt(plainNew));
        user.setUpdateBy(1L);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUser(user);

        return null;
    }

    @Override
    public List<UserInfoVo> selectUngrouped() {
        List<UserInfoVo> userInfoVos = userGroupRelMapper.selectUngrouped();

        return userInfoVos;
    }

    @Override
    public List<UserInfoVo> selectGrouped() {
        List<UserInfoVo> userInfoVos = userGroupRelMapper.selectGrouped();

        return userInfoVos;
    }

    @Override
    public boolean checkUserNameUnique(String workId) {
        User user = userMapper.selectByWorkId(workId);
        return user == null;
    }

    @Override
    public boolean checkUserExist(Long id) {
        User user = userMapper.selectById(id);
        // 如果是已删除用户,返回不存在
        if (user == null || user.getIsDelete().equals(UserConstant.IS_DELETED)) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        String aa = new String("/online-services-usercenter-base/manage/user/list");
        a.add("online-services-usercenter-base/manage/user/list");
        a.add("B");
        System.out.println(a.contains(aa));
    }
}
