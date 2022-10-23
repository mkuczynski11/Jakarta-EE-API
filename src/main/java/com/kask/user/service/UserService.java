package com.kask.user.service;

import com.kask.user.entity.User;
import com.kask.user.repository.UserRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
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

    @Transactional
    public void createUser(User user) {
        userRepository.create(user);
    }

    @Transactional
    public void deleteUser(User user) {userRepository.delete(user);}

    @Transactional
    public void updateUser(User user) {userRepository.update(user);}

    @Transactional
    public void updateAvatar(User user, InputStream is) {
        try {
            user.setAvatar(is.readAllBytes());
            updateUser(user);
        } catch (IOException io) {
            throw new IllegalStateException();
        }
    }

    @Transactional
    public void deleteAvatar(User user){
        user.setAvatar(null);
        updateUser(user);
    }

}
