package com.kask.datastore;

import com.kask.achievement.entity.Achievement;
import com.kask.game.entity.Game;
import com.kask.serialization.CloningUtility;
import com.kask.user.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class DataStore {
    private Set<Game> games = new HashSet<Game>();

    private Set<Achievement> achievements = new HashSet<Achievement>();

    private Set<User> users = new HashSet<User>();

    private Map<Integer, String> avatars = new HashMap<>();

    private String avatarPath;

    private String usrPath;

    public DataStore() {
        init();
    }

    private void init() {
        try {
            this.avatarPath = InitialContext.doLookup("config/avatars");
            this.usrPath = InitialContext.doLookup("config/usr");
            Files.createDirectories(Paths.get(usrPath + avatarPath).toAbsolutePath());
            log.info("DataStore: Avatars folder: " + Paths.get(usrPath + avatarPath).toAbsolutePath());
        } catch (IOException | NamingException ex) {
            this.avatarPath = null;
            this.usrPath = null;
        }
    }

    public synchronized List<User> getAllUsers() {
        log.info("Retrieving all users...");
        return users.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<User> getUser(int id) {
        log.info("Retrieving user with id: " + id);
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createUser(User user) throws IllegalArgumentException {
        log.info("Creating user with id: " + user.getId());
        getUser(user.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("User with id: " + user.getId() + ", already exists");
                },
                () -> users.add(CloningUtility.clone(user)));
        log.info("Created user: " + user.toString());
    }

    public synchronized void deleteUser(int id){
        log.info("Deleting user with id: " + id);
        Optional<User> user = getUser(id);
        if (user.isPresent()) {
            users.remove(user.get());
        } else {
            throw new IllegalArgumentException("User with id: " + user.get().getId() + " doesn't exist");
        }
    }

    public synchronized void updateUser(User user) throws IllegalArgumentException{
        log.info("Updating user with id: " + user.getId());
        getUser(user.getId()).ifPresentOrElse(
                original -> {
                    users.remove(user);
                    users.add(user);
                },
                () -> {
                    throw new IllegalArgumentException("User with id: " + user.getId() + " does not exists");
                });
    }

    public synchronized Optional<Game> getGame(String gameName) {
        log.info("Retrieving game with name: " + gameName);
        return games.stream()
                .filter(game -> game.getName().equals(gameName))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Game> getAllGames() {
        log.info("Getting all games");
        return games.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createGame(Game game) throws IllegalArgumentException {
        log.info("Creating game with name:" + game.getName());
        getGame(game.getName()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("Game with name: " + game.getName() + " already exists");
                },
                () -> games.add(CloningUtility.clone(game)));
        log.info("Created game: " + game.toString());
    }

    public synchronized void deleteGame(String gameName){
        log.info("Deleting game with name: " + gameName);
        Optional<Game> game = getGame(gameName);
        if (game.isPresent()) {
            achievements.removeIf(achievement -> achievement.getGame().equals(game.get()));
            games.remove(game.get());
        } else {
            throw new IllegalArgumentException("Game with name: " + game.get().getName() + " doesn't exist");
        }
    }

    public synchronized void updateGame(Game game) throws IllegalArgumentException{
        log.info("Updating game with name: " + game.getName());
        getGame(game.getName()).ifPresentOrElse(
                original -> {
                    games.remove(game);
                    games.add(game);
                },
                () -> {
                    throw new IllegalArgumentException("Game with name: " + game.getName() + " does not exists");
                });
    }

    public synchronized Optional<Achievement> getAchievement(String achievementName){
        log.info("Retrieving achievement with name: " + achievementName);
        return achievements.stream()
                .filter(achievement -> achievement.getName().equals(achievementName))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Achievement> getAllAchievements() {
        log.info("Getting all achievements");
        return achievements.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void deleteAchievement(String achievementName){
        log.info("Deleting achievement with name: " + achievementName);
        Optional<Achievement> achievement = getAchievement(achievementName);
        if (achievement.isPresent()) {
            achievements.remove(achievement.get());
        } else {
            throw new IllegalArgumentException("Achievement with name: " + achievement.get().getName() + " doesn't exist");
        }
    }

    public synchronized void createAchievement(Achievement achievement) throws IllegalArgumentException {
        log.info("Creating achievement with name: " + achievement.getName());
        getAchievement(achievement.getName()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException("Achievement with name" + achievement.getName() + " already exits");
                },
                () -> achievements.add(CloningUtility.clone(achievement))
        );
        log.info("Created achievement: "+ achievement.toString());
    }

    public synchronized void updateAchievement(Achievement achievement) throws IllegalArgumentException{
        log.info("Updating achievement with name: " + achievement.getName());
        getAchievement(achievement.getName()).ifPresentOrElse(
                original -> {
                    achievements.remove(achievement);
                    achievements.add(achievement);
                },
                () -> {
                    throw new IllegalArgumentException("Achievement with name: " + achievement.getName() + " does not exists");
                });
    }

    public synchronized List<Achievement> getAchievementByGame(String gameName){
        log.info("Retrieving achievements by game: " + gameName);
        return achievements.stream()
                .filter(achievement -> achievement.getGame().getName().equals(gameName))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createAvatar(InputStream is, String filename, Integer id){
        if (avatars.containsKey(id)) {
            throw new IllegalArgumentException("avatar for this user already exists");
        }
        if (avatarPath != null && usrPath != null) {
            String saveLocation = usrPath + avatarPath + filename;
            log.info("will save file under: " + saveLocation);

            saveFile(is, saveLocation);
            avatars.put(id, filename);
        } else {
            throw new IllegalStateException("cannot find save path!");
        }
    }

    public synchronized void updateAvatar(InputStream is, String filename, Integer id)
            throws IllegalArgumentException, IllegalStateException {
        if (!avatars.containsKey(id)) {
            throw new IllegalArgumentException("This user doesn't have avatar yet");
        }
        if (avatarPath != null && usrPath != null) {
            String saveLocation = usrPath + avatarPath + filename;
            String removeLocation = usrPath + avatarPath + avatars.get(id);
            log.info("new avatar path: " + saveLocation);
            log.info("old avatar path: " + removeLocation);

            removeFile(removeLocation);
            saveFile(is, saveLocation);

            avatars.replace(id, filename);
        } else {
            throw new IllegalStateException("cannot find save path!");
        }
    }

    public synchronized boolean deleteAvatar(Integer id)
            throws IllegalArgumentException, IllegalStateException {
        if (!avatars.containsKey(id)) {
            return false;
        }
        if (avatarPath != null && usrPath != null) {
            String removeLocation = usrPath + avatarPath + avatars.get(id);
            log.info("delete avatar from: " + removeLocation);

            removeFile(removeLocation);
            avatars.remove(id);
            return true;
        } else {
            throw new IllegalStateException("cannot find save path!");
        }
    }

    public synchronized Optional<byte[]> getAvatar(Integer id)
            throws IllegalArgumentException, IllegalStateException, IOException {
        if (!avatars.containsKey(id)) {
            return Optional.empty();
        }
        if (avatarPath != null && usrPath != null) {
            String getLocation = usrPath + avatarPath + avatars.get(id);
            log.info("get avatar from: " + getLocation);

            Path path = Paths.get(getLocation);
            return Optional.of(Files.readAllBytes(path));
        } else {
            throw new IllegalStateException("cannot find save path!");
        }
    }

    private void removeFile(String filePath){
        try{
            Files.deleteIfExists(Paths.get(filePath).toAbsolutePath());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveFile(InputStream is, String filePath){
        try{
            Files.write(Paths.get(filePath).toAbsolutePath(), is.readAllBytes());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
