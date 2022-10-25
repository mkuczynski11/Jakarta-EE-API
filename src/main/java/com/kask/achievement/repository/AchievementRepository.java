package com.kask.achievement.repository;

import com.kask.achievement.entity.Achievement;
import com.kask.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Dependent
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

    public Optional<Achievement> getByGame(String gameName, Integer achievementId) {
        try {
            return Optional.of(entityManager.createQuery("select a from Achievement a where a.id = :id and a.game.name = :gameName", Achievement.class)
                    .setParameter("id", achievementId)
                    .setParameter("gameName", gameName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Achievement> getByGameAndUser(String gameName, String username, Integer achievementId) {
        try {
            return Optional.of(entityManager.createQuery("select a from Achievement a where a.id = :id and a.game.name = :gameName and a.user.username = :username", Achievement.class)
                    .setParameter("id", achievementId)
                    .setParameter("gameName", gameName)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Achievement> getByUser(String username, Integer achievementId) {
        try {
            return Optional.of(entityManager.createQuery("select a from Achievement a where a.id = :id and a.user.username = :username", Achievement.class)
                    .setParameter("id", achievementId)
                    .setParameter("username", username)
                    .getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Achievement> getAll() {
        return entityManager.createQuery("select a from Achievement a", Achievement.class).getResultList();
    }

    public List<Achievement> getAllByUser(String username) {
        return entityManager.createQuery("select a from Achievement a where a.user.username = :username", Achievement.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Achievement> getAllByGame(String gameName) {
        return entityManager.createQuery("select a from Achievement a where a.game.name = :gameName", Achievement.class)
                .setParameter("gameName", gameName)
                .getResultList();
    }

    public List<Achievement> getAllByGameAndUser(String gameName, String username) {
        return entityManager.createQuery("select a from Achievement a where a.user.username = :username and a.game.name = :gameName", Achievement.class)
                .setParameter("username", username)
                .setParameter("gameName", gameName)
                .getResultList();
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
