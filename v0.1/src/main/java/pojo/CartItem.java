package pojo;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem implements Storable {
    private int ID;
    private int belongsUserID;
    private int itemID;
    private String name;
    private String producer;
    private Date addDate;
    private double price;
    private int num;


    public CartItem(Item item, User user) {
        belongsUserID = user.getID();
        name = item.getName();
        producer = item.getProducer();
        itemID = item.getID();
        addDate = new java.util.Date();
        price = item.getRetailPrice();
        System.out.println(price);
        // wait for item
    }

    public String showToCustomer() {
        return "名称: " + name + "  ID:" + itemID + "  单价:" + price + "  数量: " + num;
    }
}

/*
    添加物品ID 属于的用户 物品名称
*/