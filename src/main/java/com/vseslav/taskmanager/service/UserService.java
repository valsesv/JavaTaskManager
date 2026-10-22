package com.vseslav.taskmanager.service;

import com.vseslav.taskmanager.dto.UserDTO;
import com.vseslav.taskmanager.entity.User;
import com.vseslav.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        return userRepository.save(user);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("There's no user with id: " + id));
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
