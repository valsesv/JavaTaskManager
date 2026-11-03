package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatusTaskFilter implements TaskFilter<StatusCriteria> {

    @Override
    public List<Task> apply(List<Task> tasks, StatusCriteria criteria) {
        TaskStatus status = criteria.status();
        if (status == null) {
            return tasks;
        }
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (status.equals(task.getStatus())) {
                result.add(task);
            }
        }
        return result;
    }
}
