package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusTaskFilterTest {

    private final StatusTaskFilter filter = new StatusTaskFilter();

    @Test
    void whenStatusIsNull_returnsAllTasks() {
        Task todo = task(TaskStatus.TODO);
        Task done = task(TaskStatus.DONE);

        List<Task> result = filter.apply(List.of(todo, done), new StatusCriteria(null));

        assertEquals(2, result.size());
    }

    @Test
    void whenStatusIsTodo_returnsOnlyTodoTasks() {
        Task todo = task(TaskStatus.TODO);
        Task done = task(TaskStatus.DONE);

        List<Task> result = filter.apply(List.of(todo, done), new StatusCriteria(TaskStatus.TODO));

        assertEquals(1, result.size());
        assertEquals(TaskStatus.TODO, result.get(0).getStatus());
    }

    private Task task(TaskStatus status) {
        Task task = new Task();
        task.setName("test");
        task.setStatus(status);
        return task;
    }
}
