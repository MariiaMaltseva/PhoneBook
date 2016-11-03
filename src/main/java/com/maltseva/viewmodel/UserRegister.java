package com.maltseva.viewmodel;

import javax.validation.constraints.*;

@PasswordMatches
public class UserRegister {
    @Size(min = 5, message = "Your Full Name must be more than 4 characters")
    private String fullName;

    @Size(min = 3, message = "Your login must be more than 2 letters")
    @Pattern(regexp="[a-zA-Z]+", message = "Only Latin letters")
    private String login;

    @Size(min = 5, message = "Your password must be more than 4 characters")
    private String password;
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
