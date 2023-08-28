package processes.controller;

import connections.Connections;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import processes.menus.administrator.AdminFuncMenu;
import processes.menus.normalUser.UserFuncMenu;
import utils.SHA1Generator;

// FIXME:未完成
public class LoginController implements Controllers {
    int state;
    // -1:  登录失败
    // 0：  管理员登录成功
    // 1:  普通用户登录成功
    // 3:   用户密码为空 进入重置流程


    User currentUser;

    boolean userChanged;
    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        userChanged = false;
        Connections con = Connections.getInstance();
        con.sendData("欢迎登录 请输入您的用户名");
        User tempu = new User();
        tempu.setName(con.getData());
        con.sendData("请输入您的密码");
        String password = con.getData();
        if (password.isEmpty()) {
            state = 0;
            con.sendData("输入密码为空");
            return;
        }
        String userPWHash = SHA1Generator.getSHA1(password);


        // 验证
        UserDaoImpl ud = new UserDaoImpl();
        User u = (User) ud.searchByName(tempu.getName());
        if (u == null) {
            con.sendData("找不到对应用户名的用户");
            state = -1;
            return;
        }
        if (u.getFailedTried() >= 3 && u.getLevel() != 0){
            con.sendData("用户输入密码错误次数过多 用户已经锁定");
            state = -1;
            return;
        }
        if (u.getPasswordSHA1().isEmpty()){
            state = 3;
            con.sendData("用户密码为空");
            currentUser = u;
            userChanged = true;
            return;
        }
        if (u.getPasswordSHA1().equals(userPWHash)) {
            if (u.getLevel() == 1) {
                state = 1;      // 普通用户登录
            } else if (u.getLevel() == 0) {
                state = 0;  // 管理员 登录
            }
            userChanged = true;
            con.sendData("登录成功");
            currentUser = u;
            return;
        } else {
            // 处理密码错误的情况
            u.setFailedTried(u.getFailedTried() + 1);
            ud.updateByID(u);
            // TODO: 完成密码输入错误的情况
        }
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public Processes getProcess() {
        return switch (state) {
            case 0 -> new AdminFuncMenu();
            case 1 -> new UserFuncMenu();
            case 3 -> new ForgetPasswordController();
            default -> null;
        };
    }
}
