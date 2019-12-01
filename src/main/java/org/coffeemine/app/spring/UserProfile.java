package org.coffeemine.app.spring;

import com.vaadin.flow.router.Route;

@Route
public class UserProfile extends View {

    UserProfile() {
    super();
    add(new profileView());


}
}




