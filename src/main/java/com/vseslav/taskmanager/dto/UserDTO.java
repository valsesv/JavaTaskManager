package com.vseslav.taskmanager.dto;

import java.io.Serializable;

public record UserDTO(Long id, String name) implements Serializable {
}
