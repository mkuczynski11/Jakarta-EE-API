package com.kask.user.repository;

import com.kask.datastore.DataStore;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Dependent
public class UserAvatarRepository {

    private DataStore dataStore;

    @Inject
    public UserAvatarRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Optional<byte[]> get(Integer id) {
        try {
            return dataStore.getAvatar(id);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void create(InputStream is, String filename, Integer id) {
        dataStore.createAvatar(is, filename, id);
    }

    public boolean delete(Integer id) {
        return dataStore.deleteAvatar(id);
    }

    public void update(InputStream is, String filename, Integer id) {
        dataStore.updateAvatar(is, filename, id);
    }
}
