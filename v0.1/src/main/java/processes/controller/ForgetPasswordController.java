package processes.controller;

import connections.Connections;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import utils.PasswordValidation;
import utils.SHA1Generator;

// FIXME:未完成
public class ForgetPasswordController implements Controllers {
    boolean userChanged;
    int flag = -1;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        Connections con = Connections.getInstance();
        con.sendData("开始重置密码");
        UserDaoImpl ud = (UserDaoImpl) UserDaoImpl.getInstance();
        con.sendData("请输入密码");
        String newPW = con.getData();
        boolean pwOk = PasswordValidation.validation(newPW);
        if (pwOk) {
            System.out.println("密码可用 成功修改密码");
            user.setPasswordSHA1(SHA1Generator.getSHA1(newPW));
            ud.updateByID(user);
            flag = 1;
        } else {
            System.out.println("您输入的密码格式错误");
            flag = 2;
        }
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
