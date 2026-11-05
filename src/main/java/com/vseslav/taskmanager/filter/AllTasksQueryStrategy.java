package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllTasksQueryStrategy implements TaskQueryStrategy<EmptyCriteria> {

    private final TaskRepository taskRepository;

    @Override
    public boolean supports(TaskSearchRequest request) {
        return request.status() == null && request.from() == null && request.to() == null;
    }

    @Override
    public EmptyCriteria toCriteria(TaskSearchRequest request) {
        return new EmptyCriteria();
    }

    @Override
    public List<Task> find(Long userId, EmptyCriteria criteria) {
        return taskRepository.findByUser_Id(userId);
    }
}
