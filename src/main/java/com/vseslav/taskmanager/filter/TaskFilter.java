package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;

import java.util.List;

public interface TaskFilter<C> {

    List<Task> apply(List<Task> tasks, C criteria);
}
