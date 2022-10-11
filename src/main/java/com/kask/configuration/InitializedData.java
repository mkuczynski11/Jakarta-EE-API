package com.kask.configuration;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.service.AchievementService;
import com.kask.digest.Sha256Utility;
import com.kask.game.entity.Game;
import com.kask.game.service.GameService;
import com.kask.user.entity.User;
import com.kask.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final GameService gameService;
    private final AchievementService achievementService;

    @Inject
    public InitializedData(UserService userService, GameService gameService, AchievementService achievementService) {
        this.userService = userService;
        this.gameService = gameService;
        this.achievementService = achievementService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init(){
        User user1 = User.builder()
                .id(1)
                .name("Martin")
                .surname("Kuczyński")
                .email("mkuczynski11.kontakt@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.ADMIN)
                .build();
        User user2 = User.builder()
                .id(2)
                .name("Andrzej")
                .surname("Brazda")
                .email("andrzej.kontakt@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.USER)
                .build();
        User user3 = User.builder()
                .id(3)
                .name("Wojciech")
                .surname("Biela")
                .email("wb123@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.USER)
                .build();
        User user4 = User.builder()
                .id(4)
                .name("Alojzy")
                .surname("Alojzy")
                .email("AAaa@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.USER)
                .build();

        List.of(user1, user2, user3, user4).forEach(userService::createUser);

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

        List.of(game1, game2, game3).forEach(gameService::createGame);

        Achievement s2_achievement1 = Achievement.builder()
                .name("S2:Destroy the base")
                .ownedPercentage(Float.parseFloat("0.98"))
                .reward(100)
                .game(game1)
                .build();
        Achievement s2_achievement2 = Achievement.builder()
                .name("S2:Win game")
                .ownedPercentage(Float.parseFloat("0.90"))
                .reward(200)
                .game(game1)
                .build();
        Achievement s2_achievement3 = Achievement.builder()
                .name("S2:Play online game")
                .ownedPercentage(Float.parseFloat("0.88"))
                .reward(10)
                .game(game1)
                .build();

        List.of(s2_achievement1, s2_achievement2, s2_achievement3).forEach(achievementService::createAchievement);

        Achievement lol_achievement1 = Achievement.builder()
                .name("LOL:Get 10 kills")
                .ownedPercentage(Float.parseFloat("0.6"))
                .reward(10)
                .game(game2)
                .build();
        Achievement lol_achievement2 = Achievement.builder()
                .name("LOL:Win game")
                .ownedPercentage(Float.parseFloat("0.90"))
                .reward(10)
                .game(game2)
                .build();
        Achievement lol_achievement3 = Achievement.builder()
                .name("LOL:Buy a skin")
                .ownedPercentage(Float.parseFloat("0.5"))
                .reward(10)
                .game(game2)
                .build();

        List.of(lol_achievement1, lol_achievement2, lol_achievement3).forEach(achievementService::createAchievement);

        Achievement fifa_achievement1 = Achievement.builder()
                .name("FIFA:Score 5 goals in one game")
                .ownedPercentage(Float.parseFloat("0.3"))
                .reward(1000)
                .game(game3)
                .build();
        Achievement fifa_achievement2 = Achievement.builder()
                .name("FIFA:Win game")
                .ownedPercentage(Float.parseFloat("0.96"))
                .reward(15)
                .game(game3)
                .build();
        Achievement fifa_achievement3 = Achievement.builder()
                .name("FIFA:Play with Ronaldo and Messi in one team")
                .ownedPercentage(Float.parseFloat("0.1"))
                .reward(1500)
                .game(game3)
                .build();

        List.of(fifa_achievement1, fifa_achievement2, fifa_achievement3).forEach(achievementService::createAchievement);
    }
}
