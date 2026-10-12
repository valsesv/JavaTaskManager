package com.vseslav.taskmanager.controller;

import com.vseslav.taskmanager.dto.TaskDTO;
import com.vseslav.taskmanager.entity.Task;
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
    public ResponseEntity<Task> create(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.create(taskDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Task>> readAll() {
        return new ResponseEntity<>(taskService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Task> findByID(@RequestBody Long id) {
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.update(task), HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public HttpStatus delete(@PathVariable Long id) {
        taskService.delete(id);
        return HttpStatus.OK;
    }
}
