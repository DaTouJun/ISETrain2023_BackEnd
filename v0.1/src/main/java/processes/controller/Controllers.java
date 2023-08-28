package processes.controller;

import pojo.User;
import processes.Processes;

public interface Controllers extends Processes{
    boolean getUserChanged();
    void startProcess(User user);
    User getUser();
    Processes getProcess();
}

