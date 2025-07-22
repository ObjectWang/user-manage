package org.example.user.manage.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PwdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String oldPwd;

    @NotNull
    private String newPwd;

}
