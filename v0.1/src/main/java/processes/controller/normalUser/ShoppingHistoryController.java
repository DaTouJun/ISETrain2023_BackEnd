package processes.controller.normalUser;

import dao.ShoppingHistoryDaoImpl;
import pojo.Deal;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.normalUser.UserFuncMenu;

import java.util.ArrayList;

public class ShoppingHistoryController implements Controllers {
    boolean userChanged;
    User user;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        this.user = user;
        System.out.println("这里是您的购物历史");
        ArrayList<Deal> list = ShoppingHistoryDaoImpl.getInstance().queryUserID(user.getID());
        for (var val : list)
            System.out.println(val.toUserViewString());
        System.out.println("显示完成，返回用户菜单");
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Processes getProcess() {
        return new UserFuncMenu();
    }
}
