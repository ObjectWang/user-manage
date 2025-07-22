package org.example.user.manage.domain;


import lombok.Data;
import org.example.user.manage.domain.po.User;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    //存储权限信息
    private List<String> permissions;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public Long getUserId(){return user.getId();}

    public String getPassword() {
        return user.getPassword();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getNickName() {
        return user.getNickName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
