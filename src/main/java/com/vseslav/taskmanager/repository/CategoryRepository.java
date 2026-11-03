package com.vseslav.taskmanager.repository;

import com.vseslav.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_Id(Long userId);

    Optional<Category> findByNameAndUser_Id(String name, Long userId);
}
