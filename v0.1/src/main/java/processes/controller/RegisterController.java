package processes.controller;

import connections.Connections;
import dao.UserDaoImpl;
import pojo.User;
import processes.Processes;
import processes.menus.WelcomeMenu;
import utils.EmailSender;
import utils.PasswordValidation;
import utils.SHA1Generator;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterController implements Controllers {
    User currentUser;
    boolean userChanged;
    int state = 0;
    int flag = 1;

    @Override
    public boolean getUserChanged() {
        return userChanged;
    }

    @Override
    public void startProcess(User u) {
        UserDaoImpl ud = UserDaoImpl.getInstance();
        currentUser = new User();       // 注册新用户
        userChanged = true;
        System.out.println("请输入您要注册的用户名 用户名长度要大于5个字符");
        String username = Connections.getInstance().getData();
        if (username.length() < 5) {
            System.out.println("您的用户名长度小于5个字符 请重新注册");
            state = 1;
        }
        if (ud.searchByName(username) != null) {
            System.out.println("您的用户名已经有人使用了");
        }
        currentUser.setName(username);
        Connections con = Connections.getInstance();
        while (flag == 1) {
            con.sendData("请输入密码");
            System.out.println("密码要求大于5个字符 大小写字母 标点和数字的组合");
            String newPW = con.getData();
            boolean pwOk = PasswordValidation.validation(newPW);
            if (pwOk) {
                System.out.println("密码可用 请再次输入");
                String newPW2 = Connections.getInstance().getData();
                if (newPW.equals(newPW2)) {
                    currentUser.setPasswordSHA1(SHA1Generator.getSHA1(newPW));
                    flag = 2;   // 完成输入密码
                } else {
                    Connections.getInstance().sendData("两次密码输入不一致 输入失败");
                    flag = 1;
                }
            } else {
                System.out.println("您输入的密码格式错误");
                System.out.println("请重新输入密码");
                state = 1;
            }
        }

        flag = 1;
        while (flag == 1) {
            con.sendData("邮箱");
            String email = con.getData();
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
            Matcher matcher = pattern.matcher(email);
            if (matcher.find() && email.equals(matcher.group())) {
                currentUser.setEmail(email);
                flag = 2;
            } else {
                System.out.println("您输入的不是一个有效的邮箱地址");
                flag = 1;
            }
        }

        {   // 测试邮箱是否为本人邮箱
            int times = 0;
            while (true) {
                times++;
                String testCode = SHA1Generator.getSHA1(String.valueOf(new Date().getTime())).substring(0, 6);
                EmailSender.send(testCode, currentUser.getEmail());
                Connections.getInstance().sendData("已经发送了验证码，请输入");

                String inCode = Connections.getInstance().getData();
                if (inCode.equals(testCode)) {
                    con.sendData("验证码正确 成功验证邮箱");
                    break;
                } else {
                    con.sendData("验证码错误");
                    con.sendData("还有" + (3 - times) + " 次机会");
                }
                if (times == 3) {
                    System.out.println("您输入的验证码错误次数过多，将退出注册流程");
                    state = 0;
                    return;
                }
            }
        }

        con.sendData("是否要添加电话号码？ 添加请输入y");
        String pr = con.getData();
        if (pr.equals("y")) {
            flag = 1;
            while (flag == 1) {
                con.sendData("请输入电话号码");
                String phoneNumber = con.getData();
                if (phoneNumber.length() != 11) {
                    con.sendData("电话号码不是11位");
                    flag = 1;
                    continue;
                }
                currentUser.setPhoneNumber(phoneNumber);
                flag = 2;
            }
        }

        currentUser.setFailedTried(0);
        currentUser.setLevel(3);
        currentUser.setRegisteredTime(new Date());
        ud.insert(currentUser);
        state = 0;
        System.out.println("注册完成 快去登陆吧");
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public Processes getProcess() {
        if (state == 0) {
            return new WelcomeMenu();
        } else if (state == 1) {
            return new RegisterController();
        }
        return new WelcomeMenu();
    }
}
