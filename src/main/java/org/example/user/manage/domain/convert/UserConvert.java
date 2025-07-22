package org.example.user.manage.domain.convert;



import org.example.user.manage.domain.bo.*;
import org.example.user.manage.domain.dto.*;
import org.example.user.manage.domain.param.*;
import org.example.user.manage.domain.po.Group;
import org.example.user.manage.domain.po.Permission;
import org.example.user.manage.domain.po.Role;
import org.example.user.manage.domain.po.User;
import org.example.user.manage.domain.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserConvert {

    @Named("replaceAll")
    default String replaceAll(String agentName) {
        if (StringUtils.isEmpty(agentName)) {
            return null;
        }
        return agentName.replaceAll("%", "/%").replaceAll("_", "/_");
    }

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mapping(target = "nickName", source = "nickName", qualifiedByName = "replaceAll")
    UserPageRequestBo convertUserPageRequestParam2Bo(UserPageRequestParam param);

    UserAddBo userAddParam2bo(UserAddParam userAddParam);

    @Mapping(target = "name", source = "name", qualifiedByName = "replaceAll")
    RolePageRequestBo convertRolePageRequestParam2Dto(RolePageRequestParam param);

    RoleInfoVo convertRole2RoleInfoVo(Role role);


    RoleAddBo convertRoleAddParam2Dto(RoleAddParam roleAddParam);

    RoleEditBo convertRoleEditParam2Bo(RoleEditParam roleEditParam);

    GroupEditBo convertGroupEditParam2Bo(GroupEditParam groupEditParam);

    GroupAddBo convertGroupAddParam2Bo(GroupAddParam groupAddParam);

    GroupInfoVo convertGroup2GroupInfoVo(Group group);

    GroupPageRequestBo convertGroupPageRequestParam2Bo(GroupPageRequestParam groupPageRequestParam);

    UserEditBo convertUserEditParam2Bo(UserEditParam userEditParam);

    UserVo convertUserBo2Vo(UserBo userBo);

    RoleVo convertRoleBo2Vo(RoleBo roleBo);

    MenuVo convertMenuBo2Vo(MenuBo menuBo);

    GroupMemberVo convertGroupMemberBo2Vo(GroupMemberBo groupMemberBo);

    GroupVo convertGroupBo2Vo(GroupBo groupBo);

    UserPageRequestDto convertUserPageRequestBo2Dto(UserPageRequestBo userPageRequestBo);

    User convertUserEditBo2Po(UserEditBo userEditBo);

    UserBo convertUserPo2UserBo(User user);

    GroupPageRequestDto convertGroupPageRequestBo2Dto(GroupPageRequestBo groupPageRequestBo);

    Group convertGroupAddBo2Po(GroupAddBo groupAddBo);

    GroupBo convertGroupDto2Bo(GroupDto groupDto);

    RolePageRequestDto convertRolePageRequestBo2Dto(RolePageRequestBo rolePageRequestBo);

    Role convertRoleAddBo2Po(RoleAddBo roleAddBo);

    Role convertRoleEditBo2Po(RoleEditBo roleEditBo);

    RoleBo convertRolePo2Bo(Role role);

    LoginBo convertLoginParam2Dto(LoginParam loginParam);

    LoginResultVo convertLoginResultBo2Vo(LoginResultBo loginResultBo);
}
