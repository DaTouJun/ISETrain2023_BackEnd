package DaoTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.CartItem;
import pojo.Storable;

import java.util.ArrayList;
import java.util.Date;

public class CartDaoTest {

    @BeforeAll
    public void TestInsert(){
        CartItem cartItem = new CartItem();
        cartItem.setBelongsUserID(123);
        cartItem.setItemID(2);
        cartItem.setName("TestItem1");
        cartItem.setProducer("Producer1");
        cartItem.setAddDate(new Date());
        cartItem.setPrice(12.1);
        cartItem.setNum(1);
        dao.ShoppingCartDaoImpl.getInstance().insert(cartItem);
    }

    @Test
    public void testQuery(){
        ArrayList<CartItem> list = dao.ShoppingCartDaoImpl.getInstance().queryUserID(123);
        if (list != null){
            for(var val : list)
                System.out.println(val.toString());
        }
    }

    @AfterAll
    public void TestDelete(){
        CartItem item = (CartItem) dao.ShoppingCartDaoImpl.getInstance().searchByName("TestItem1");
        dao.ShoppingCartDaoImpl.getInstance().deleteByID(item.getID());
    }
}
