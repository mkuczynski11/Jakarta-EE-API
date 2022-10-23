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
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final GameService gameService;
    private final AchievementService achievementService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService, GameService gameService, AchievementService achievementService, RequestContextController requestContextController) {
        this.userService = userService;
        this.gameService = gameService;
        this.achievementService = achievementService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init(){
        requestContextController.activate();

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
                .id(1)
                .name("Destroy the base")
                .ownedPercentage(0.98)
                .reward(100)
                .game(game1)
                .build();
        Achievement s2_achievement2 = Achievement.builder()
                .id(2)
                .name("Win game")
                .ownedPercentage(0.90)
                .reward(200)
                .game(game1)
                .build();
        Achievement s2_achievement3 = Achievement.builder()
                .id(3)
                .name("Play online game")
                .ownedPercentage(0.88)
                .reward(10)
                .game(game1)
                .build();

        List.of(s2_achievement1, s2_achievement2, s2_achievement3).forEach(achievementService::createAchievement);

        Achievement lol_achievement1 = Achievement.builder()
                .id(4)
                .name("Get 10 kills")
                .ownedPercentage(0.6)
                .reward(10)
                .game(game2)
                .build();
        Achievement lol_achievement2 = Achievement.builder()
                .id(5)
                .name("Win game")
                .ownedPercentage(0.90)
                .reward(10)
                .game(game2)
                .build();
        Achievement lol_achievement3 = Achievement.builder()
                .id(6)
                .name("Buy a skin")
                .ownedPercentage(0.5)
                .reward(10)
                .game(game2)
                .build();

        List.of(lol_achievement1, lol_achievement2, lol_achievement3).forEach(achievementService::createAchievement);

        Achievement fifa_achievement1 = Achievement.builder()
                .id(7)
                .name("Score 5 goals in one game")
                .ownedPercentage(0.3)
                .reward(1000)
                .game(game3)
                .build();
        Achievement fifa_achievement2 = Achievement.builder()
                .id(8)
                .name("Win game")
                .ownedPercentage(0.96)
                .reward(15)
                .game(game3)
                .build();
        Achievement fifa_achievement3 = Achievement.builder()
                .id(9)
                .name("Play with Ronaldo and Messi in one team")
                .ownedPercentage(0.1)
                .reward(1500)
                .game(game3)
                .build();

        List.of(fifa_achievement1, fifa_achievement2, fifa_achievement3).forEach(achievementService::createAchievement);

        User user1 = User.builder()
                .id(1)
                .name("Martin")
                .surname("Kuczyński")
                .email("mkuczynski11.kontakt@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.ADMIN)
                .achievementList(List.of(s2_achievement1, lol_achievement1, fifa_achievement1))
                .build();
        User user2 = User.builder()
                .id(2)
                .name("Andrzej")
                .surname("Brazda")
                .email("andrzej.kontakt@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.USER)
                .achievementList(List.of(s2_achievement2, lol_achievement2, fifa_achievement2))
                .build();
        User user3 = User.builder()
                .id(3)
                .name("Wojciech")
                .surname("Biela")
                .email("wb123@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.USER)
                .achievementList(List.of(s2_achievement3, lol_achievement3, fifa_achievement3))
                .build();
        User user4 = User.builder()
                .id(4)
                .name("Alojzy")
                .surname("Alojzy")
                .email("AAaa@gmail.com")
                .password(Sha256Utility.hash("pass"))
                .role(User.Role.USER)
                .achievementList(List.of())
                .build();

        List.of(user1, user2, user3, user4).forEach(userService::createUser);

        requestContextController.deactivate();
    }
}
