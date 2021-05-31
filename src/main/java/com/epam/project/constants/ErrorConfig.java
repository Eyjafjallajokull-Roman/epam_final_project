package com.epam.project.constants;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorConfig {

    private static final ErrorConfig instance = new ErrorConfig();
    private final ResourceBundle errors;
    private Locale locale;

    public ErrorConfig() {
        this.locale = Locale.getDefault();
        this.errors = ResourceBundle.getBundle("error", locale);
    }

    public static ErrorConfig getInstance() {
        return instance;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getErrorMessage(String key) {
        String message;
        message = errors.getObject(key).toString();
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        message = new String(bytes, StandardCharsets.UTF_8);
        return message;
    }

}


