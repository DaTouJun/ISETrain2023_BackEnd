package processes.controller.normalUser;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;
import processes.controller.ForgetPasswordController;
import processes.controller.NullPasswordController;
import processes.menus.normalUser.UserFuncMenu;
import utils.SHA1Generator;

// TODO
public class AlterPasswordController implements Controllers {
    User currentUser;
    boolean userChanged;

    int state = 0;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        currentUser = user;
        Connections con = Connections.getInstance();
        con.sendData("请输入您当前的密码");
        String password = con.getData();
        if (SHA1Generator.getSHA1(password).equals(user.getPasswordSHA1())) {
            con.sendData("验证成功 现在将清除当前密码并开始重置密码流程");
            state = 1;
        } else {
            con.sendData("验证失败，是否要重新输入? 是请输入y");
            String prompt = con.getData();
            if (prompt.equals("y")) {
                state = 2;
            }
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new UserFuncMenu();
        } else if (state == 1) {
            return new NullPasswordController();
        } else if (state == 2) {
            return new ForgetPasswordController();
        }

        return new UserFuncMenu();
    }
}
