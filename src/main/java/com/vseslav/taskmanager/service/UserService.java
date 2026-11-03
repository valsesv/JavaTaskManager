package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.UserDTO;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.exception.BadRequestException;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.mapper.UserMapper;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CategoryService categoryService;

    @Transactional
    public UserDTO create(UserDTO dto) {
        if (dto.name() == null || dto.name().isBlank()) {
            throw new BadRequestException("name is required");
        }
        User saved = userRepository.save(UserMapper.toEntity(dto));
        categoryService.getOrCreateCommon(saved);
        return UserMapper.toDto(saved);
    }

    @Cacheable("users")
    public List<UserDTO> readAll() {
        log.info("Loading users from DB (cache miss)");
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }

    public UserDTO findById(long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public UserDTO update(UserDTO dto) {
        if (dto.id() == null || dto.name() == null || dto.name().isBlank()) {
            throw new BadRequestException("id and name are required");
        }
        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.id()));
        user.setName(dto.name());
        return UserMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
