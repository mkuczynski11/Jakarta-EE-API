package com.kask.achievement.repository;

import com.kask.achievement.entity.Achievement;
import com.kask.datastore.DataStore;
import com.kask.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AchievementRepository implements Repository<Achievement, Integer> {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Achievement> get(Integer achievementId) {
        return Optional.ofNullable(entityManager.find(Achievement.class, achievementId));
    }

    @Override
    public List<Achievement> getAll() {
        return entityManager.createQuery("select a from Achievement a", Achievement.class).getResultList();
    }

    @Override
    public void create(Achievement entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Achievement entity) {
        entityManager.remove(entityManager.find(Achievement.class, entity.getId()));
    }

    @Override
    public void update(Achievement entity) {
        entityManager.merge(entity);
    }

    @Override
    public void detach(Achievement entity){
        entityManager.detach(entity);
    }

}
