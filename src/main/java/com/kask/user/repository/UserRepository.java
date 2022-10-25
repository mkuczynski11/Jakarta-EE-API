package com.kask.user.repository;

import com.kask.repository.Repository;
import com.kask.user.entity.User;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
public class UserRepository implements Repository<User, String> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> get(String username) {
        return Optional.ofNullable(entityManager.find(User.class, username));
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
        entityManager.createQuery("delete from Achievement a where a.user = :user")
                .setParameter("user", entity)
                .executeUpdate();
        entityManager.remove(entityManager.find(User.class, entity.getUsername()));
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
