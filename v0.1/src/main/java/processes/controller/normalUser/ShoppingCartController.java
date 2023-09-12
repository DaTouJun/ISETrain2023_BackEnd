package processes.controller.normalUser;

import pojo.User;
import processes.Processes;
import processes.controller.Controllers;

public class ShoppingCartController implements Controllers {
    // TODO:
    @Override
    public boolean getUserChanged() {
        return false;
    }

    @Override
    public void startProcess(User user) {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public Processes getProcess() {
        return null;
    }
}
