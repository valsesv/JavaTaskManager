package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.CategoryDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return categoryRepository.save(category);
    }

    public List<Category> readAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There's no category with id: " + id));
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
