package processes.controller.administrator.manageusers;

import connections.Connections;
import pojo.Storable;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageUserMenu;

import java.util.ArrayList;

public class SearchUserController implements Controllers {
    @Override
    public boolean getUserChanged() {
        return false;   // 一定不会改
    }

    @Override
    public void startProcess(User user) {
        Connections.getInstance().sendData("请输入要查找的用户名(模糊搜索)");
        String uname = Connections.getInstance().getData();
        ArrayList<Storable> list = dao.UserDaoImpl.getInstance().queryLikeName(uname);
        if (list == null) {
            Connections.getInstance().sendData("通过您的输入查找不到任何用户");
        } else {
            for (var val : list) {
                User u = (User) val;
                Connections.getInstance().sendData(u.toAdminString());
            }
        }
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public Processes getProcess() {
        return new ManageUserMenu();
    }
}
