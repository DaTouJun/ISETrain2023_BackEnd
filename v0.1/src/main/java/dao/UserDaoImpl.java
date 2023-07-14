package dao;

import pojo.Storable;
import pojo.User;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDaoImpl implements DaoInterface {


    @Override
    public void updateByID(Storable object) {
        User user = (User) object;
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE UserList SET Name=? " +
                "Level=? Email=? PhoneNumber=? " +
                "TotalConsumption=? Password=? " +
                "FailedTried=? Where id=?";
        boolean userReady = user.check();
        if (!userReady) {
            System.err.println("In " + getClass().getName() + " the User are not ready\n" + user);
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setInt(2, user.getLevel());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setDouble(5, user.getTotalConsumptions());
            ps.setString(6, user.getPasswordSHA1());
            ps.setInt(7, user.getFailedTried());

            ps.setInt(8, user.getID());

            ps.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    // FIXME: 未完成
    @Override
    public void create(Storable object) {
        User user = (User) object;
    }


    // FIXME: 未完成
    @Override
    public Storable searchByID(int ID) {
        return null;
    }
}
