package processes.controller.normalUser;

import connections.Connections;
import dao.DaoInterface;
import dao.ShoppingCartDaoImpl;
import dao.ShoppingHistoryDaoImpl;
import pojo.CartItem;
import pojo.Deal;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
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
        System.out.println("进入购物车 ：");
        ArrayList<CartItem> list = dao.ShoppingCartDaoImpl.getInstance().queryUserID(user.getID());
        double totalPrice = 0;
        if (list == null) {
            System.out.println("您的购物车为空 将返回");
            state = 0;
            return;
        }
        for (var val : list) {
            Connections.getInstance().sendData(val.showToCustomer());
            totalPrice += val.getPrice() * val.getNum();
        }
        System.out.println("总价格为: " + totalPrice + " , 是否结算?\n输入y进行结算否则输入n取消");
        String prompt = Connections.getInstance().getData();
        if (prompt.equals("y")) {
            System.out.println("调起结算接口(模拟)");
            DaoInterface dao = ShoppingHistoryDaoImpl.getInstance();
            for (var val : list) {
                ShoppingHistoryDaoImpl.getInstance().insert(new Deal(val));
                ShoppingCartDaoImpl.getInstance().deleteByID(val.getID());
            }
            System.out.println("完成结算(模拟),共花费" + totalPrice);
            user1.setTotalConsumptions(user1.getTotalConsumptions() + totalPrice);
            state = 1;
        } else {
            state = 0;
        }
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new UserFuncMenu();
        } else if (state == 1) {
            return new ShoppingHistoryController();
        }

        return new UserFuncMenu();
    }
}
