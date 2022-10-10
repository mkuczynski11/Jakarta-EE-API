package com.kask.user.servlet;

import com.kask.servlet.HttpHeaders;
import com.kask.servlet.MimeTypes;
import com.kask.servlet.ServletUtility;
import com.kask.user.service.UserAvatarService;
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

    private UserAvatarService userAvatarService;

    @Inject
    public UserAvatarServlet(UserAvatarService userAvatarService) {
        this.userAvatarService = userAvatarService;
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
        Optional<byte[]> avatar = userAvatarService.getAvatar(id);

        if (avatar.isPresent()) {
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(avatar.get().length);
            response.getOutputStream().write(avatar.get());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Part avatar = request.getPart(Parameters.AVATAR);
        if (avatar != null) {
            userAvatarService.createAvatar(avatar.getInputStream(), avatar.getSubmittedFileName(), id);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        if (userAvatarService.deleteAvatar(id)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = Integer.parseInt(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Part avatar = request.getPart(Parameters.AVATAR);
        if (avatar != null) {
            try {
                userAvatarService.updateAvatar(avatar.getInputStream(), avatar.getSubmittedFileName(), id);
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
