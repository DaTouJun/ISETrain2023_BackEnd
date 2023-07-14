package menus;

import controller.Controllers;

public interface Menus {
    String[] getMenus();
    Controllers getController(int parseFunction);
}
