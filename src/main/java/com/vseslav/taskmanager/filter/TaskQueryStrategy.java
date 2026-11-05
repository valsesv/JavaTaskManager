package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;

import java.util.List;

public interface TaskQueryStrategy<C> {

    boolean supports(TaskSearchRequest request);

    C toCriteria(TaskSearchRequest request);

    List<Task> find(Long userId, C criteria);
}
