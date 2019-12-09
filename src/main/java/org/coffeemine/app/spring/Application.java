package org.coffeemine.app.spring;

import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@ComponentScan(value = "org.coffeemine.app.spring.properties")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        NitriteDBProvider.init(null);
        NitriteDBProvider.getInstance().addUser(new User(0, "Admoon", User.Status.ADMIN, -1.0f, "admoon", "Foobar"));
        SpringApplication.run(Application.class, args);
    }

}
