package DaoTest;

import dao.UserDaoImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pojo.Storable;
import pojo.User;
import utils.SHA1Generator;
import java.util.ArrayList;

public class UserDaoTest {
    @Test
    public void testUserInsertAndSearchByName() {
        User user = new User();
        user.setName("TestName");
        user.setEmail("Test@test.com");
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.insert(user);
        User user2 = (User) userDao.searchByName("TestName");
        System.out.println(user2);
    }

    @Test
    public void testUserInsertAndSearchByID() {
        UserDaoImpl userDao = new UserDaoImpl();
        User user2 = (User) userDao.searchByID(1);
        System.out.println(user2);
    }


    @Test
    public void testUserDaoUpdateFail() {
        User user = new User();
        user.setEmail("");
        user.setName("NewName");
        user.setLevel(1);
        user.setPasswordSHA1(SHA1Generator.getSHA1("Test"));
        user.setTotalConsumptions(21.1);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.updateByID(user);
    }   // Should fail with : In dao.UserDaoImpl the User are not ready

    @Test
    void testUserDaoUpdateSuccess() {
        User user = new User();
        user.setID(12);
        user.setEmail("e@email.com");
        user.setName("NewName");
        user.setLevel(1);
        user.setPasswordSHA1(SHA1Generator.getSHA1("Test"));
        user.setTotalConsumptions(21.1);
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.updateByID(user);
    }   // Should work

    @Test
    void testGetAllUsers() {
        ArrayList<Storable> users;
        UserDaoImpl userDao = new UserDaoImpl();
        users = userDao.queryAll();
        for (Storable st : users) {
            User user = (User) st;
            System.out.println(user);
        }
    }

    @BeforeAll
    public static void exampleInserter() {
        System.out.println("添加测试用的初始用户");
        User user = new User();
        user.setName("TestName1");
        user.setEmail("Test@test.com");
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.insert(user);
        user.setName("TestName2");
        userDao.insert(user);
    }

    @AfterAll
    public static void exampleDelete(){
        System.out.println("删除添加的用户");
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.deleteByID(userDao.searchByName("TestName").getID());
        userDao.deleteByID(userDao.searchByName("TestName1").getID());
        userDao.deleteByID(userDao.searchByName("TestName2").getID());
    }

    @Test
    void testGetUserNameLike() {
        System.out.println("测试模糊搜索名称");
        ArrayList<Storable> users;
        UserDaoImpl userDao = new UserDaoImpl();
        users = userDao.queryLikeName("Test");
        if (users == null)
            System.err.println("err users is null");
        else {
            for (Storable st : users) {
                User user = (User) st;
                System.out.println(user);
            }
        }
    }

    @Test
    void testDeleteByID(){
        System.out.println("测试通过ID删除");
        UserDaoImpl userDao = new UserDaoImpl();
        User user = new User();
        user.setName("Test1");
        user.setEmail("a@a.com");
        userDao.insert(user);
        user = (User) userDao.searchByName("Test1");
        System.out.println("插入后信息");
        System.out.println(user);
        userDao.deleteByID(user.getID());
        System.out.println("删除后是否变成了null?");
        System.out.println(userDao.searchByID(user.getID()) == null);
    }
}
