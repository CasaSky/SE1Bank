package se1app.applicationcore.util;

import java.io.Serializable;

/**
 * Created by srs on 08.12.15.
 */
public class EmailType implements Serializable {

    /**
     * @see <a href="http://howtodoinjava.com/2014/11/11/java-regex-validate-email-address/">E-Mail-Validation</a>
     */
    private static final String EMAIL_PATTERN =
        "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private String email;

    public EmailType(String email)
    {
        if (!isValidEmailAddress(email))
        {
            throw new IllegalArgumentException("not a valid email address:" + email);
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public static boolean isValidEmailAddress(String email) {
        return email.matches(EMAIL_PATTERN);
    }
}
