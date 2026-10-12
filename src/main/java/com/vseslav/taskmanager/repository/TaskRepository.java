package com.vseslav.taskmanager.repository;

import com.vseslav.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryId(Long categoryId);
    List<Task> findByUserId(Long userId);
    List<Task> findByDone(boolean done);
}
