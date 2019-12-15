package org.coffeemine.app.spring;

import org.coffeemine.app.spring.data.TrackItem;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@ComponentScan(value = "org.coffeemine.app.spring.properties")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        NitriteDBProvider.init(null);
        final var db = NitriteDBProvider.getInstance();
        db.addUser(new User(0, "Admoon", User.Status.ADMIN, -1.0f, "admoon", "Foobar"));
        db.addTrackItem(new TrackItem(3, "Bob is broken", "Bob doesn't work since the update", 0,0, TrackItem.Type.BUG, true, TrackItem.Status.OPEN, TrackItem.Resolution.UNRESOLVED, LocalDateTime.now(), null));
        db.addTrackItem(new TrackItem(45, "Change color", "Green is better than yellow", 1,2, TrackItem.Type.FEATURE_REQUEST, true, TrackItem.Status.IN_PROGRESS, TrackItem.Resolution.FIXED, LocalDateTime.now(), LocalDateTime.now()));
        SpringApplication.run(Application.class, args);
    }

}
