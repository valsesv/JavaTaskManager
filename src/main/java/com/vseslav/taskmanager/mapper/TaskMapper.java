package com.vseslav.taskmanager.mapper;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;

public class TaskMapper {

    public static Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setName(dto.name());
        task.setDescription(dto.description());
        task.setStatus(dto.status() != null ? dto.status() : TaskStatus.TODO);
        task.setUser(dto.user());
        task.setCategory(dto.category());
        return task;
    }
}
