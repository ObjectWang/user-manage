package org.example.user.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDisVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private String name;
}
