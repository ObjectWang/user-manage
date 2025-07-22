package org.example.user.manage.domain.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 节点类型，1文件夹，2页面
     */
    private Integer nodeType;

    /**
     * 名称
     */
    private String name;

    /**
     * 前端编码
     */
    private String code;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除 1：已删除；0：未删除
     */
    private Integer isDelete;

}
