package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.HoursLogging;
import org.coffeemine.app.spring.components.LetterIcon;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TopBar extends HorizontalLayout {

    public TopBar() {
        this.setWidthFull();
        this.setHeight("48px");
        this.setClassName("mono_font");
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        HorizontalLayout brandLayout = new HorizontalLayout();
        brandLayout.getStyle().set("margin-right", "auto");

        HorizontalLayout tabLayout = new HorizontalLayout();

        HorizontalLayout miscLayout = new HorizontalLayout();
        miscLayout.getStyle().set("margin-left", "auto");

        final var branding = new Image("./icons/icon-mono.png", "");
        branding.setHeight("100%");

        H3 brandName = new H3("CoffeeMine");
        brandName.getStyle().set("margin", "auto");
        brandName.getStyle().set("padding-left", "10px");

        brandLayout.add(branding, brandName);

        tabLayout.add(new Button("Export", e -> Notification.show(NitriteDBProvider.getInstance().exportJSONProject(NitriteDBProvider.getInstance().getProject(CurrentUser.get().getCurrentProject())))));

        tabLayout.add(new RoutingTabs());

        final var log_hours = new Button("Log hours", e -> new HoursLogging());
        AccountPopUp accountInfo = new AccountPopUp();

        SearchPopUp search = new SearchPopUp();

        // TODO: Currently we're drawing a fullsize Div over the screen to detect clicks
        // anywhere, but this is not pretty, fix this natively in the future.
        Div clickhack = new Div();
        clickhack.addClassName("clickhack");
        clickhack.setVisible(false);

        User user = CurrentUser.get();
        String userName = (user == null) ? "No User" : user.getName();

        LetterIcon AccountButton = new LetterIcon(userName.substring(0, 1));
        AccountButton.getStyle().set("font-size", "30px");
        AccountButton.getStyle().set("margin", "0px");

        clickhack.addClickListener(e -> {
            clickhack.setVisible(false);
            accountInfo.setVisible(false);
            search.setVisible(false);
        });

        final var searchButton = new Button(new Icon(VaadinIcon.SEARCH), e -> {
            if (!clickhack.isVisible()) {
                clickhack.setVisible(true);
                search.setVisible(true);
                search.focus();
            } else {
                clickhack.setVisible(false);
                search.setVisible(false);
            }
        });

        AccountButton.addClickListener(e -> {
            if (!clickhack.isVisible()) {
                clickhack.setVisible(true);
                accountInfo.setVisible(true);
            } else {
                clickhack.setVisible(false);
                accountInfo.setVisible(false);
            }
        });

        miscLayout.add(log_hours, searchButton, AccountButton);

        this.add(brandLayout, tabLayout, miscLayout, clickhack, accountInfo, search);
    }
}
