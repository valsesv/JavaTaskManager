package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.exception.BadRequestException;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.mapper.TaskMapper;
import com.vseslav.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(TaskDTO dto) {
        if (dto.name() == null || dto.name().isBlank()) {
            throw new BadRequestException("Task name is required");
        }
        Task task = TaskMapper.toEntity(dto);
        log.info("Creating task: {}", task.getName());
        return taskRepository.save(task);
    }

    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByStatus(TaskStatus status) {
        log.info("Find tasks by status={}", status);
        return taskRepository.findByStatus(status);
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
