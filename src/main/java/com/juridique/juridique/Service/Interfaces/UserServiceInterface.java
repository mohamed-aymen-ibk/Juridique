package com.juridique.juridique.Service.Interfaces;

import com.juridique.juridique.DTOs.Request.UserDtoRequest;
import com.juridique.juridique.DTOs.Response.UserDtoResponse;

import java.util.List;

public interface UserServiceInterface {
    public UserDtoResponse create(UserDtoRequest userRequest);

    public UserDtoResponse update(UserDtoRequest userRequest, Long id);

    public List<UserDtoResponse> findAll() ;

    public UserDtoResponse findUserById(Long id);

    public void delete(Long id);
}
