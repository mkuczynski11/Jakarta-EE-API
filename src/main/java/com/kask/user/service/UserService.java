package com.kask.user.service;

import com.kask.user.entity.User;
import com.kask.user.entity.UserRole;
import com.kask.user.repository.UserRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private SecurityContext securityContext;
    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public UserService(UserRepository userRepository, SecurityContext securityContext, Pbkdf2PasswordHash pbkdf) {
        this.userRepository = userRepository;
        this.securityContext = securityContext;
        this.pbkdf = pbkdf;
    }

    public Optional<User> getUser(String username) {
        if (securityContext.isCallerInRole(UserRole.ADMIN) || (securityContext.getCallerPrincipal() != null && username.equals(securityContext.getCallerPrincipal().getName())))
        {
            return userRepository.get(username);
        }
        else {
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public void createUser(User user) {
        user.setRoles(List.of(UserRole.USER));
        user.setPassword(pbkdf.generate(user.getPassword().toCharArray()));
        userRepository.create(user);
    }

    public void deleteUser(User user) {userRepository.delete(user);}

    public void updateUser(User user) {
        if (securityContext.getCallerPrincipal() != null && user.getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            userRepository.update(user);
        } else {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

    public void updateRoles(User user) {
        if (securityContext.getCallerPrincipal() != null && user.getUsername().equals(securityContext.getCallerPrincipal().getName())) {
            userRepository.update(user);
        } else {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }

    public void updateAvatar(User user, InputStream is) {
        try {
            user.setAvatar(is.readAllBytes());
            updateUser(user);
        } catch (IOException io) {
            throw new IllegalStateException();
        }
    }

    public void deleteAvatar(User user){
        user.setAvatar(null);
        updateUser(user);
    }

}
