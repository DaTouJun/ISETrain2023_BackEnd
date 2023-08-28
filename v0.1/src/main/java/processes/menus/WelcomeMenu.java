package processes.menus;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.LoginController;
import processes.controller.RegisterController;

public class WelcomeMenu implements Menus {
    @Override
    public void showMenu(User user) {
        String[] menus = new String[3];
        menus[0] = "1.登录";
        menus[1] = "2.注册";
        menus[2] = "3.忘记密码";
        Connections.getInstance().sendData(menus);
    }

    @Override
    public Processes getNextProcess(User user) {
        int nextInstructions = -1;
        try {
//            nextInstructions = Integer.getInteger(Connections.getInstance().getData());
            nextInstructions = Integer.decode(Connections.getInstance().getData());
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (nextInstructions){
            case 1 :
                return new LoginController();
            case 2:
                return new RegisterController();
            case 3:

            default:
                System.out.println("输入错误");
                return null;
        }
    }
}
