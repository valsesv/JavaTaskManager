package com.vseslav.taskmanager.repository;

import com.vseslav.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
