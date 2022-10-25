package com.kask.achievement.service;

import com.kask.achievement.entity.Achievement;
import com.kask.achievement.repository.AchievementRepository;
import com.kask.user.entity.User;
import com.kask.user.entity.UserRole;
import com.kask.user.repository.UserRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class AchievementService {

    private AchievementRepository achievementRepository;
    private SecurityContext securityContext;
    private UserRepository userRepository;

    @Inject
    public AchievementService(AchievementRepository achievementRepository, SecurityContext securityContext, UserRepository userRepository) {
        this.achievementRepository = achievementRepository;
        this.securityContext = securityContext;
        this.userRepository = userRepository;
    }

    public void createAchievement(Achievement achievement) {
        if (securityContext.getCallerPrincipal() != null) {
            Optional<User> user = userRepository.get(securityContext.getCallerPrincipal().getName());
            if (user.isPresent()) {
                achievement.setUser(user.get());
                achievementRepository.create(achievement);
                return;
            }
        }
        throw new WebApplicationException(Response.Status.UNAUTHORIZED);
    }

    public Optional<Achievement> getAchievement(int achievementId) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return achievementRepository.get(achievementId);
        } else if (securityContext.getCallerPrincipal() != null) {
            return achievementRepository.getByUser(securityContext.getCallerPrincipal().getName(), achievementId);
        }
        return Optional.empty();
    }

    public Optional<Achievement> getAchievementByGame(int achievementId, String gameName) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return achievementRepository.getByGame(gameName, achievementId);
        } else if (securityContext.getCallerPrincipal() != null) {
            return achievementRepository.getByGameAndUser(gameName, securityContext.getCallerPrincipal().getName(), achievementId);
        }
        return Optional.empty();
    }

    public List<Achievement> getAllAchievements() {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return achievementRepository.getAll();
        } else if (securityContext.getCallerPrincipal() != null) {
            return achievementRepository.getAllByUser(securityContext.getCallerPrincipal().getName());
        }
        return Collections.emptyList();
    }

    public List<Achievement> getAllByGame(String gameName) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return achievementRepository.getAllByGame(gameName);
        } else if (securityContext.getCallerPrincipal() != null) {
            return achievementRepository.getAllByGameAndUser(gameName, securityContext.getCallerPrincipal().getName());
        }
        return Collections.emptyList();
    }

    public void updateAchievement(Achievement achievement) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            achievementRepository.update(achievement);
        } else if (securityContext.getCallerPrincipal() != null && achievement.getUser().getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            achievementRepository.update(achievement);
        } else {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

    public void deleteAchievement(Achievement achievement) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            achievementRepository.delete(achievement);
        } else if (securityContext.getCallerPrincipal() != null && achievement.getUser().getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            achievementRepository.delete(achievement);
        } else {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
