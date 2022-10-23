package com.kask.user.repository;

import com.kask.datastore.DataStore;
import com.kask.repository.Repository;
import com.kask.user.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserRepository implements Repository<User, Integer> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> get(Integer id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entityManager.find(User.class, entity.getId()));
    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public void detach(User entity){
        entityManager.detach(entity);
    }
}
