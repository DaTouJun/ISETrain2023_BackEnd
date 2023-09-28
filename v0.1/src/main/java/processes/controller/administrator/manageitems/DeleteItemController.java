package processes.controller.administrator.manageitems;

import connections.Connections;
import pojo.Item;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageItemsMenu;

import java.util.ArrayList;


public class DeleteItemController implements Controllers {
    boolean userChanged;
    User currentUser;
    int state = 0;


    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User u) {
        currentUser = u;
        System.out.println("进入删除物品功能");
        System.out.println("请输入要删除的物品名称");
        String sname = Connections.getInstance().getData();
        ArrayList<Storable> itemList = dao.ItemDaoImpl.getInstance().queryLikeName(sname);
        for (Storable st : itemList) {
            Item item = (Item) st;
            Connections.getInstance().sendData(item.toUserString());
        }
        System.out.println("请输入要删除的物品ID");
        int id = 0;
        try {
            id = Integer.parseInt(Connections.getInstance().getData());
        } catch (NumberFormatException e) {
            System.out.println("输入的不是数字!");
            e.printStackTrace();
        }
        Item i = (Item) dao.ItemDaoImpl.getInstance().searchByID(id);
        if (i != null) {
            System.out.println("确认删除请输入y");
            String prompt = Connections.getInstance().getData();
            if (prompt.equals("y")) {
                dao.ItemDaoImpl.getInstance().deleteByID(id);
                System.out.println("已经删除");
            } else {
                System.out.println("取消删除");
            }
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new ManageItemsMenu();
        }

        return new ManageItemsMenu();
    }
}
