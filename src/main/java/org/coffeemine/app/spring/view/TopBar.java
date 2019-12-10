package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.coffeemine.app.spring.components.LetterIcon;

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

        H3 brandName = new H3("CoffeeMine");
        brandName.getStyle().set("margin", "auto");
        brandName.getStyle().set("padding-left", "10px");

        brandLayout.add(brandName);

        tabLayout.add(new RoutingTabs());

        AccountPopUp accountInfo = new AccountPopUp();

        // TODO: Currently we're drawing a fullsize Div over the screen to detect clicks
        // anywhere, but this is not pretty, fix this natively in the future.
        Div clickhack = new Div();
        clickhack.addClassName("clickhack");
        clickhack.setVisible(false);

        String userName = "John Bob";
        LetterIcon AccountButton = new LetterIcon(userName.substring(0, 1));
        AccountButton.getStyle().set("font-size", "30px");

        clickhack.addClickListener(e -> {
            clickhack.setVisible(false);
            accountInfo.setVisible(false);
        });

        AccountButton.addClickListener(e -> {
            if (!accountInfo.isVisible()) {
                clickhack.setVisible(true);
                accountInfo.setVisible(true);
            } else {
                clickhack.setVisible(false);
                accountInfo.setVisible(false);
            }
        });

        miscLayout.add(AccountButton);

        this.add(brandLayout, tabLayout, miscLayout, clickhack, accountInfo);
    }
}
