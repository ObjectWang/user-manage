package org.example.user.manage.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.example.user.manage.domain.bo.UserInfoBo;
import org.example.user.manage.domain.dto.UserGroupDto;
import org.example.user.manage.domain.dto.UserPageRequestDto;
import org.example.user.manage.domain.dto.UserPageResultDto;
import org.example.user.manage.domain.po.User;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface UserMapper {

    List<UserPageResultDto> selectUserAll(UserPageRequestDto userPageRequestDto);

    int insertUser(User user);

    void deleteUserById(Long id);

    void updateUser(User user);

    User selectById(Long id);

    void start(Long id);

    void stop(Long id);

    User selectByWorkId(String workId);

    List<String> selectUrlById(Long id);

    List<String> selectMenuIdById(Long id);

    List<User> selectByNickName(String nickName);

    List<UserInfoBo> QueryByWorkIdAndNickName(User user);

    List<UserGroupDto> selectAllUserGroupDto();

    List<UserInfoBo> selectByIds(List<Long> ids);

    List<User> selectAllUsers();

    List<Long> selectelementIdsById(Long id);
}
