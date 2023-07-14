package dao;

import pojo.Storable;

import java.util.ArrayList;

public interface DaoInterface {
    void updateByID(Storable object);
    void insert(Storable object);
    void deleteByID(int ID);
    Storable searchByID(int ID);
    Storable searchByName(String name);
    ArrayList<Storable> queryLikeName(String name);
    ArrayList<Storable> queryAll();
}
