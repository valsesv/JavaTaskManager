package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.PageResponse;
import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.exception.BadRequestException;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.filter.DateCriteria;
import com.vseslav.taskmanager.filter.DateTaskFilter;
import com.vseslav.taskmanager.filter.StatusCriteria;
import com.vseslav.taskmanager.filter.StatusTaskFilter;
import com.vseslav.taskmanager.mapper.TaskMapper;
import com.vseslav.taskmanager.repository.CategoryRepository;
import com.vseslav.taskmanager.repository.TaskRepository;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final StatusTaskFilter statusTaskFilter;
    private final DateTaskFilter dateTaskFilter;

    @Value("${app.pagination.page-size}")
    private int pageSize;

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
        task.setCreatedAt(dto.createdAt() != null ? dto.createdAt() : task.getCreatedAt());
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
    public PageResponse<TaskDTO> find(Long userId, TaskStatus status, LocalDate from, LocalDate to, int page) {
        if (userId == null) {
            throw new BadRequestException("userId is required");
        }
        if (!userRepository.existsById(userId)) {
            throw new BadRequestException("User not found: " + userId);
        }
        if (page < 0) {
            throw new BadRequestException("page must be >= 0");
        }

        List<Task> tasks = taskRepository.findByUser_Id(userId);
        tasks = statusTaskFilter.apply(tasks, new StatusCriteria(status));
        tasks = dateTaskFilter.apply(tasks, new DateCriteria(from, to));

        int totalElements = tasks.size();
        int totalPages = totalElements == 0 ? 0 : (totalElements + pageSize - 1) / pageSize;

        int fromIndex = page * pageSize;
        List<TaskDTO> content = new ArrayList<>();
        if (fromIndex < totalElements) {
            int toIndex = Math.min(fromIndex + pageSize, totalElements);
            for (int i = fromIndex; i < toIndex; i++) {
                content.add(TaskMapper.toDto(tasks.get(i)));
            }
        }

        return new PageResponse<>(content, page, pageSize, totalElements, totalPages);
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + id));
        return TaskMapper.toDto(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
