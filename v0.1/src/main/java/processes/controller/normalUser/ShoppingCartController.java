package processes.controller.normalUser;

import connections.Connections;
import dao.DaoInterface;
import dao.ShoppingHistoryDaoImpl;
import pojo.CartItem;
import pojo.Deal;
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
        System.out.println("总价格为: " + totalPrice + " , 是否结算?\n输入y进行结算否则输入n取消");
        String prompt = Connections.getInstance().getData();
        if (prompt.equals("y")){
            System.out.println("调起结算接口(模拟)");
            DaoInterface dao = ShoppingHistoryDaoImpl.getInstance();
            for (var val : list){
                dao.insert(new Deal(val));
            }
            System.out.println("完成结算(模拟),共花费" + totalPrice);
            state = 1;
        } else{
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
        } else if (state == 1){
            return new ShoppingHistoryController();
        }

        return new UserFuncMenu();
    }
}
