package dao;

import pojo.Storable;
import pojo.CartItem;

import utils.DBUtils;
import utils.DateUtilToSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingCartDaoImpl implements DaoInterface {
    private static ShoppingCartDaoImpl instance;

    public static ShoppingCartDaoImpl getInstance() {
        if (instance == null)
            instance = new ShoppingCartDaoImpl();
        return instance;
    }

    private ShoppingCartDaoImpl() {
    }

    @Override
    public void updateByID(Storable object) {
        CartItem cartItem = (CartItem) object;
        Connection con = DBUtils.getConnection();
        String sql = "UPDATE CartList SET BelongUserID=? ," +
                "ItemID=?, Name=?, Producer=?, " +
                "addDate=?, Price=?, " +
                "Num=? WHERE ID=?";
        if (cartItem.getName() == null || cartItem.getName().isEmpty()) {
            System.err.println("the updated cartItem is not ready");
            return;
        }

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartItem.getBelongsUserID());
            ps.setInt(2, cartItem.getItemID());
            ps.setString(3, cartItem.getName());
            ps.setString(4, cartItem.getProducer());
            ps.setDate(5, DateUtilToSql.conv(cartItem.getAddDate()));
            ps.setDouble(6, cartItem.getPrice());
            ps.setInt(7, cartItem.getNum());

            ps.execute();
            ps.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Storable object) {
        CartItem cartItem = (CartItem) object;
        Connection con = DBUtils.getConnection();
        String sql = "INSERT INTO ShopBackend.CartList (BelongUserID, ItemID, " +
                "Name, Producer, addDate, Price, Num)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        if (cartItem.getID() != 0) {
            System.err.println("In " + getClass().getName() + " the User ID should be null\n");
            return;
        }
        if (cartItem.getName() == null || cartItem.getName().isEmpty()) {
            System.err.println("In " + getClass().getName() +
                    " the insert User are not ready\n" + cartItem);
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cartItem.getBelongsUserID());
            ps.setInt(2, cartItem.getItemID());
            ps.setString(3, cartItem.getName());
            ps.setString(4, cartItem.getProducer());
            ps.setDate(5, DateUtilToSql.conv(cartItem.getAddDate()));
            ps.setDouble(6, cartItem.getPrice());
            ps.setInt(7, cartItem.getNum());

            ps.executeUpdate();
            ps.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByID(int ID) {
        String sql = "DELETE FROM CartList WHERE ID = ?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private CartItem singleCopy(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setID(rs.getInt("ID"));
        cartItem.setBelongsUserID(rs.getInt("BelongUserID"));
        cartItem.setItemID(rs.getInt("ItemID"));
        cartItem.setName(rs.getString("Name"));
        cartItem.setProducer(rs.getString("Producer"));
        cartItem.setAddDate(DateUtilToSql.reconv(rs.getDate("addDate")));
        cartItem.setNum(rs.getInt("Num"));
        return cartItem;
    }

    private Storable copySingleCartItem(ResultSet rs)throws SQLException{
        return singleCopy(rs);
    }

    private ArrayList<Storable> copyCartItemList(ResultSet rs) throws SQLException {
        ArrayList<Storable> carts = new ArrayList<>();
        Storable cartItem;
        while (rs.next()) {
            cartItem = singleCopy(rs);
            carts.add(cartItem);
        }
        if (carts.isEmpty()) {
            return null;
        }
        return carts;
    }

    private ArrayList<CartItem> copyCartItemList_(ResultSet rs) throws SQLException {
        ArrayList<CartItem> carts = new ArrayList<>();
        CartItem cartItem;
        while (rs.next()) {
            cartItem = singleCopy(rs);
            carts.add(cartItem);
        }
        if (carts.isEmpty()) {
            return null;
        }
        return carts;
    }

    @Override
    public Storable searchByID(int ID) {
        String sql = "Select * from CartList Where ID=?";
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return copySingleCartItem(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Storable searchByName(String name) {
        String sql = "Select * from CartList Where name=?";
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
            return copySingleCartItem(rs);
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
            all = copyCartItemList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Storable> queryLikeName(String name) {
        String sql = "Select * from CartList Where CartList.Name Like ?";
        name = '%' + name + "%";
        ArrayList<Storable> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            all = copyCartItemList(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CartItem> queryUserID(int UserID) {
        String sql = "Select * from CartList Where CartList.BelongUserID = ?";
        ArrayList<CartItem> all;
        try {
            PreparedStatement ps = DBUtils.getConnection().prepareStatement(sql);
            ps.closeOnCompletion();
            ps.setInt(1, UserID);

            ResultSet rs = ps.executeQuery();
            all = copyCartItemList_(rs);
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
