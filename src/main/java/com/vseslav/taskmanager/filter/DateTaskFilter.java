package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateTaskFilter implements TaskFilter<DateCriteria> {

    @Override
    public List<Task> apply(List<Task> tasks, DateCriteria criteria) {
        LocalDate from = criteria.from();
        LocalDate to = criteria.to();
        if (from == null && to == null) {
            return tasks;
        }
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            LocalDate createdAt = task.getCreatedAt();
            if (createdAt == null) {
                continue;
            }
            if (from != null && createdAt.isBefore(from)) {
                continue;
            }
            if (to != null && createdAt.isAfter(to)) {
                continue;
            }
            result.add(task);
        }
        return result;
    }
}
