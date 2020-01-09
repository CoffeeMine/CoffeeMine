package org.coffeemine.app.spring.auth;

import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;

import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public final class CurrentUser {
    private static int currentUserId = -1;

    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class.getCanonicalName();

    private CurrentUser() {
    }

    public static User get() {
        return NitriteDBProvider.getInstance().getUser(currentUserId);
    }

    private static WrappedSession getCurrentHttpSession() {
        final var va_session = VaadinSession.getCurrent();
        if (va_session == null) {
            throw new IllegalStateException("No session found for current thread");
        }
        return va_session.getSession();
    }

    public static void set(Integer userId) {
        if (userId == null) {
            getCurrentHttpSession().removeAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
            currentUserId = -1;
        } else {
            getCurrentHttpSession().setAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY, userId);
            currentUserId = userId;
        }
    }

}
