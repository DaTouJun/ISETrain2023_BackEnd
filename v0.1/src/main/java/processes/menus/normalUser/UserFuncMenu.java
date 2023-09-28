package processes.menus.normalUser;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.normalUser.AlterPasswordController;
import processes.controller.normalUser.ExploreItemsController;
import processes.controller.normalUser.ShoppingCartController;
import processes.controller.normalUser.ShoppingHistoryController;
import processes.menus.Menus;
import processes.menus.WelcomeMenu;

public class UserFuncMenu implements Menus {


    @Override
    public void showMenu(User user) {
        String[] menus = new String[5];
        menus[0] = "1.浏览商品";
        menus[1] = "2.进入购物车";
        menus[2] = "3.查看购物历史";
        menus[3] = "4.修改密码";
        menus[4] = "0.退出";
        Connections.getInstance().sendData(menus);
    }

    @Override
    public Processes getNextProcess(User user) {
        int nextInstructions = -1;
        try {
            nextInstructions = Integer.decode(Connections.getInstance().getData());
        } catch (Exception e) {
            System.out.println("输入的字符错误，应为数字");
            return new UserFuncMenu();
        }

        return switch (nextInstructions) {
            case 1 -> new ExploreItemsController();
            case 2 -> new ShoppingCartController();
            case 3 -> new ShoppingHistoryController();
            case 4 -> new AlterPasswordController();
            case 0 -> new WelcomeMenu();
            default -> {
                System.out.println("输入错误，请重新输入");
                yield new UserFuncMenu();
            }
        };
    }
}
