package org.example.user.manage.domain.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String workId;

    private String nickName;

    private String groupName;
}
