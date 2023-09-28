package processes.controller.normalUser;

import connections.Connections;
import pojo.CartItem;
import pojo.Item;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.normalUser.UserFuncMenu;

import java.util.ArrayList;

public class ExploreItemsController implements Controllers {
    boolean userChanged;
    int state = 0;
    User user;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user1) {
        this.user = user1;
        System.out.println("请输入要查找的物品名称（支持模糊搜索）");
        String itemName = Connections.getInstance().getData();
        ArrayList<Storable> items = dao.ItemDaoImpl.getInstance().queryLikeName(itemName);
        for (Storable st : items) {
            Item item = (Item) st;
            Connections.getInstance().sendData(item.toUserString());
        }
        System.out.println("是否要添加物品到购物车？要添加请输入y否则输入n");
        String prompt = Connections.getInstance().getData();
        if (prompt.equals("y")) {
            System.out.println("请输入添加的物品ID");
            try {
                int id = Integer.parseInt(Connections.getInstance().getData());
                System.out.println("请输入数量");
                int num = Integer.parseInt(Connections.getInstance().getData());
                Item item = (Item) dao.ItemDaoImpl.getInstance().searchByID(id);
                if (item == null) {
                    System.out.println("您添加了一个不存在的物品");
                } else {
                    if (num <= 0 || num > item.getNum()) {
                        System.out.println("输入的数量不正确 请重新开始");
                        state = 1;
                        return;
                    }
                    item.setNum(item.getNum() - num);
                    CartItem cartItem = new CartItem(item, user);
                    cartItem.setNum(num);
                    dao.ShoppingCartDaoImpl.getInstance().insert(cartItem);
                    dao.ItemDaoImpl.getInstance().updateByID(item);
                    System.out.println("添加成功");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        System.out.println("是否要继续搜索？是请输入y否则输入n");
        prompt = Connections.getInstance().getData();
        if (prompt.equals("y")) {
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
        if (state == 1) {
            return new ExploreItemsController();
        } else {
            return new UserFuncMenu();
        }
    }
}
