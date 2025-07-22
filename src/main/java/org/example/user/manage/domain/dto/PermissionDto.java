package org.example.user.manage.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long permissionId;

    private String permissionName;

}
