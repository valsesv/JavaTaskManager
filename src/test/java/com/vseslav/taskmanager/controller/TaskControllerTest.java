package com.vseslav.taskmanager.controller;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.exception.GlobalExceptionHandler;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getById_returnsTask() throws Exception {
        TaskDTO task = new TaskDTO(
                1L,
                "Buy water",
                "from store",
                TaskStatus.TODO,
                LocalDate.of(2026, 11, 5),
                1L,
                2L
        );
        when(taskService.findById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buy water"));
    }

    @Test
    void getById_whenMissing_returns404() throws Exception {
        when(taskService.findById(99L))
                .thenThrow(new ResourceNotFoundException("Task not found: 99"));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_returns201() throws Exception {
        TaskDTO created = new TaskDTO(
                1L,
                "Buy water",
                "from store",
                TaskStatus.TODO,
                LocalDate.of(2026, 11, 5),
                1L,
                null
        );
        when(taskService.create(any(TaskDTO.class))).thenReturn(created);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Buy water",
                                  "description": "from store",
                                  "status": "TODO",
                                  "userId": 1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Buy water"));
    }
}
