package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.exception.BadRequestException;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.mapper.TaskMapper;
import com.vseslav.taskmanager.repository.CategoryRepository;
import com.vseslav.taskmanager.repository.TaskRepository;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    public Task create(TaskDTO dto) {
        if (dto.name() == null || dto.name().isBlank() || dto.userId() == null) {
            throw new BadRequestException("name and userId are required");
        }

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BadRequestException("User not found: " + dto.userId()));

        Category category = resolveCategory(dto.categoryId(), user);
        Task task = TaskMapper.toEntity(dto, user, category);
        log.info("Creating task: {}", task.getName());
        return taskRepository.save(task);
    }

    private Category resolveCategory(Long categoryId, User user) {
        if (categoryId == null) {
            return categoryService.getOrCreateCommon(user);
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BadRequestException("Category not found: " + categoryId));
        if (!category.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Category does not belong to user " + user.getId());
        }
        return category;
    }

    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUser_Id(userId);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
