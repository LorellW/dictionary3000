package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.entities.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {
    private static final String LOGOUT_SUCCESS_URL = "/";

    public UserDetails getAuthenticatedUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        return (User) context.getAuthentication().getPrincipal();
    }

    public void logout(){
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(),
                null,null);
    }
}
