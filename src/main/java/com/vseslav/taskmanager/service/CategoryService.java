package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.CategoryDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.exception.BadRequestException;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.mapper.CategoryMapper;
import com.vseslav.taskmanager.repository.CategoryRepository;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public static final String COMMON = "Common";

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryDTO create(CategoryDTO dto) {
        if (dto.name() == null || dto.name().isBlank() || dto.userId() == null) {
            throw new BadRequestException("name and userId are required");
        }
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BadRequestException("User not found: " + dto.userId()));
        return CategoryMapper.toDto(categoryRepository.save(CategoryMapper.toEntity(dto, user)));
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> readAll(Long userId) {
        if (userId == null) {
            throw new BadRequestException("userId is required");
        }
        return categoryRepository.findByUser_Id(userId).stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    public Category getOrCreateCommon(User user) {
        return categoryRepository.findByNameAndUser_Id(COMMON, user.getId())
                .orElseGet(() -> categoryRepository.save(
                        CategoryMapper.toEntity(new CategoryDTO(null, COMMON, user.getId()), user)));
    }

    public CategoryDTO update(CategoryDTO dto) {
        if (dto.id() == null || dto.name() == null || dto.name().isBlank()) {
            throw new BadRequestException("id and name are required");
        }
        Category category = categoryRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + dto.id()));
        category.setName(dto.name());
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
