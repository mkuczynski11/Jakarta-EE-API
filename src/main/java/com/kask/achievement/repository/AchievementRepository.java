package com.kask.achievement.repository;

import com.kask.achievement.entity.Achievement;
import com.kask.datastore.DataStore;
import com.kask.repository.Repository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class AchievementRepository implements Repository<Achievement, Integer> {

    private DataStore dataStore;

    @Inject
    public AchievementRepository(DataStore dataStore) {this.dataStore = dataStore;}

    @Override
    public Optional<Achievement> get(Integer achievementId) {return dataStore.getAchievement(achievementId);}

    @Override
    public List<Achievement> getAll() {return dataStore.getAllAchievements();}

    @Override
    public void create(Achievement entity) {dataStore.createAchievement(entity);}

    @Override
    public void delete(Achievement entity) {dataStore.deleteAchievement(entity.getId());}

    @Override
    public void update(Achievement entity) {dataStore.updateAchievement(entity);}

    public List<Achievement> getByGame(String gameName) { return dataStore.getAchievementByGame(gameName);}

}
