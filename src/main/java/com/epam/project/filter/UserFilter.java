package com.epam.project.filter;

import com.epam.project.constants.Path;
import com.epam.project.entity.Role;
import com.epam.project.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    public static final Logger log = Logger.getLogger(UserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            User user = (User) httpSession.getAttribute("user");
            log.info("UserFilter " + user);
            if (user != null && user.getRole().equals(Role.CLIENT)) {
                filterChain.doFilter(request, response);
            } else response.sendRedirect(Path.SECURITY_ERROR);
        } else response.sendRedirect(Path.SECURITY_ERROR);
    }

    @Override
    public void destroy() {

    }
}
