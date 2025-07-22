package org.example.user.manage.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private List<String> menuIds;

    private List<Long> elementIds;

    private String nickName;

    private Long userId;

}
