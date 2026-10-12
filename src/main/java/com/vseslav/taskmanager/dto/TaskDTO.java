package com.vseslav.taskmanager.dto;

import com.vseslav.taskmanager.entity.Category;
import com.vseslav.taskmanager.entity.User;
import lombok.Data;

@Data
public class TaskDTO {
    private String name;
    private String description;
    private Boolean done = false;
    private User user;
    private Category category;
}
