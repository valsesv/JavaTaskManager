package com.vseslav.taskmanager.mapper;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.entity.User;

import java.time.LocalDate;

public class TaskMapper {

    public static Task toEntity(TaskDTO dto, User user, Category category) {
        Task task = new Task();
        task.setName(dto.name());
        task.setDescription(dto.description());
        task.setStatus(dto.status() != null ? dto.status() : TaskStatus.TODO);
        task.setCreatedAt(dto.createdAt() != null ? dto.createdAt() : LocalDate.now());
        task.setUser(user);
        task.setCategory(category);
        return task;
    }

    public static TaskDTO toDto(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUser() != null ? task.getUser().getId() : null,
                task.getCategory() != null ? task.getCategory().getId() : null
        );
    }
}
