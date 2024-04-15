package com.example.aida.Utils;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import java.util.regex.Pattern;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputUtils {
    private static final PolicyFactory htmlSanitizer = Sanitizers.FORMATTING;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    );

    // Egyptian Phone number pattern: +20 is optional, followed by 10, 11, 12, or 15, and then 8 digits
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(?:\\+?20)?(?:10|11|12|15)\\d{8}$\n"
    );
    // Password pattern: at least 8 characters, at least one digit, one lowercase letter, one uppercase letter, and one special character
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    );

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private InputUtils() {}

    // Trim leading and trailing whitespace
    public static String trimWhitespace(String input) {
        return input.trim();
    }

    public static boolean isNumeric(String input) {
        return Pattern.matches("[0-9]+", input);
    }

    public static boolean isValidEmail(String input) {
        return EMAIL_PATTERN.matcher(input).matches();
    }

    public static boolean isValidPhoneNumber(String input) {
        return PHONE_PATTERN.matcher(input).matches();
    }

    public static boolean isStrongPassword(String input) {
        return PASSWORD_PATTERN.matcher(input).matches();
    }

    public static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    // Check if input is a valid URL by creating a URL object
    public static boolean isValidURL(String input) {
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    // Sanitize HTML input: remove all HTML tags and attributes
    public static String sanitizeHtml(String input) {
        return htmlSanitizer.sanitize(input);
    }


}
