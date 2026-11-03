package com.vseslav.taskmanager.dto;

import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.entity.User;

public record TaskDTO(
        String name,
        String description,
        TaskStatus status,
        User user,
        Category category
) {
}
