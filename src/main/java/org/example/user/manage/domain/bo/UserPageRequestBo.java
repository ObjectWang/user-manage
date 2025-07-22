package org.example.user.manage.domain.bo;

import lombok.Data;
import org.example.user.manage.common.page.PageRequestParam;

import java.io.Serializable;

@Data
public class UserPageRequestBo extends PageRequestParam implements Serializable {

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