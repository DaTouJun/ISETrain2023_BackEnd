package processes.controller.administrator;

import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.AdminFuncMenu;

// TODO
public class ManageUserController implements Controllers {
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
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0){
            return new AdminFuncMenu();
        }

        return new AdminFuncMenu();
    }
}
