package com.github.lorellw.dictionary3000.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;


public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H3 logo =new H3("Dictionary 3000");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        header.expand(logo);
        header.setWidth("99%");
        header.addClassNames("py-0","px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink dictionaryLink = new RouterLink("3000",DictionaryView.class);
        //dictionaryLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink wordcardsLink = new RouterLink("Word cards", WordcardsView.class);
        //wordcardsLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink enToRuLink = new RouterLink("EN to RU", enToRuView.class);
        RouterLink ruToEnLink = new RouterLink("RU to EN", ruToEnView.class);

        RouterLink aboutLink = new RouterLink("About", AboutView.class);
        //aboutLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(
                new VerticalLayout(
                        dictionaryLink,
                        wordcardsLink,
                        enToRuLink,
                        ruToEnLink,
                        aboutLink
                )
        );
    }
}
