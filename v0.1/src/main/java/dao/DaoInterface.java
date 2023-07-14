package dao;

import pojo.Storable;

public interface DaoInterface {
    void updateByID(Storable object);
    void create(Storable object);
    Storable searchByID(int ID);
}
