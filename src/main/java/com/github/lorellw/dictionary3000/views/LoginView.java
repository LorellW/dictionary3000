package com.github.lorellw.dictionary3000.views;

import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.services.UserService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final UserService userService;

    private final LoginForm loginForm = new LoginForm();

    public LoginView(UserService userService) {
        this.userService = userService;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);

        add(new H3("Dictionary 3000"), loginForm, createRegistrationButton());

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }

    private Button createRegistrationButton() {
        Button registrationButton = new Button("Registration");
        registrationButton.setId("registration-button");
        registrationButton.addClickListener(event -> new RegistrationDialog(userService).open());
        return registrationButton;
    }


    private static class RegistrationDialog extends Dialog {
        private final TextField usernameTextField = new TextField("Login/Username");
        private final PasswordField passwordField = new PasswordField("Password");
        private final PasswordField confirmPasswordField = new PasswordField("Confirm password");
        private final Button singUpButton = new Button("Sing Up");
        private final UserService userService;


        private RegistrationDialog(UserService userService) {
            this.userService = userService;
            add(createDialogLayout());
        }

        private VerticalLayout createDialogLayout() {

            configFields();
            configSingUpButton();

            Button closeButton = new Button("Close", e -> this.close());
            closeButton.setId("close-button");
            HorizontalLayout buttonLayout = new HorizontalLayout(closeButton, singUpButton);

            usernameTextField.setId("login-input");
            passwordField.setId("pass-input");
            confirmPasswordField.setId("confirm-pass-input");

            return new VerticalLayout(usernameTextField,passwordField,confirmPasswordField,buttonLayout);
        }

        private void configSingUpButton() {
            singUpButton.setId("sign-up-button");
            singUpButton.addClickShortcut(Key.ENTER);
            singUpButton.addClickListener(buttonClickEvent -> {
                if (passwordField.getValue().equals(confirmPasswordField.getValue())) {
                    if (userService.loadUserByUsername(usernameTextField.getValue()) == null){
                        User user = new User();
                        user.setUsername(usernameTextField.getValue());
                        user.setPassword(passwordField.getValue());
                        userService.addUser(user);
                        this.close();
                    } else {
                        createInfoDialog("Username already exists").open();
                    }
                } else {
                    createInfoDialog("Password is not confirmed").open();
                }
            });
        }

        private void configFields() {
            usernameTextField.addValueChangeListener(event -> singUpButton.setEnabled(confirm()));
            passwordField.addValueChangeListener(event -> singUpButton.setEnabled(confirm()));
            confirmPasswordField.addValueChangeListener(event -> singUpButton.setEnabled(confirm()));
        }

        private boolean confirm() {
            return !usernameTextField.isEmpty() && !passwordField.isEmpty() && !confirmPasswordField.isEmpty();
        }

        private Dialog createInfoDialog(String info){
            Dialog infoDialog = new Dialog();
            infoDialog.setId("info-dialog");
            infoDialog.setHeaderTitle("Message");
            Button cancelButton = new Button("Cancel", e-> infoDialog.close());

            infoDialog.add(info);
            infoDialog.getFooter().add(cancelButton);
            return infoDialog;
        }
    }
}
