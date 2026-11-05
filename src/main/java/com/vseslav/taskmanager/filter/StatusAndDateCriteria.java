package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.TaskStatus;

import java.time.LocalDate;

public record StatusAndDateCriteria(TaskStatus status, LocalDate from, LocalDate to) {
}
