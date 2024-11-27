// UserService.java
package com.juridique.juridique.Service;

import com.juridique.juridique.DTOs.Request.UserDtoRequest;
import com.juridique.juridique.DTOs.Response.UserDtoResponse;
import com.juridique.juridique.Exeption.ResourceNotFoundException;
import com.juridique.juridique.Model.User;
import com.juridique.juridique.Model.UserRole;
import com.juridique.juridique.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public UserDtoResponse create(UserDtoRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user.setActive(true);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDtoResponse.class);
    }

    @Transactional
    public UserDtoResponse update(UserDtoRequest userRequest, Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        existingUser.setRole(UserRole.valueOf(userRequest.getRole().toUpperCase()));
        if (userRequest.isActive() != existingUser.isActive()) {
            existingUser.setActive(userRequest.isActive());
        }
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDtoResponse.class);
    }

    public List<UserDtoResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDtoResponse.class))
                .collect(Collectors.toList());
    }

    public UserDtoResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        return modelMapper.map(user, UserDtoResponse.class);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
    }
}