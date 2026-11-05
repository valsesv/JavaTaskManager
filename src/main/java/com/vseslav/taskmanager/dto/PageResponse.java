package com.vseslav.taskmanager.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        int totalElements,
        int totalPages
) {
}
