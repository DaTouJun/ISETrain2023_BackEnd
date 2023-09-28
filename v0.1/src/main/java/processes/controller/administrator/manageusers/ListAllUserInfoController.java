package processes.controller.administrator.manageusers;

import connections.Connections;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageUserMenu;

import java.util.ArrayList;

public class ListAllUserInfoController implements Controllers {
    boolean userChanged;
    User currentUser;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        Connections con = Connections.getInstance();
        ArrayList<Storable> list = dao.UserDaoImpl.getInstance().queryAll();
        if (list == null) {
            con.sendData("查找不到任何用户");
        } else {
            con.sendData("这是所有的用户信息");
            for (var val : list) {
                User u = (User) val;
                con.sendData(u.toAdminString());
            }
            con.sendData("打印完成");
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        return new ManageUserMenu();
    }
}
