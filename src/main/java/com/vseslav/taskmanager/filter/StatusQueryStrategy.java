package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusQueryStrategy implements TaskQueryStrategy<StatusCriteria> {

    private final TaskRepository taskRepository;

    @Override
    public boolean supports(TaskSearchRequest request) {
        return request.status() != null && request.from() == null && request.to() == null;
    }

    @Override
    public StatusCriteria toCriteria(TaskSearchRequest request) {
        return new StatusCriteria(request.status());
    }

    @Override
    public List<Task> find(Long userId, StatusCriteria criteria) {
        return taskRepository.findByUserIdAndStatus(userId, criteria.status());
    }
}
