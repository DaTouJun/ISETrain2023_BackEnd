package processes.controller.administrator.manageitems;

import connections.Connections;
import pojo.Item;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageItemsMenu;

import java.util.ArrayList;


public class ListAllItemController implements Controllers {
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
        Connections con = Connections.getInstance();
        con.sendData("这是所有物品");
        ArrayList<Storable> items = dao.ItemDaoImpl.getInstance().queryAll();
        for (Storable sI : items){
            Item item = (Item) sI;
            con.sendData(item.toString());
        }
        con.sendData("返回物品管理菜单 \n");
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {

        return new ManageItemsMenu();
    }
}
