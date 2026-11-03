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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @Transactional
    public TaskDTO create(TaskDTO dto) {
        if (dto.name() == null || dto.name().isBlank() || dto.userId() == null) {
            throw new BadRequestException("name and userId are required");
        }

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BadRequestException("User not found: " + dto.userId()));
        Category category = resolveCategory(dto.categoryId(), user);

        Task saved = taskRepository.save(TaskMapper.toEntity(dto, user, category));
        log.info("Creating task: {}", saved.getName());
        return TaskMapper.toDto(saved);
    }

    @Transactional
    public TaskDTO update(TaskDTO dto) {
        if (dto.id() == null || dto.name() == null || dto.name().isBlank() || dto.userId() == null) {
            throw new BadRequestException("id, name and userId are required");
        }

        Task task = taskRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + dto.id()));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BadRequestException("User not found: " + dto.userId()));
        Category category = resolveCategory(dto.categoryId(), user);

        task.setName(dto.name());
        task.setDescription(dto.description());
        task.setStatus(dto.status() != null ? dto.status() : TaskStatus.TODO);
        task.setUser(user);
        task.setCategory(category);

        return TaskMapper.toDto(taskRepository.save(task));
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

    @Transactional(readOnly = true)
    public List<TaskDTO> readAll() {
        return taskRepository.findAll().stream().map(TaskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream().map(TaskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findByUserId(Long userId) {
        return taskRepository.findByUser_Id(userId).stream().map(TaskMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
