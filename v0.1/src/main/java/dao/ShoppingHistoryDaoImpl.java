package dao;

import pojo.Storable;
import pojo.Deal;

import utils.DBUtils;
import utils.DateUtilToSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingHistoryDaoImpl implements DaoInterface {
    // TODO:
    private ShoppingHistoryDaoImpl() {
    }

    private ShoppingHistoryDaoImpl instance;

    public ShoppingHistoryDaoImpl getInstance() {
        if (instance != null)
            instance = new ShoppingHistoryDaoImpl();
        return instance;
    }

    @Override
    public void updateByID(Storable object) {
        Deal deal = (Deal) object;
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE DealList SET ItemID=? ," +
                "ItemName=?, UserID=?, " +
                "Num=?, Price=?, " +
                "addDate=? , DealFingerPrint = ? WHERE ID=?";
        if (deal.getItemName() == null || deal.getNum() == 0 ||
                deal.getItemName().isEmpty()) {
            System.err.println("the updated cartItem is not ready");
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, deal.getItemID());
            ps.setString(2, deal.getItemName());
            ps.setInt(3, deal.getUserID());
            ps.setInt(4, deal.getNum());
            ps.setDouble(5, deal.getPrice());
            ps.setDate(6, DateUtilToSql.conv(deal.getAddDate()));
            ps.setString(7, deal.getDealFingerPrint());

            ps.execute();
            ps.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Storable object) {
        Deal deal = (Deal) object;
        Connection con = DBUtils.getConnection();
        String sql = "INSERT INTO ShopBackend.DealList ( ItemID, " +
                "ItemName, UserID, Num, Price, addDate, DealFingerPrint)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        if (deal.getID() != 0) {
            System.err.println("In " + getClass().getName() + " the User ID should be null\n");
            return;
        }
        if (deal.getNum() == 0) {
            System.err.println("Insert deal record error" + deal.toString());
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, deal.getItemID());
            ps.setString(2, deal.getItemName());
            ps.setInt(3, deal.getUserID());
            ps.setInt(4, deal.getNum());
            ps.setDouble(5, deal.getPrice());
            ps.setDate(6, DateUtilToSql.conv(deal.getAddDate()));
            ps.setString(7, deal.getDealFingerPrint());

            ps.executeUpdate();
            ps.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteByID(int ID) {
        String sql = "DELETE FROM DealList WHERE ID = ?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Deal singleCopy(ResultSet rs) throws SQLException {
        Deal deal = new Deal();
        deal.setID(rs.getInt("ID"));
        deal.setItemID(rs.getInt("ItemID"));
        deal.setItemName(rs.getString("ItemName"));
        deal.setUserID(rs.getInt("UserID"));
        deal.setNum(rs.getInt("Num"));
        deal.setPrice(rs.getDouble("Price"));
        deal.setAddDate(DateUtilToSql.reconv(rs.getDate("addDate")));
        deal.setDealFingerPrint(rs.getString("DealFingerPrint"));
        return deal;
    }

    private Storable copySingleDeal(ResultSet rs) throws SQLException {
        return singleCopy(rs);
    }


    private ArrayList<Storable> copyDealList(ResultSet rs) throws SQLException {
        ArrayList<Storable> deals = new ArrayList<>();
        Storable cartItem;
        while (rs.next()) {
            cartItem = singleCopy(rs);
            deals.add(cartItem);
        }
        if (deals.isEmpty()) {
            return null;
        }
        return deals;
    }

    @Override
    public Storable searchByID(int ID) {
        String sql = "Select * from DealList Where ID=?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return copySingleDeal(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Storable searchByName(String name) {
        String sql = "Select * from DealList Where ItemName=?";
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
            return copySingleDeal(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Storable> queryLikeName(String name) {
        String sql = "Select * from DealList Where DealList.ItemName Like ?";
        name = '%' + name + "%";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            all = copyDealList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Storable> queryUserID(int UserID) {
        String sql = "Select * from DealList Where DealList.UserID = ?";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, UserID);

            ResultSet rs = ps.executeQuery();
            all = copyDealList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Storable> queryAll() {
        String sql = "Select * from CartList";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ResultSet rs = ps.executeQuery();
            all = copyDealList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
