package com.juridique.juridique.Service;

import com.juridique.juridique.DTOs.Request.UserDtoRequest;
import com.juridique.juridique.DTOs.Response.UserDtoResponse;
import com.juridique.juridique.Exeption.ResourceNotFoundException;
import com.juridique.juridique.Model.User;
import com.juridique.juridique.Model.UserRole;
import com.juridique.juridique.Reposetory.UserReposetory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserReposetory userRepository;
    private final ModelMapper modelMapper;

    // Create a new user
    @Transactional
    public UserDtoResponse create(UserDtoRequest userRequest) {
        // Map UserDtoRequest to User entity
        User user = modelMapper.map(userRequest, User.class);

        // Save user to database
        User savedUser = userRepository.save(user);

        // Map saved User entity to UserDtoResponse
        return modelMapper.map(savedUser, UserDtoResponse.class);
    }

    // Update an existing user
    @Transactional
    public UserDtoResponse update(UserDtoRequest userRequest, Long id) {
        // Find the existing user by ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        // Update the existing user fields with new data
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        existingUser.setRole(UserRole.valueOf(userRequest.getRole().toUpperCase())); // Assuming role is a string
        existingUser.setActive(userRequest.isActive());

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // Return the updated user as a DTO response
        return modelMapper.map(updatedUser, UserDtoResponse.class);
    }

    // Get all users
    public List<UserDtoResponse> findAll() {
        // Get all users from the repository
        List<User> users = userRepository.findAll();

        // Convert list of User entities to list of UserDtoResponse using ModelMapper
        return users.stream()
                .map(user -> modelMapper.map(user, UserDtoResponse.class))
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserDtoResponse findUserById(Long id) {
        // Find the user by ID, throw exception if not found
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        // Convert User entity to UserDtoResponse and return
        return modelMapper.map(user, UserDtoResponse.class);
    }

    // Delete a user by ID
    @Transactional
    public void delete(Long id) {
        // Check if user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));

        // Delete the user
        userRepository.delete(user);
    }
}

