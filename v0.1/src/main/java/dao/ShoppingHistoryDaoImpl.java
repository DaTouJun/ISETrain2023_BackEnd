package dao;

import pojo.CartItem;
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

    }

    @Override
    public void deleteByID(int ID) {

    }

    @Override
    public Storable searchByID(int ID) {
        return null;
    }

    @Override
    public Storable searchByName(String name) {
        return null;
    }

    @Override
    public ArrayList<Storable> queryLikeName(String name) {
        return null;
    }

    @Override
    public ArrayList<Storable> queryAll() {
        return null;
    }
}
