package ui;
import connections.Connections;
import utils.SHA1Generator;

import java.util.Objects;

// FIXME:未完成
public class LoginUI {
    static Connections con = Connections.getInstance();
    static void noteLocked(){
        con.sendData("账户已经锁定了 请联系管理员解锁");
    }

    static void noteEmptyPW(){
        con.sendData("您的账户密码是空的 请重新设置");
    }

    static String getUserName(){
        con.sendData("请输入您的账户名:");
        return con.getData();
    }

    static String getPassword() throws Exception {
        con.sendData("请输入您的密码:");
        String password = con.getData();
        if (Objects.equals(password, "")){
            throw new Exception("EMPTY PASSWORD");
        }
        return SHA1Generator.getSHA1(password);
    }
}
