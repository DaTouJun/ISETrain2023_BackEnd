package processes.controller;

import connections.Connections;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import processes.menus.Menus;

// FIXME:未完成
public class ResetPasswordController implements Controllers{

    @Override
    public boolean getUserChanged() {
        return true;
    }

    @Override
    public void startProcess(User user) {
        Connections con  =Connections.getInstance();
        con.sendData("开始重置密码");
        UserDaoImpl ud;
        con.sendData("请输入密码");
        String newPW = con.getData();

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
