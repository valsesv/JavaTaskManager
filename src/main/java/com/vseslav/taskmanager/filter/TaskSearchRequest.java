package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.TaskStatus;

import java.time.LocalDate;

public record TaskSearchRequest(TaskStatus status, LocalDate from, LocalDate to) {
}
