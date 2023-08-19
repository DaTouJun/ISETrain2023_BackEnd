package processes.menus;

import pojo.User;
import processes.Processes;

public interface Menus extends Processes {
    void showMenu(User user);
    Processes getNextProcess(User user);
}
