package org.example.user.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
