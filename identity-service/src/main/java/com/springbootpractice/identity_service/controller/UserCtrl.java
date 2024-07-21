package com.springbootpractice.identity_service.controller;

import com.springbootpractice.identity_service.model.dto.request.UserCreateRequest;
import com.springbootpractice.identity_service.model.dto.request.UserUpdateRequest;
import com.springbootpractice.identity_service.model.dto.response.ResponseData;
import com.springbootpractice.identity_service.model.entity.User;
import com.springbootpractice.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author duchm245"
 * @Date: 09/062024
 */

@RestController()
@RequestMapping(value = "/users")
public class UserCtrl {
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseData<User> createUser(@Valid @RequestBody UserCreateRequest request) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "", userService.createUser(request));
    }

    @GetMapping
    public ResponseData<?> getUsers() {
        return new ResponseData<>(HttpStatus.OK.value(),"", userService.getUsers());
    }

    @GetMapping("/{userId}")
    public ResponseData<User> getUser(@PathVariable("userId" ) String userId) {
        return new ResponseData<>(HttpStatus.OK.value(), "", userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userId, userUpdateRequest);
    }

    /**
     * Deletes a user permanently from the system.
     *
     * @param userId The unique identifier of the user to be deleted.
     * @return A ResponseEntity indicating the success of the operation.
     *         The HTTP status code will be 200 (OK) and the response body will contain the message "User deleted successfully".
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId" ) String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * Deletes a user from the system. This method is intended for soft deletion,
     * where the user is marked as deleted but not removed from the database.
     *
     * @param userId The unique identifier of the user to be deleted. This parameter is required and must not be null or empty.
     * @return A ResponseEntity indicating the success of the operation.
     *         The HTTP status code will be 200 (OK) and the response body will contain the message "User deleted successfully".
     */
    @PostMapping("/{userId}")
    public ResponseEntity<String> deleteSoftUser(@PathVariable("userId" ) String userId) {
        userService.deleteSoftUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
