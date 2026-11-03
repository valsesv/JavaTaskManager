package com.vseslav.taskmanager.mapper;

import com.vseslav.taskmanager.dto.CategoryDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.User;

public class CategoryMapper {

    public static Category toEntity(CategoryDTO dto, User user) {
        Category category = new Category();
        category.setName(dto.name());
        category.setUser(user);
        return category;
    }
}
