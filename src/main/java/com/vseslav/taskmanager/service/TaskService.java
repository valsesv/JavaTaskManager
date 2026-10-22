package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task create(TaskDTO dto) {
        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setDone(dto.getDone());
        task.setUser(dto.getUser());
        task.setCategory(dto.getCategory());

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
