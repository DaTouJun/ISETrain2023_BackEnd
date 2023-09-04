package dao;

import pojo.Storable;
import pojo.User;
import utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UserDaoImpl implements DaoInterface {
    private static UserDaoImpl instance;

    public static UserDaoImpl getInstance() {
        if (instance == null)
            instance = new UserDaoImpl();
        return instance;
    }

    private UserDaoImpl(){
    }

    @Override
    public void updateByID(Storable object) {
        User user = (User) object;
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE UserList SET Name=? ," +
                "Level=?, Email=?, PhoneNumber=?, " +
                "TotalConsumptions=?, Password=?, " +
                "FailedTried=? WHERE ID=?";
        if (user.getName() == null || user.getEmail() == null
                || user.getName().isEmpty() || user.getEmail().isEmpty()) {
            System.err.println("In " + getClass().getName() +
                    " the updated User are not ready\n" + user);
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
            ps.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }


    // 唯一性检验由control设置
    @Override
    public void insert(Storable object) {
        User user = (User) object;
        Connection con = DBUtils.getConnection();
        String sql = "INSERT INTO UserList (Name, Level, Email, PhoneNumber, RegisteredTime, " +
                "TotalConsumptions, Password, FailedTried)\n" +
                "VALUES (?, ?, ?, ?, DEFAULT, DEFAULT, ?, DEFAULT);";
        if (user.getID() != 0) {
            System.err.println("In " + getClass().getName() + " the User ID should be null\n");
            return;
        }
        if (user.getName() == null || user.getEmail() == null
                || user.getName().isEmpty() || user.getEmail().isEmpty()) {
            System.err.println("In " + getClass().getName() +
                    " the insert User are not ready\n" + user);
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setInt(2, user.getLevel());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getPasswordSHA1());
            ps.executeUpdate();
            ps.close();

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByID(int ID) {
        String sql = "DELETE FROM UserList WHERE ID = ?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private User singleCopy(ResultSet rs) throws SQLException {
        User user = new User();
        user.setID(rs.getInt("ID"));
        user.setName(rs.getString("Name"));
        user.setLevel(rs.getInt("Level"));
        user.setEmail(rs.getString("Email"));
        user.setPhoneNumber(rs.getString("phoneNumber"));
        user.setRegisteredTime(rs.getTimestamp("registeredTime"));
        user.setFailedTried(rs.getInt("FailedTried"));
        user.setPasswordSHA1(rs.getString("Password"));
        return user;
    }

    private Storable copySingleUser(ResultSet rs) throws SQLException {
        return singleCopy(rs);
    }

    private ArrayList<Storable> copyUserList(ResultSet rs) throws SQLException {
        ArrayList<Storable> users = new ArrayList<>();
        Storable user;
        while (rs.next()) {
            user = singleCopy(rs);
            users.add(user);
        }
        if (users.isEmpty())
            return null;
        return users;
    }

    @Override
    public Storable searchByID(int ID) {
        String sql = "Select * from UserList Where ID=?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return copySingleUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Storable searchByName(String name) {
        String sql = "Select * from UserList Where name=?";
        if (name == null) {
            System.err.println(getClass().getName() + "Name = null !!");
        }
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return null;
            return copySingleUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<Storable> queryAll() {
        String sql = "Select * from UserList";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ResultSet rs = ps.executeQuery();
            all = copyUserList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Storable> queryLikeName(String name) {
        String sql = "Select * from UserList Where name Like ?";
        name = '%' + name + "%";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            all = copyUserList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Storable searchByEmail(String email) {
        String sql = "Select * from UserList Where Email=?";
        if (email == null) {
            System.err.println(getClass().getName() + "Email = null !!");
        }
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return null;
            return copySingleUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
