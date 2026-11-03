package com.vseslav.taskmanager.dto;

import com.vseslav.taskmanager.entity.TaskStatus;

import java.time.LocalDate;

public record TaskDTO(
        Long id,
        String name,
        String description,
        TaskStatus status,
        LocalDate createdAt,
        Long userId,
        Long categoryId
) {
}
