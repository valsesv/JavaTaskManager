package com.vseslav.taskmanager.controller;

import com.vseslav.taskmanager.dto.CategoryDTO;
import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.create(categoryDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> readAll() {
        return new ResponseEntity<>(categoryService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Category> findByID(@RequestBody Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Category> update(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.update(category), HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public HttpStatus delete(@PathVariable Long id) {
        categoryService.delete(id);
        return HttpStatus.OK;
    }
}
