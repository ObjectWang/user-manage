package org.example.user.manage.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RoleEditDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotNull(message = "请输入必填项")
    private Long id;

    /**
     * 名称
     */
    @NotNull(message = "请输入必填项")
    @Length(max = 20)
    private String name;

    /**
     * 描述
     */
    @Length(max = 200)
    private String description;

    /**
     * 权限
     */
    private Long[] permissionIds;

}
