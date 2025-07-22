package org.example.user.manage.domain.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RoleAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @NotNull
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
    @NotNull
    private Long[] permissionIds;
}
