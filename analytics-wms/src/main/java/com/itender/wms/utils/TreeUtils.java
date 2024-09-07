package com.itender.wms.utils;

import com.itender.wms.entity.ProductType;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author analytics
 * @date 2024/9/6 17:37
 * @description
 */
public class TreeUtils {

    /**
     * 生成菜单树
     *
     * @param productTypeList
     * @param pid
     * @return
     */
    public static List<ProductType> buildProductTypeTree(List<ProductType> productTypeList, Integer pid) {
        // 创建集合保存菜单数据
        List<ProductType> tree = new ArrayList<>();
        // 判断菜单列表是否为空，如果不为空则使用菜单列表，否则创建集合对象
        Optional.ofNullable(productTypeList).orElse(new ArrayList<>())
                .stream().filter(item -> Objects.nonNull(item) && Objects.equals(pid, item.getParentId()))
                .forEach(item -> {
                    // 创建权限菜单对象
                    ProductType productType = new ProductType();
                    // 将原有的属性复制给菜单对象
                    BeanUtils.copyProperties(item, productType);
                    // 获取每一个item对象的子菜单，递归生成菜单树
                    List<ProductType> children = buildProductTypeTree(productTypeList, item.getTypeId());
                    // 设置子菜单
                    productType.setChildProductCategory(children);
                    // 将菜单对象添加到集合
                    tree.add(productType);
                });
        // 返回菜单信息
        return tree;
    }

    private TreeUtils() {}
}
