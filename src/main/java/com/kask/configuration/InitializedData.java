package com.kask.configuration;

import com.kask.digest.Sha256Utility;
import com.kask.user.entity.User;
import com.kask.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class InitializedData {

    private final UserService userService;

    @Inject
    public InitializedData(UserService userService) {
        this.userService = userService;
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

        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);
        userService.createUser(user4);
    }
}
