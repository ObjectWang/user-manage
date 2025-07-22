package org.example.user.manage.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;

    private String nickName;

    private String token;

    private List<String> permissions;

}
