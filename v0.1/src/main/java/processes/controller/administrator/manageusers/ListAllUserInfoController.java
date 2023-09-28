package processes.controller.administrator.manageusers;

import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageUserMenu;

public class ListAllUserInfoController implements Controllers {
    boolean userChanged;
    User currentUser;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {

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
