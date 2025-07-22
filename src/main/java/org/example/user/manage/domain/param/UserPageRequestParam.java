package org.example.user.manage.domain.param;

import lombok.Data;

@Data
public class UserPageRequestParam {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    private String userName;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 组别id
     */
    private Integer groupId;

    /**
     * 角色id
     */
    private Integer roleId;
}
