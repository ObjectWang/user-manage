package org.example.user.manage.domain.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String nickName;

    private Integer sex;

    private String workId;

    private String role;

    private Integer level;
}
