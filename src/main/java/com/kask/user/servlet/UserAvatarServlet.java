package com.kask.user.servlet;

import com.kask.servlet.HttpHeaders;
import com.kask.servlet.MimeTypes;
import com.kask.servlet.ServletUtility;
import com.kask.user.entity.User;
import com.kask.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@WebServlet(urlPatterns = {
        UserAvatarServlet.Paths.AVATAR + "/*"
})
@MultipartConfig(maxFileSize = 800 * 1024)
public class UserAvatarServlet extends HttpServlet {

    private UserService userService;

    @Inject
    public UserAvatarServlet(UserService userService) {
        this.userService = userService;
    }

    public static class Paths {
        public static final String AVATAR = "/api/avatar";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[0-9]+/?$";
    }

    public static class Parameters {
        public static final String AVATAR = "avatar";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        log.info("path = " + path);
        log.info("servletPath = " + servletPath);
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                log.info("Getting avatar");
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                log.info("Creating avatar");
                postAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                log.info("Deleting avatar");
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                log.info("Updating avatar");
                updateAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.getUser(id);

        if (user.isPresent() && user.get().getAvatar() != null) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(user.get().getAvatar().length);
            response.getOutputStream().write(user.get().getAvatar());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Part avatar = request.getPart(Parameters.AVATAR);
        Optional<User> user = userService.getUser(id);
        if (avatar != null && user.isPresent() && user.get().getAvatar() == null) {
            userService.updateAvatar(user.get(), avatar.getInputStream());
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.getUser(id);
        if (user.isPresent() && user.get().getAvatar() != null) {
            userService.deleteAvatar(user.get());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Part avatar = request.getPart(Parameters.AVATAR);
        Optional<User> user = userService.getUser(id);
        if (avatar != null && user.isPresent()) {
            userService.updateAvatar(user.get(), avatar.getInputStream());
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
