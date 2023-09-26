package processes.controller.administrator.manageitems;

import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.menus.administrator.ManageItemsMenu;
// TODO
public class AddItemController implements Controllers {
    boolean userChanged;
    User currentUser;
    int state = 0;  // Stay here

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        currentUser = user;

    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0){
            return new ManageItemsMenu();
        }


        return new ManageItemsMenu();
    }
}
