package org.example.user.manage.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工号
     */
    @NotBlank
    private String userName;

    /**
     * 密码
     */
    @NotBlank
    private String password;

}