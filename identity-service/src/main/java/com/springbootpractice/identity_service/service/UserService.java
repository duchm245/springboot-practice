package com.springbootpractice.identity_service.service;

import com.springbootpractice.identity_service.exception.ResourceNotFoundException;
import com.springbootpractice.identity_service.model.dto.request.UserCreateRequest;
import com.springbootpractice.identity_service.model.dto.request.UserUpdateRequest;
import com.springbootpractice.identity_service.model.entity.User;
import com.springbootpractice.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * This method creates a new user based on the provided user creation request.
     *
     * @param request The user creation request containing the necessary user details.
     * @return The newly created user entity.
     */
    public User createUser(UserCreateRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());
        user.setStatus(1);

        return userRepository.save(user);
    }

    /**
     * This method gets all the users.
     *
     * @return A list of all the users.
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * This method gets a user based on the provided user id.
     *
     * @param userId The id of the user to be retrieved.
     * @return The user entity corresponding to the provided user id.
     */
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Not found userId=" + userId));
    }

    /**
     * This method updates a user based on the provided user id and user update request.
     *
     * @param userId           The id of the user to be updated.
     * @param userUpdateRequest The user update request containing the updated user details.
     */
    public User updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        existingUser.setPassword(userUpdateRequest.getPassword());
        existingUser.setEmail(userUpdateRequest.getEmail());
        existingUser.setFirstName(userUpdateRequest.getFirstName());
        existingUser.setLastName(userUpdateRequest.getLastName());
        existingUser.setDob(userUpdateRequest.getDob());
        existingUser.setStatus(userUpdateRequest.getStatus());

        return userRepository.save(existingUser);
    }

    /**
     * This method deletes a user based on the provided user id.
     *
     * @param userId The id of the user to be deleted.
     */
    public void deleteUser(String userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        if (existingUser != null) {
            userRepository.deleteById(userId);
        }
    }

    /**
     * This method soft deletes a user based on the provided user id.
     * Soft deletion means that the user's status is updated to indicate that the user is inactive.
     *
     * @param userId The id of the user to be soft deleted.
     * @throws RuntimeException If the user with the provided user id is not found.
     */
    public void deleteSoftUser(String userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found: " + userId));
        existingUser.setStatus(0);
    }

}
