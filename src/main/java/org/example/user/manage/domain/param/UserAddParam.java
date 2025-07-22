package org.example.user.manage.domain.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp = "\\d{4}", message = "请输入4位用户名")
    private String userName;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,32}$", message = "密码必须包含大小写字母、数字、特殊字符且长度在8-32位之间")
    private String password;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    @Length(max = 30)
    private String nickName;

    /**
     * 手机号
     */
    @NotNull(message = "联系方式不能为空")
    @Pattern(regexp = "\\d{11}", message = "请输入11位手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^(|(?=.{0,70}$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-z]{2,}$)", message = "邮箱格式错误")
    private String email;

    /**
     * 性别 1：男；0：女
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;

    /**
     * 婚姻状况 2：离异 1：已婚；0：未婚
     */
    private Integer marital;

    /**
     * 家庭住址
     */
    @Length(max = 50)
    private String address;

    /**
     * 角色
     */
    @NotNull
    private Long[] roleIds;

    /**
     * 组别id
     */
    private Long groupId;

}
