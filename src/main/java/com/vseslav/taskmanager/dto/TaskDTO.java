package com.vseslav.taskmanager.dto;

import com.vseslav.taskmanager.entity.TaskStatus;

public record TaskDTO(
        Long id,
        String name,
        String description,
        TaskStatus status,
        Long userId,
        Long categoryId
) {
}
