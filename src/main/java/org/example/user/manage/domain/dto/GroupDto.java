package org.example.user.manage.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupDto implements Serializable {

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

    private String userName;

    private String role;

    private Integer level;
}
