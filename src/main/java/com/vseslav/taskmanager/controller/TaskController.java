package com.vseslav.taskmanager.controller;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.TaskStatus;
import com.vseslav.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.create(taskDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> readAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) TaskStatus status) {
        if (userId != null) {
            return ResponseEntity.ok(taskService.findByUserId(userId));
        }
        if (status != null) {
            return ResponseEntity.ok(taskService.findByStatus(status));
        }
        return ResponseEntity.ok(taskService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findByID(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.update(taskDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        taskService.delete(id);
        return HttpStatus.OK;
    }
}
