package org.example.user.manage.domain.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginResultBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private List<String> menuIds;

    private List<Long> elementIds;

    private String nickName;

    private Long userId;

}
