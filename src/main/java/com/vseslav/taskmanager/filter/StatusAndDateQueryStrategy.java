package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusAndDateQueryStrategy implements TaskQueryStrategy<StatusAndDateCriteria> {

    private final TaskRepository taskRepository;

    @Override
    public boolean supports(TaskSearchRequest request) {
        return request.status() != null && (request.from() != null || request.to() != null);
    }

    @Override
    public StatusAndDateCriteria toCriteria(TaskSearchRequest request) {
        return new StatusAndDateCriteria(request.status(), request.from(), request.to());
    }

    @Override
    public List<Task> find(Long userId, StatusAndDateCriteria criteria) {
        return taskRepository.findByUserIdAndStatusAndPeriod(
                userId, criteria.status(), criteria.from(), criteria.to());
    }
}
