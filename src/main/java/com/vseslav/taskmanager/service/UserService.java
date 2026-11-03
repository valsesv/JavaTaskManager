package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.UserDTO;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.exception.ResourceNotFoundException;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(UserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        return userRepository.save(user);
    }

    @Cacheable("users")
    public List<User> readAll() {
        log.info("Loading users from DB (cache miss)");
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
