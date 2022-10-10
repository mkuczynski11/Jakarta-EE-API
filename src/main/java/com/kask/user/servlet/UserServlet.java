package com.kask.user.servlet;

import com.kask.servlet.MimeTypes;
import com.kask.servlet.ServletUtility;
import com.kask.user.dto.GetUserResponse;
import com.kask.user.dto.GetUsersResponse;
import com.kask.user.entity.User;
import com.kask.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = {
        UserServlet.Paths.USER + "/*",
        UserServlet.Paths.USERS + "/*"
})
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Inject
    public UserServlet(UserService userService) {this.userService = userService;}

    public static class Paths {
        public static final String USER = "/api/user";
        public static final String USERS = "/api/users";
    }

    public static class Patterns {
        public static final String USER = "^/[0-9]+/?$";
        public static final String USERS = "^/?$";
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        log.info("path = " + path);
        log.info("servletPath = " + servletPath);
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            }
        } else if (Paths.USER.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.getAllUsers())));
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.getUser(id);

        if (user.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
