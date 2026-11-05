package com.vseslav.taskmanager.event;

public record TaskEvent(
        TaskEventType type,
        Long taskId,
        String name,
        String status,
        Long userId
) {
}
