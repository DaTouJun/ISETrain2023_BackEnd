package dao;

import pojo.Item;
import pojo.Storable;
import utils.DBUtils;
import utils.DateUtilToSql;

import java.sql.*;
import java.util.ArrayList;

public class ItemDaoImpl implements DaoInterface {
    private static ItemDaoImpl instance;

    public static DaoInterface getInstance() {
        if (instance == null)
            instance = new ItemDaoImpl();
        return instance;
    }

    private ItemDaoImpl() {
    }

    private Item singleCopy(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setID(rs.getInt("ID"));
        item.setName(rs.getString("Name"));
        item.setProducer(rs.getString("Producer"));
        item.setDateInProduced(DateUtilToSql.reconv(rs.getDate("dataInProduced")));
        item.setPrimeCost(rs.getDouble("PrimeCost"));
        item.setRetailPrice(rs.getDouble("RetailPrice"));
        item.setNum(rs.getLong("Num"));
        item.setType(rs.getString("Type"));
        return item;
    }

    private Storable copySingleItem(ResultSet rs) throws SQLException {
        return singleCopy(rs);
    }

    private ArrayList<Storable> copyItemList(ResultSet rs) throws SQLException {
        ArrayList<Storable> items = new ArrayList<>();
        Storable item;
        while (rs.next()) {
            item = singleCopy(rs);
            items.add(item);
        }
        if (items.isEmpty())
            return null;
        return items;
    }

    @Override
    public void updateByID(Storable object) {
        Item item = (Item) object;
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE ItemList SET Name=? ," +
                "Producer=?, dataInProduced=?, " +
                "Type=?, PrimeCost=?, RetailPrice=?, " +
                "Num=? WHERE ID=?";
        if (item.getName() == null || item.getName().isEmpty()) {
            System.err.println("In " + getClass().getName() +
                    " the updated Item are not ready\n" + item);
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, item.getName());
            ps.setString(2, item.getProducer());
            ps.setDate(3, utils.DateUtilToSql.conv(item.getDateInProduced()));
            ps.setString(4, item.getType());
            ps.setDouble(5, item.getPrimeCost());
            ps.setDouble(6, item.getRetailPrice());
            ps.setLong(7, item.getNum());

            ps.setInt(8, item.getID());
            ps.executeUpdate();
            ps.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Storable object) {
        Item item = (Item) object;
        Connection con = DBUtils.getConnection();
        String sql = "INSERT INTO ItemList (Name, Producer, dataInProduced, Type, PrimeCost, RetailPrice, Num) " +
                "VALUES (?, ? ,? ,? ,? ,? ,?)";
        if (item.getID() != 0) {
            System.err.println("In " + getClass().getName() + " the Item ID should be null\n");
            return;
        }
        if (item.getName() == null || item.getName().isEmpty()) {
            System.err.println("In " + getClass().getName() +
                    " the insert Item are not ready\n" + item);
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, item.getName());
            ps.setString(2, item.getProducer());
            ps.setDate(3, DateUtilToSql.conv(item.getDateInProduced()));
            ps.setString(4, item.getType());
            ps.setDouble(5, item.getPrimeCost());
            ps.setDouble(6, item.getRetailPrice());
            ps.setLong(7, item.getNum());
            ps.executeUpdate();
            ps.close();

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByID(int ID) {
        String sql = "DELETE FROM ItemList WHERE ID = ?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Storable searchByID(int ID) {
        String sql = "Select * from ItemList Where ID=?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return copySingleItem(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Storable searchByName(String name) {
        String sql = "Select * from ItemList Where Name=?";
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
            return copySingleItem(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Storable> queryLikeName(String name) {
        String sql = "Select * from ItemList Where Name Like ?";
        name = '%' + name + "%";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            all = copyItemList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Storable> queryAll() {
        String sql = "Select * from ItemList";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ResultSet rs = ps.executeQuery();
            all = copyItemList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
