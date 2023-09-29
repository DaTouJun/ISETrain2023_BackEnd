package processes.controller;

import connections.Connections;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import processes.menus.administrator.AdminFuncMenu;
import processes.menus.normalUser.UserFuncMenu;
import utils.SHA1Generator;

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
        currentUser = user;
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
        UserDaoImpl ud = UserDaoImpl.getInstance();
        User u = (User) ud.searchByName(tempu.getName());
        if (u == null) {
            con.sendData("找不到对应用户名的用户");
            state = -1;
            return;
        }
        if (u.getFailedTried() >= 5 && u.getLevel() != 0) {
            con.sendData("用户输入密码错误次数过多 用户已经锁定");
            state = -1;
            return;
        }
        if (u.getPasswordSHA1().isEmpty()) {
            state = 3;
            con.sendData("用户密码为空");
            currentUser = u;
            userChanged = true;
            return;
        }
        if (u.getPasswordSHA1().equals(userPWHash)) {
            if (u.getLevel() >= 1 && u.getLevel() <= 3) {
                state = 1;      // 普通用户登录
            } else if (u.getLevel() == 0) {
                state = 0;  // 管理员 登录
            }
            userChanged = true;
            con.sendData("登录成功");
            u.setFailedTried(0);        // 登录成功删除数量限制
            ud.updateByID(u);
            currentUser = u;
        } else {
            // 处理密码错误的情况
            u.setFailedTried(u.getFailedTried() + 1);
            ud.updateByID(u);
            System.out.println("密码输入错误，当前输入错误次数为" + u.getFailedTried());
            System.out.println("输入错误次数大于5次将禁止登录");
            System.out.println("*****返回主菜单*****");
            state = -1;
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        return switch (state) {
            case 0 -> new AdminFuncMenu();
            case 1 -> new UserFuncMenu();
            case 3 -> new NullPasswordController();
            case -1 -> null;    // 返回主菜单
            default -> {
                yield null;    // 同样返回主菜单
            }
        };
    }
}
