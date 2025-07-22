package org.example.user.manage.domain.bo;


import lombok.Data;
import org.example.user.manage.domain.vo.UserInfoVo;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupMemberBo implements Serializable {
    /**
     * 组别id
     */
    private Long groupId;

    private String groupName;

    private List<UserInfoVo> members;

    private static final long serialVersionUID = 1L;
}
