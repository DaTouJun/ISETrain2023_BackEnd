package processes.menus.administrator;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.administrator.manageitems.AddItemController;
import processes.controller.administrator.manageitems.DeleteItemController;
import processes.controller.administrator.manageitems.EditItemController;
import processes.controller.administrator.manageitems.ListAllItemController;
import processes.menus.Menus;

public class ManageItemsMenu implements Menus {

    @Override
    public void showMenu(User user) {
        String[] menus = new String[5];
        menus[0] = "1.列出所有商品";
        menus[1] = "2.添加商品";
        menus[2] = "3.移除商品";
        menus[3] = "4.修改商品信息";
        menus[4] = "0.返回主功能菜单";
        Connections.getInstance().sendData(menus);
    }

    @Override
    public Processes getNextProcess(User user) {
        int nextInstructions = -1;
        try {
            nextInstructions = Integer.decode(Connections.getInstance().getData());
        } catch (Exception e) {
            System.out.println("输入的字符错误，应为数字");
            return new AdminFuncMenu();
        }

        return switch (nextInstructions) {
            case 1 -> new ListAllItemController();
            case 2 -> new AddItemController();
            case 3 -> new DeleteItemController();
            case 4 -> new EditItemController();
            case 0 -> new AdminFuncMenu();
            default -> {
                System.out.println("输入错误，请重新输入");
                yield new ManageItemsMenu();
            }
        };
    }
}
