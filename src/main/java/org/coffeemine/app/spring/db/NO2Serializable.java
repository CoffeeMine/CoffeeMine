package org.coffeemine.app.spring.db;

import org.dizitart.no2.Document;

public interface NO2Serializable {
    Document asNO2Doc();
    NO2Serializable fromNO2Doc(Document doc);
}
