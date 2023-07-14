package menus;

import controller.Controllers;
import controller.LoginController;
import controller.RegisterController;
import controller.ResetPasswordController;

public class LoginMenu implements Menus {
    @Override
    public String[] getMenus() {
        String [] menus = new String[3];
        menus[0] = "1.登录";
        menus[1] = "2.注册";
        menus[2] = "3.忘记密码";
        return menus;
    }

    @Override
    public Controllers getController(int parseFunction) {
        return switch (parseFunction) {
            case 1 -> new LoginController();
            case 2 -> new RegisterController();
            case 3 -> new ResetPasswordController();
            default -> null;
        };
    }
}
