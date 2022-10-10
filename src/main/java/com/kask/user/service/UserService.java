package com.kask.user.service;

import com.kask.user.entity.User;
import com.kask.user.repository.UserRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.get(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public void createUser(User user) {
        userRepository.create(user);
    }

    public void deleteUser(User user) {userRepository.delete(user);}

    public void updateUser(User user) {userRepository.update(user);}

}
