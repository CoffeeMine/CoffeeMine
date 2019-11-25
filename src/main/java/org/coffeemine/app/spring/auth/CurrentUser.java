package org.coffeemine.app.spring.auth;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

import java.util.Objects;

/**
 * Class for retrieving and setting the name of the current user of the current
 * session (without using JAAS). All methods of this class require that a
 * {@link VaadinRequest} is bound to the current thread.
 *
 *
 * @see com.vaadin.flow.server.VaadinService#getCurrentRequest()
 */
public final class CurrentUser {

    /**
     * The attribute key used to store the username in the session.
     */
    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class.getCanonicalName();

    private CurrentUser() {
    }

    /**
     * Returns the name of the current user stored in the current session, or an
     * empty string if no user name is stored.
     *
     * @throws IllegalStateException
     *             if the current session cannot be accessed.
     */
    public static String get() {
        return Objects.requireNonNullElse((String) getCurrentHttpSession().getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY), "");
    }

    private static WrappedSession getCurrentHttpSession() {
        final var va_session = VaadinSession.getCurrent();
        if (va_session == null) {
            throw new IllegalStateException("No session found for current thread");
        }
        return va_session.getSession();
    }

    /**
     * Sets the name of the current user and stores it in the current session.
     * Using a {@code null} username will remove the username from the session.
     *
     * @throws IllegalStateException
     *             if the current session cannot be accessed.
     */
    public static void set(String currentUser) {
        if (currentUser == null)
            getCurrentHttpSession().removeAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        else
            getCurrentHttpSession().setAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser);
    }

}
