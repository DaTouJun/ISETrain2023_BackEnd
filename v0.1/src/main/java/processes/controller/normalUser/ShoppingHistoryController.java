package processes.controller.normalUser;

import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.normalUser.UserFuncMenu;

public class ShoppingHistoryController implements Controllers {
    boolean userChanged;
    User user;
    int state = 0;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {

    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Processes getProcess() {
        // TODO:


        return new UserFuncMenu();
    }
}
