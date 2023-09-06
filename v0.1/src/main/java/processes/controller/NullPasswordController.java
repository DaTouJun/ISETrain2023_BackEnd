package processes.controller;

import connections.Connections;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import processes.menus.WelcomeMenu;
import utils.PasswordValidation;
import utils.SHA1Generator;

// FIXME:未完成
public class NullPasswordController implements Controllers {
    boolean userChanged;
    User currentUser;
    int flag = -1;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        currentUser = user;
        Connections con = Connections.getInstance();
        con.sendData("开始重置密码");
        UserDaoImpl ud = UserDaoImpl.getInstance();
        con.sendData("请输入密码");
        String newPW = con.getData();
        boolean pwOk = PasswordValidation.validation(newPW);
        if (pwOk) {
            System.out.println("密码可用 请再次输入");
            String newPW2 = Connections.getInstance().getData();
            if (newPW.equals(newPW2)) {
                user.setPasswordSHA1(SHA1Generator.getSHA1(newPW));
                ud.updateByID(user);
                flag = 1;
                Connections.getInstance().sendData("重置完成 请去登录吧");
            } else {
                Connections.getInstance().sendData("两次密码输入不一致 修改不成功");
                flag = 2;
            }
        } else {
            System.out.println("您输入的密码格式错误");
            flag = 2;
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        return new WelcomeMenu();
    }
}
