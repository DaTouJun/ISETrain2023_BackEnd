package processes.controller.administrator.manageusers;

import pojo.User;
import processes.Processes;
import processes.controller.Controllers;

public class DeleteUserController implements Controllers {
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
