package org.coffeemine.app.spring;

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
        // When developing the application, please use an in-memory document store (uncomment the following line)
        NitriteDBProvider.init(null);
        // For production mode however, uncomment the two lines below
        //System.setProperty("spring.devtools.restart.enabled", "false");
        //NitriteDBProvider.init("CoffeeMine.no2");

        if (NitriteDBProvider.getInstance().is_first_run())
            TestingData.load();

        SpringApplication.run(Application.class, args);
    }

}
