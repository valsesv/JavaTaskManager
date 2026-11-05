package com.vseslav.taskmanager.repository;

import com.vseslav.taskmanager.entity.Task;
import com.vseslav.taskmanager.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_Id(Long userId);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.status = :status")
    List<Task> findByUserIdAndStatus(@Param("userId") Long userId,
                                     @Param("status") TaskStatus status);

    @Query("""
            SELECT t FROM Task t
            WHERE t.user.id = :userId
              AND (:from IS NULL OR t.createdAt >= :from)
              AND (:to IS NULL OR t.createdAt <= :to)
            """)
    List<Task> findByUserIdAndPeriod(@Param("userId") Long userId,
                                     @Param("from") LocalDate from,
                                     @Param("to") LocalDate to);

    @Query("""
            SELECT t FROM Task t
            WHERE t.user.id = :userId
              AND t.status = :status
              AND (:from IS NULL OR t.createdAt >= :from)
              AND (:to IS NULL OR t.createdAt <= :to)
            """)
    List<Task> findByUserIdAndFilters(@Param("userId") Long userId,
                                      @Param("status") TaskStatus status,
                                      @Param("from") LocalDate from,
                                      @Param("to") LocalDate to);
}
