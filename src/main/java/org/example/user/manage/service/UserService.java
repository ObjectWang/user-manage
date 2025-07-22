package org.example.user.manage.service;

import com.github.pagehelper.PageInfo;
import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.dto.*;
import org.example.user.manage.domain.vo.UserInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
public interface UserService {

    PageInfo<UserPageResultBo> selectUserAll(UserPageRequestBo userPageRequestBo);

    void insertUser(UserAddBo userAddBo);

    void deleteUserById(Long id);

    void updateUser(UserEditBo userEditBo);

    UserBo selectById(Long id);

    void start(Long id);

    void stop(Long id);

    String resetPwd(PwdDto pwdDto, HttpServletRequest request);


    List<UserInfoVo> selectUngrouped();

    List<UserInfoVo> selectGrouped();

    boolean checkUserNameUnique(String workId);

    boolean checkUserExist(Long id);

    LoginResultBo login(LoginBo loginBo);

    Boolean logout();
}
