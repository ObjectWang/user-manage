package org.example.user.manage.domain.bo;

import lombok.Data;
import org.example.user.manage.common.page.PageRequestParam;

import java.io.Serializable;

@Data
public class RolePageRequestBo extends PageRequestParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

}