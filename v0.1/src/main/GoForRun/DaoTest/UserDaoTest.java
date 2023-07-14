package DaoTest;

import dao.UserDaoImpl;
import org.junit.jupiter.api.Test;

import pojo.User;
import utils.SHA1Generator;

public class UserDaoTest {

    @Test
    public void TestUserDaoUpdateFail(){
        User user = new User();
        user.setID(2);
        user.setEmail("");
        user.setName("NewName");
        user.setLevel(1);
        user.setPasswordSHA1(SHA1Generator.getSHA1("Test"));
        user.setTotalConsumptions(21.1);
        UserDaoImpl userDao =  new UserDaoImpl();
        userDao.updateByID(user);
    }   // Should fail with : In dao.UserDaoImpl the User are not ready

    @Test void TestUserDaoUpdateSuccess(){
        User user = new User();
        user.setID(2);
        user.setEmail("");
        user.setName("NewName");
        user.setLevel(1);
        user.setPasswordSHA1(SHA1Generator.getSHA1("Test"));
        user.setTotalConsumptions(21.1);
        UserDaoImpl userDao =  new UserDaoImpl();
        userDao.updateByID(user);
    }   // Should words


}
