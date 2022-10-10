package com.kask.user.service;

import com.kask.user.repository.UserAvatarRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserAvatarService {

    private UserAvatarRepository userAvatarRepository;

    @Inject
    public UserAvatarService(UserAvatarRepository userAvatarRepository) {
        this.userAvatarRepository = userAvatarRepository;
    }

    public Optional<byte[]> getAvatar(Integer id) {
        return userAvatarRepository.get(id);
    }

    public void createAvatar(InputStream is, String filename, Integer id) {
        userAvatarRepository.create(is, filename, id);
    }

    public void updateAvatar(InputStream is, String filename, Integer id) {
        userAvatarRepository.update(is, filename, id);
    }

    public boolean deleteAvatar(Integer id) {
        return userAvatarRepository.delete(id);
    }
}
