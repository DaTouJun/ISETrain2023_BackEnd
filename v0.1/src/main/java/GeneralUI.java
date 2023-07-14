import controller.Controllers;
import menus.LoginMenu;
import menus.Menus;

import java.util.Scanner;

public class GeneralUI {
    public void start(){
        Menus menus1 = new LoginMenu();
        Scanner scanner = new Scanner(System.in);
        while (menus1 != null){
            String[] menus = menus1.getMenus();
            for (var menu : menus){
                System.out.println(menu);
            }
            int mode = scanner.nextInt();
            Controllers controller = menus1.getController(mode);
            try {
                menus1 = controller.getNextMenu();
            } catch (NullPointerException e){
                System.out.println("输入功能编号错误，请重新输入");
            }
            // FIXME: 考虑如何退出到登录界面的问题
        }
    }
}
