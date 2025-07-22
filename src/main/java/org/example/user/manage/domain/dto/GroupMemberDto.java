package org.example.user.manage.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupMemberDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组别id
     */
    private Long groupId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 主键
     */
    private Long id;

    /**
     * 工号
     */
    private String nickName;

    /**
     * 姓名
     */
    private String workId;

    private Integer status;

}
