package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DateQueryStrategy implements TaskQueryStrategy<DateCriteria> {

    private final TaskRepository taskRepository;

    @Override
    public boolean supports(TaskSearchRequest request) {
        return request.status() == null && (request.from() != null || request.to() != null);
    }

    @Override
    public DateCriteria toCriteria(TaskSearchRequest request) {
        return new DateCriteria(request.from(), request.to());
    }

    @Override
    public List<Task> find(Long userId, DateCriteria criteria) {
        return taskRepository.findByUserIdAndPeriod(userId, criteria.from(), criteria.to());
    }
}
