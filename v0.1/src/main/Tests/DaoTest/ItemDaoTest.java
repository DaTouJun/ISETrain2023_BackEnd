package DaoTest;

import dao.UserDaoImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.ItemDaoImpl;
import pojo.Item;

import java.util.Date;

public class ItemDaoTest {
    @BeforeAll
    public static void exampleInserter() {
        System.out.println("添加测试用的物品");
        Item item = new Item();
        item.setName("TestItem1");
        item.setNum(10);
        ItemDaoImpl.getInstance().insert(item);
        item.setName("TestItem2");
        item.setNum(20);
        ItemDaoImpl.getInstance().insert(item);
    }

    @AfterAll
    public static void exampleDeleter() {
        System.out.println("删除测试用的物品");
        ItemDaoImpl itemDao = (ItemDaoImpl) ItemDaoImpl.getInstance();
        itemDao.deleteByID(itemDao.searchByName("TestItem1").getID());
        itemDao.deleteByID(itemDao.searchByName("TestItem2").getID());
    }


    @Test
    public void testItemInsertAndSearchByName() {
        System.out.println("测试插入和通过Name查找物品");
        ItemDaoImpl itemDao = (ItemDaoImpl) ItemDaoImpl.getInstance();
        Item item = new Item();
        item.setName("TestItem3");
        itemDao.insert(item);
        item = (Item) itemDao.searchByName("TestItem3");
        System.out.println(item.toString());
    }

    @Test
    public void testItemInsertAndSearchByID() {
        System.out.println("测试插入和通过ID查找物品");
        ItemDaoImpl itemDao = (ItemDaoImpl) ItemDaoImpl.getInstance();
        Item item = new Item();
        item.setName("TestItem4");
        itemDao.insert(item);
        item = (Item) itemDao.searchByName("TestItem4");
        item = (Item) itemDao.searchByID(item.getID());
        System.out.println(item.toString());
    }

    @Test
    public void testItemDeleteByName() {
        ItemDaoImpl itemDao = (ItemDaoImpl) ItemDaoImpl.getInstance();
        itemDao.deleteByID(itemDao.searchByName("TestItem1").getID());
    }

    @Test
    public void testUpdate() {
        Item item = new Item();
        item.setID(2);
        item.setName("绿豆");
        item.setNum(13);
        item.setDataInProduced(new Date());

        ItemDaoImpl itemDao = (ItemDaoImpl) ItemDaoImpl.getInstance();
        itemDao.updateByID(item);
    }
}
