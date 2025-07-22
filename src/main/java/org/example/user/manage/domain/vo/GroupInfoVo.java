package org.example.user.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupId;

    private String name;

}
