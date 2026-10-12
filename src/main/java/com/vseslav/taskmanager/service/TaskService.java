package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.repository.CategoryRepository;
import com.vseslav.taskmanager.repository.TaskRepository;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(TaskDTO dto) {
        Task task = Task.builder()
                .name(dto.getName())
                .user(dto.getUser())
                .category(dto.getCategory())
                .done(dto.getDone())
                .build();
        return taskRepository.save(task);
    }

    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
