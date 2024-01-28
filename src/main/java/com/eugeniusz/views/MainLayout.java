package com.eugeniusz.views;

import com.eugeniusz.data.User;
import com.eugeniusz.security.AuthenticatedUser;
import com.eugeniusz.views.about.AboutView;
import com.eugeniusz.views.addressform.AddressFormView;
import com.eugeniusz.views.chat.ChatView;
import com.eugeniusz.views.checkoutform.CheckoutFormView;
import com.eugeniusz.views.collaborativemasterdetail.CollaborativeMasterDetailView;
import com.eugeniusz.views.creditcardform.CreditCardFormView;
import com.eugeniusz.views.dashboard.DashboardView;
import com.eugeniusz.views.datagrid.DataGridView;
import com.eugeniusz.views.feed.FeedView;
import com.eugeniusz.views.gridwithfilters.GridwithFiltersView;
import com.eugeniusz.views.helloworld.HelloWorldView;
import com.eugeniusz.views.imagegallery.ImageGalleryView;
import com.eugeniusz.views.map.MapView;
import com.eugeniusz.views.masterdetail.MasterDetailView;
import com.eugeniusz.views.pageeditor.PageEditorView;
import com.eugeniusz.views.personform.PersonFormView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Whitespace;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import java.io.ByteArrayInputStream;
import java.util.Optional;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

    }

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        addToNavbar(createHeaderContent());
        setDrawerOpened(false);
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("FeignCrudTiN");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        layout.add(appName);

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getName());
            StreamResource resource = new StreamResource("profile-pic",
                    () -> new ByteArrayInputStream(user.getProfilePicture()));
            avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }

        }

        header.add(layout, nav);
        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Hello World", LineAwesomeIcon.GLOBE_SOLID.create(), HelloWorldView.class), //

                new MenuItemInfo("About", LineAwesomeIcon.FILE.create(), AboutView.class), //

                new MenuItemInfo("Dashboard", LineAwesomeIcon.CHART_AREA_SOLID.create(), DashboardView.class), //

                new MenuItemInfo("Feed", LineAwesomeIcon.LIST_SOLID.create(), FeedView.class), //

                new MenuItemInfo("Data Grid", LineAwesomeIcon.TH_SOLID.create(), DataGridView.class), //

                new MenuItemInfo("Master-Detail", LineAwesomeIcon.COLUMNS_SOLID.create(), MasterDetailView.class), //

                new MenuItemInfo("Collaborative Master-Detail", LineAwesomeIcon.COLUMNS_SOLID.create(),
                        CollaborativeMasterDetailView.class), //

                new MenuItemInfo("Person Form", LineAwesomeIcon.USER.create(), PersonFormView.class), //

                new MenuItemInfo("Address Form", LineAwesomeIcon.MAP_MARKER_SOLID.create(), AddressFormView.class), //

                new MenuItemInfo("Credit Card Form", LineAwesomeIcon.CREDIT_CARD.create(), CreditCardFormView.class), //

                new MenuItemInfo("Map", LineAwesomeIcon.MAP.create(), MapView.class), //

                new MenuItemInfo("Chat", LineAwesomeIcon.COMMENTS.create(), ChatView.class), //

                new MenuItemInfo("Page Editor", LineAwesomeIcon.EDIT.create(), PageEditorView.class), //

                new MenuItemInfo("Image Gallery", LineAwesomeIcon.TH_LIST_SOLID.create(), ImageGalleryView.class), //

                new MenuItemInfo("Checkout Form", LineAwesomeIcon.CREDIT_CARD.create(), CheckoutFormView.class), //

                new MenuItemInfo("Grid with Filters", LineAwesomeIcon.FILTER_SOLID.create(), GridwithFiltersView.class), //

        };
    }

}
