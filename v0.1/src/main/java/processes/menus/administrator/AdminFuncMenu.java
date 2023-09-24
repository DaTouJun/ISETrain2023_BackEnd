package processes.menus.administrator;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.administrator.ManageUserController;
import processes.controller.administrator.PasswordController;
import processes.menus.Menus;
import processes.menus.WelcomeMenu;

public class AdminFuncMenu implements Menus {
    @Override
    public void showMenu(User user) {
        String[] menus = new String[4];
        menus[0] = "1.客户管理";
        menus[1] = "2.商品管理";
        menus[2] = "3.密码管理";
        menus[3] = "0.返回登录菜单";
        Connections.getInstance().sendData(menus);
    }

    @Override
    public Processes getNextProcess(User user) {
        int nextInstructions = -1;
        try {
            nextInstructions = Integer.decode(Connections.getInstance().getData());
        } catch (Exception e) {
            System.out.println("输入的字符错误，应为数字");
            return new WelcomeMenu();
        }

        return switch (nextInstructions) {
            case 1 -> new ManageUserController();
            case 2 -> new ManageItemsMenu();
            case 3 -> new PasswordController();
            case 0 -> new AdminFuncMenu();
            default -> {
                System.out.println("输入错误，请重新输入");
                yield new AdminFuncMenu();
            }
        };
    }
}
