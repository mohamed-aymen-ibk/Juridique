package com.juridique.juridique.Service;

import com.juridique.juridique.DTOs.Request.UserDtoRequest;
import com.juridique.juridique.DTOs.Response.UserDtoResponse;
import com.juridique.juridique.Model.User;
import com.juridique.juridique.Model.UserRole;
import com.juridique.juridique.Reposetory.UserReposetory;
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

    public UserDtoResponse findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserDtoResponse.class);
    }

    public List<UserDtoResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDtoResponse.class))
                .collect(Collectors.toList());
    }

    public UserDtoResponse create(UserDtoRequest user) {
        User userEntity = modelMapper.map(user, User.class);
        User savedUser = userRepository.save(userEntity);
        return modelMapper.map(savedUser, UserDtoResponse.class);
    }

    public UserDtoResponse update(UserDtoRequest user, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setRole(UserRole.valueOf(user.getRole().toUpperCase()));
        existingUser.setActive(user.isActive());
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDtoResponse.class);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
