package com.vseslav.taskmanager.filter;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusQueryStrategyTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private StatusQueryStrategy strategy;

    @Test
    void supports_onlyStatusWithoutDates() {
        assertTrue(strategy.supports(new TaskSearchRequest(TaskStatus.TODO, null, null)));
        assertFalse(strategy.supports(new TaskSearchRequest(TaskStatus.TODO, LocalDate.now(), null)));
        assertFalse(strategy.supports(new TaskSearchRequest(null, null, null)));
    }

    @Test
    void find_callsStatusQuery() {
        StatusCriteria criteria = new StatusCriteria(TaskStatus.TODO);
        when(taskRepository.findByUserIdAndStatus(1L, TaskStatus.TODO))
                .thenReturn(List.of(new Task()));

        strategy.find(1L, criteria);

        verify(taskRepository).findByUserIdAndStatus(1L, TaskStatus.TODO);
    }

    @Test
    void toCriteria_keepsOnlyStatus() {
        StatusCriteria criteria = strategy.toCriteria(
                new TaskSearchRequest(TaskStatus.DONE, null, null));

        assertEquals(TaskStatus.DONE, criteria.status());
    }
}
