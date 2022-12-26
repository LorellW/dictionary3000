package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.services.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value = "registration")
@PageTitle(value = "Registration")
@AnonymousAllowed

//TODO rewrite with vaadin binder
public class RegistrationView extends VerticalLayout {
    private TextField usernameTextField = new TextField("Login/Username");
    private PasswordField passwordField = new PasswordField("Password");
    private PasswordField confirmPasswordField = new PasswordField("Confirm password");
    private Button singUpButton = new Button("Sing Up");

    private final UserService userService;


    public RegistrationView(UserService userService) {
        this.userService = userService;

        configFields();
        configSingUpButton();

        setAlignItems(Alignment.CENTER);
        add(usernameTextField, passwordField, confirmPasswordField, singUpButton);
    }

    private void configFields() {
        usernameTextField.addValueChangeListener(event -> singUpButton.setEnabled(confirm()));
        passwordField.addValueChangeListener(event -> singUpButton.setEnabled(confirm()));
        confirmPasswordField.addValueChangeListener(event -> singUpButton.setEnabled(confirm()));
    }

    private void configSingUpButton() {
        singUpButton.addClickShortcut(Key.ENTER);
        singUpButton.addClickListener(buttonClickEvent -> {
            if (passwordField.getValue().equals(confirmPasswordField.getValue())) {
                User user = new User();
                user.setUsername(usernameTextField.getValue());
                user.setPassword(passwordField.getValue());
                userService.addUser(user);
            } else {
                createDialog().open();
            }
        });
    }

    private boolean confirm() {
        if (usernameTextField.isEmpty() || passwordField.isEmpty() || confirmPasswordField.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Title");

        return dialog;
    }
}
