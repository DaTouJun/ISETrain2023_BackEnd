package processes.controller.normalUser;

import connections.Connections;
import pojo.CartItem;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.WelcomeMenu;
import processes.menus.normalUser.UserFuncMenu;

import java.util.ArrayList;

public class ShoppingCartController implements Controllers {
    boolean userChanged;
    User user;
    int state = 0;


    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user1) {
        user = user1;
        System.out.println("这是您购物车内的物品：");
        ArrayList<CartItem> list = dao.ShoppingCartDaoImpl.getInstance().queryUserID(user.getID());
        double totalPrice = 0;
        for (var val : list) {
            Connections.getInstance().sendData(val.showToCustomer());
            totalPrice += val.getPrice() * val.getNum();
        }
        System.out.println("总价格为: " + totalPrice + " , 是否结算?");
        // TODO
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new UserFuncMenu();
        }  // TODO:

        return new UserFuncMenu();
    }
}
