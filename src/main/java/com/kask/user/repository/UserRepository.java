package com.kask.user.repository;

import com.kask.datastore.DataStore;
import com.kask.repository.Repository;
import com.kask.user.entity.User;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User, Integer> {

    private DataStore dataStore;

    @Inject
    public UserRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<User> get(Integer id) {
        return dataStore.getUser(id);
    }

    @Override
    public List<User> getAll() {
        return dataStore.getAllUsers();
    }

    @Override
    public void create(User entity) {
        dataStore.createUser(entity);
    }

    @Override
    public void delete(User entity) {
        dataStore.updateUser(entity);
    }

    @Override
    public void update(User entity) {
        dataStore.updateUser(entity);
    }
}
