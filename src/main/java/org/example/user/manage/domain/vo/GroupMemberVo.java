package org.example.user.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupMemberVo implements Serializable {

    private Long groupId;

    private String groupName;

    private List<UserInfoVo> members;

    private static final long serialVersionUID = 1L;

}
