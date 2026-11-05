package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.event.TaskEventType;
import com.vseslav.taskmanager.exception.BadRequestException;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.filter.DateTaskFilter;
import com.vseslav.taskmanager.filter.StatusTaskFilter;
import com.vseslav.taskmanager.kafka.TaskEventProducer;
import com.vseslav.taskmanager.repository.CategoryRepository;
import com.vseslav.taskmanager.repository.TaskRepository;
import com.vseslav.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private StatusTaskFilter statusTaskFilter;
    @Mock
    private DateTaskFilter dateTaskFilter;
    @Mock
    private TaskEventProducer taskEventProducer;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(taskService, "pageSize", 10);
    }

    @Test
    void create_withoutName_throwsBadRequest() {
        TaskDTO dto = new TaskDTO(null, "  ", "desc", TaskStatus.TODO, null, 1L, null);

        assertThrows(BadRequestException.class, () -> taskService.create(dto));
    }

    @Test
    void create_savesTaskAndSendsKafkaEvent() {
        User user = user(1L, "Alice");
        Category category = category(10L, "common", user);
        TaskDTO dto = new TaskDTO(null, "Buy water", "from store", TaskStatus.TODO, null, 1L, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(categoryService.getOrCreateCommon(user)).thenReturn(category);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            ReflectionTestUtils.setField(task, "id", 100L);
            return task;
        });

        TaskDTO result = taskService.create(dto);

        assertEquals(100L, result.id());
        assertEquals("Buy water", result.name());
        verify(taskEventProducer).send(TaskEventType.CREATED, result);
    }

    @Test
    void findById_whenMissing_throwsNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.findById(99L));
    }

    @Test
    void findById_returnsDto() {
        User user = user(1L, "Alice");
        Category category = category(10L, "common", user);
        Task task = new Task();
        ReflectionTestUtils.setField(task, "id", 5L);
        task.setName("Read book");
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDate.of(2026, 11, 5));
        task.setUser(user);
        task.setCategory(category);

        when(taskRepository.findById(5L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.findById(5L);

        assertEquals(5L, result.id());
        assertEquals("Read book", result.name());
        assertEquals(1L, result.userId());
    }

    private User user(Long id, String name) {
        User user = new User();
        ReflectionTestUtils.setField(user, "id", id);
        user.setName(name);
        return user;
    }

    private Category category(Long id, String name, User user) {
        Category category = new Category();
        ReflectionTestUtils.setField(category, "id", id);
        category.setName(name);
        category.setUser(user);
        return category;
    }
}
