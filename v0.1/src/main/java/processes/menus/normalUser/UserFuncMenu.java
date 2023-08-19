package processes.menus.normalUser;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.menus.Menus;

public class UserFuncMenu implements Menus {


    @Override
    public void showMenu(User user) {
        String[] menus = new String[5];
        menus[0] = "1.浏览商品";
        menus[1] = "2.进入购物车";
        menus[2] = "0.返回上一个菜单";
        Connections.getInstance().sendData(menus);
    }

    @Override
    public Processes getNextProcess(User user) {
        return null;
    }
}
