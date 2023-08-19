package processes.controller;

import pojo.User;
import processes.Processes;
import processes.menus.Menus;

public interface Controllers extends Processes{
    boolean getUserChanged();
    void startProcess(User user);
    User getUser();
    Processes getProcess();
}

