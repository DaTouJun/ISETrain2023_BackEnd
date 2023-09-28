package processes.controller.administrator.manageusers;

import connections.Connections;
import pojo.User;
import processes.Processes;
import processes.controller.Controllers;

public class DeleteUserController implements Controllers {
    @Override
    public boolean getUserChanged() {
        return false;
    }

    @Override
    public void startProcess(User user) {
        Connections con = Connections.getInstance();
        con.sendData("请输入要删除的用户名");
        String userName = Connections.getInstance().getData();
        User u = (User) dao.UserDaoImpl.getInstance().searchByName(userName);
        if (u == null) {
            con.sendData("您输入的用户名找不到对应的用户");
        } else {
            con.sendData("您输入的要查找的用户名信息:\n" + u.toAdminString());
            con.sendData("是否要删除 确认请输入yes");
            String getConfirm = con.getData();
            if (getConfirm.equals("yes")) {
                con.sendData("您确认了删除");
                dao.UserDaoImpl.getInstance().deleteByID(u.getID());
                con.sendData("已经删除成功");
            } else {
                con.sendData("您取消了删除");
            }
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
