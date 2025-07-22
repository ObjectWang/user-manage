package org.example.user.manage.domain.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserGroupRelBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Boolean leader;

    private Long groupId;

}
