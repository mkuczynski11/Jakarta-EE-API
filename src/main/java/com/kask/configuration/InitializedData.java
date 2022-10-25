package com.kask.configuration;

import com.kask.achievement.entity.Achievement;
import com.kask.game.entity.Game;
import com.kask.user.entity.User;
import com.kask.user.entity.UserRole;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.List;

@Singleton
@Startup
@NoArgsConstructor
public class InitializedData {
    private EntityManager entityManager;
    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public InitializedData(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    private synchronized void init(){

        User user1 = User.builder()
                .username("mkuczyns")
                .name("Martin")
                .surname("Kuczyński")
                .email("mkuczynski11.kontakt@gmail.com")
                .password(pbkdf.generate("pass".toCharArray()))
                .roles(List.of(UserRole.ADMIN))
                .build();
        User user2 = User.builder()
                .username("andrzej_brazda")
                .name("Andrzej")
                .surname("Brazda")
                .email("andrzej.kontakt@gmail.com")
                .password(pbkdf.generate("pass".toCharArray()))
                .roles(List.of(UserRole.USER))
                .build();
        User user3 = User.builder()
                .username("wb123")
                .name("Wojciech")
                .surname("Biela")
                .email("wb123@gmail.com")
                .password(pbkdf.generate("pass".toCharArray()))
                .roles(List.of(UserRole.USER))
                .build();
        User user4 = User.builder()
                .username("aaAA")
                .name("Alojzy")
                .surname("Alojzy")
                .email("AAaa@gmail.com")
                .password(pbkdf.generate("pass".toCharArray()))
                .roles(List.of(UserRole.USER))
                .build();

        List.of(user1, user2, user3, user4).forEach(entityManager::persist);

        Game game1 = Game.builder()
                .name("Starcraft 2")
                .developer("Activision")
                .price(20)
                .build();
        Game game2 = Game.builder()
                .name("League of legends")
                .developer("Riot games")
                .price(0)
                .build();
        Game game3 = Game.builder()
                .name("Fifa 23")
                .developer("Electronic arts")
                .price(250)
                .build();

        List.of(game1, game2, game3).forEach(entityManager::persist);

        Achievement s2_achievement1 = Achievement.builder()
                .id(1)
                .name("Destroy the base")
                .ownedPercentage(0.98)
                .reward(100)
                .game(game1)
                .user(user1)
                .build();
        Achievement s2_achievement2 = Achievement.builder()
                .id(2)
                .name("Win game")
                .ownedPercentage(0.90)
                .reward(200)
                .game(game1)
                .user(user1)
                .build();
        Achievement s2_achievement3 = Achievement.builder()
                .id(3)
                .name("Play online game")
                .ownedPercentage(0.88)
                .reward(10)
                .game(game1)
                .user(user1)
                .build();

        List.of(s2_achievement1, s2_achievement2, s2_achievement3).forEach(entityManager::persist);

        Achievement lol_achievement1 = Achievement.builder()
                .id(4)
                .name("Get 10 kills")
                .ownedPercentage(0.6)
                .reward(10)
                .game(game2)
                .user(user2)
                .build();
        Achievement lol_achievement2 = Achievement.builder()
                .id(5)
                .name("Win game")
                .ownedPercentage(0.90)
                .reward(10)
                .game(game2)
                .user(user2)
                .build();
        Achievement lol_achievement3 = Achievement.builder()
                .id(6)
                .name("Buy a skin")
                .ownedPercentage(0.5)
                .reward(10)
                .game(game2)
                .user(user2)
                .build();

        List.of(lol_achievement1, lol_achievement2, lol_achievement3).forEach(entityManager::persist);

        Achievement fifa_achievement1 = Achievement.builder()
                .id(7)
                .name("Score 5 goals in one game")
                .ownedPercentage(0.3)
                .reward(1000)
                .game(game3)
                .user(user3)
                .build();
        Achievement fifa_achievement2 = Achievement.builder()
                .id(8)
                .name("Win game")
                .ownedPercentage(0.96)
                .reward(15)
                .game(game3)
                .user(user3)
                .build();
        Achievement fifa_achievement3 = Achievement.builder()
                .id(9)
                .name("Play with Ronaldo and Messi in one team")
                .ownedPercentage(0.1)
                .reward(1500)
                .game(game3)
                .user(user3)
                .build();

        List.of(fifa_achievement1, fifa_achievement2, fifa_achievement3).forEach(entityManager::persist);

    }
}
