package processes.controller;

import pojo.User;
import processes.Processes;
import processes.menus.Menus;

// FIXME:未完成
public class RegisterController implements Controllers{
    User currentUser;
    boolean userChanged;
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
        return null;
    }
}
