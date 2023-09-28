package processes.menus.administrator;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.administrator.manageusers.DeleteUserController;
import processes.controller.administrator.manageusers.ListAllUserInfoController;
import processes.controller.administrator.manageusers.SearchUserController;
import processes.menus.Menus;

public class ManageUserMenu implements Menus {
    @Override
    public void showMenu(User user) {
        String[] menus = new String[6];
        menus[0] = "|   管理员用户管理功能页面   |";
        menus[1] = "1.列出所有用户";
        menus[2] = "2.查找特定用户信息";
        menus[3] = "3.删除特定用户";
        menus[4] = "0.返回管理员用户菜单";
        menus[5] = "|                       |";
        Connections.getInstance().sendData(menus);
    }

    @Override
    public Processes getNextProcess(User user) {
        int nextInstruction;
        try {
            nextInstruction = Integer.parseInt(Connections.getInstance().getData());
        } catch (NumberFormatException e) {
            System.out.println("请输入特定的菜单序号 您的输入错误");
//            e.printStackTrace();
            return new AdminFuncMenu();
        }
        return switch (nextInstruction) {
            case 1 -> new ListAllUserInfoController();
            case 2 -> new SearchUserController();
            case 3 -> new DeleteUserController();
            default -> new AdminFuncMenu();
        };
    }
}
