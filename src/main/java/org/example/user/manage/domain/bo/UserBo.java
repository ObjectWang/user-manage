package org.example.user.manage.domain.bo;

import lombok.Data;
import org.example.user.manage.domain.vo.GroupDisVo;
import org.example.user.manage.domain.vo.RoleDisVo;

import java.io.Serializable;
import java.util.List;

@Data
public class UserBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 工号
     */
    private String userName;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 性别 1：男；0：女
     */
    private Integer sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 婚姻状况 2：离异 1：已婚；0：未婚
     */
    private Integer marital;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 角色
     */
    private List<RoleDisVo> roleList;

    /**
     * 组
     */
    private GroupDisVo group;

}
