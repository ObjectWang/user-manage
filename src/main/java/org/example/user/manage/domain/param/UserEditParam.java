package org.example.user.manage.domain.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserEditParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    // /**
    //  * 工号
    //  */
    // @Pattern(regexp = "\\d{4}")
    // private String workId;
    //
    // /**
    //  * 姓名
    //  */
    // @Length(max = 30)
    // private String nickName;
    //
    // /**
    //  * 性别 1：男；0：女
    //  */
    // private Integer sex;


    /**
     * 手机号
     */
    @NotNull(message = "联系方式不能为空")
    @Pattern(regexp = "\\d{11}")
    private String mobile;

    /**
     * 婚姻状况 2：离异 1：已婚；0：未婚
     */
    private Integer marital;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^(|(?=.{0,70}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-z]{2,}$)", message = "邮箱格式错误")
    private String email;

    /**
     * 家庭住址
     */
    @Length(max = 50)
    private String address;

    /**
     * 角色
     */
    @NotNull(message = "角色不能为空")
    private Long[] roleIds;

    /**
     * 组别id
     */
    private Long groupId;
}
