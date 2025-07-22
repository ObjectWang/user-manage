package org.example.user.manage.domain.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UserGroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long agentId;

    /**
     * 工号
     */
    private String userName;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 组别Id
     */
    private Integer groupId;


    /**
     * 组别名称
     */
    private String groupName;

    /**
     * 级别1：组长 0：组员
     */
    private Integer leveler;


}
