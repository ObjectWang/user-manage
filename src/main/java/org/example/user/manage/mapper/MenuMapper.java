package org.example.user.manage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.user.manage.domain.po.Menu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author wyh
 * @since 2024-09-11
 */
@Mapper
public interface MenuMapper {

    List<Menu> selcetMenuByNodeType(int nodeType);

    List<Menu> selectList();
}
