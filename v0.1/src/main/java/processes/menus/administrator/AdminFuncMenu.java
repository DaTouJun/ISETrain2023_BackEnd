package processes.menus.administrator;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.menus.Menus;

import java.awt.*;

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
        return null;
    }
}
