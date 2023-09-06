package processes.controller;

import connections.Connections;
import dao.DaoInterface;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import processes.menus.WelcomeMenu;
import utils.EmailSender;
import utils.SHA1Generator;

import java.net.ConnectException;
import java.util.Date;

public class ForgetPasswordController implements Controllers {
    private boolean userChanged;
    private User currentUser;
    private int flag = -1;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User user) {
        currentUser = user;
        Connections.getInstance().sendData("*******进行密码忘记密码流程*******");
        Connections.getInstance().sendData("请输入注册所使用的邮箱");
        String email = Connections.getInstance().getData();
        User getUser = (User) UserDaoImpl.getInstance().searchByEmail(email);
        if (getUser == null) {
            Connections.getInstance().sendData("未找到对应的用户名");
        } else {
            if (getUser.getLevel() == 0) {
                Connections.getInstance().sendData("管理员用户不支持忘记密码");
                flag = 0;
            } else {
                Connections.getInstance().sendData("用户名为:" + getUser.getName() + "\n确认正确输入y否则输入n");
                String e = Connections.getInstance().getData();
                if (e.charAt(0) == 'y') {
                    userChanged = true;
                    String testCode = SHA1Generator.getSHA1(String.valueOf(new Date().getTime())).substring(0, 6);
                    EmailSender.send(testCode, getUser.getEmail());
                    Connections.getInstance().sendData("已经发送了验证码，请输入");
                    boolean end = false;
                    int times = 3;
                    do {
                        String inCode = Connections.getInstance().getData();
                        if (inCode.equals(testCode)) {
                            Connections.getInstance().sendData("验证码正确，进入重置密码流程");
                            getUser.setPasswordSHA1("");
                            currentUser = getUser;
                            userChanged = true;
                            end = true;
                            flag = 2;
                        } else {
                            times--;
                            Connections.getInstance().sendData("验证码输入错误 还有" + times + "次机会");
                            flag = 1;
                        }
                    } while (times > 0 && !end);
                } else {
                    Connections.getInstance().sendData("******退出忘记密码流程******");
                    flag = 1;
                }
            }
        }
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (flag == 2)
            return new NullPasswordController();
        else
            return new WelcomeMenu();
    }
}
