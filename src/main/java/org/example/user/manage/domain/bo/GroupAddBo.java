package org.example.user.manage.domain.bo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class GroupAddBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @NotNull
    @Length(max = 20)
    private String name;

    /**
     * 成员id
     */
    private List<Long> members;

    /**
     * 组长id
     */
    private Long leader;

}
