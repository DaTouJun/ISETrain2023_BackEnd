package processes;

import pojo.User;
import processes.controller.Controllers;
import processes.menus.Menus;
import processes.menus.WelcomeMenu;


public class ProcessInstance {
    Processes currentProcess;
    Processes nextProcess;
    User user;

    Menus menus;

    public ProcessInstance(Processes pro) {
        currentProcess = pro;
        user = new User();
        user.setLevel(4);
        user.setName("游客");
        while (currentProcess != null) {
            // 如果是菜单 则显示菜单并进一步判权
            if (currentProcess instanceof Menus) { // 如果是菜单，则进行显示功能和功能表的判别
                menus = (Menus) currentProcess;
                menus.showMenu(user);
                // 用来获取下一个内容 可能是子菜单也可能是进入功能控制器
                nextProcess = menus.getNextProcess(user);
            } else {
                // 如果是控制器，说明进入了功能则进行功能的执行
                Controllers controllers = (Controllers) currentProcess;
                controllers.startProcess(user);
                if (controllers.getUserChanged()) {
                    user = controllers.getUser();
                    nextProcess = controllers.getProcess();
                }
            }
            if (nextProcess != null) {
                currentProcess = nextProcess;
            } else {
                currentProcess = new WelcomeMenu();
            }
        }
    }
}
