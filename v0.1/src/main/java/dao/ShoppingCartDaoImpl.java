package dao;

import pojo.Storable;

import java.util.ArrayList;

public class ShoppingCartDaoImpl implements DaoInterface {
    private static DaoInterface instance;

    public static DaoInterface getInstance(){
        if (instance == null)
            instance = new ShopingHistoryDaoImpl();
        return instance;
    }

    private ShoppingCartDaoImpl() {
    }

    @Override
    public void updateByID(Storable object) {

    }

    @Override
    public void insert(Storable object) {

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
