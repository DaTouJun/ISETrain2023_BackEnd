package pojo;


import lombok.*;
import utils.SHA1Generator;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Deal implements Storable {
    private int ID;
    private int itemID;
    private String itemName;
    private int userID;
    private int num;
    private double price;
    private Date addDate;
    private String dealFingerPrint;

    public Deal(CartItem cartItem) {
        itemID = cartItem.getItemID();
        itemName = cartItem.getName();
        userID = cartItem.getBelongsUserID();
        num = cartItem.getNum();
        price = cartItem.getPrice();
        addDate = new java.util.Date();
        dealFingerPrint = SHA1Generator.getSHA1(String.valueOf(itemID) + userID + price + addDate);
    }

    public String toUserViewString() {
        return "ID: " + ID + " 物品名:" + itemName + " 数量: " + num + " 单价: " + price + " 交易时间: " + addDate.toString();
    }
}



/*
create table ShopBackend.DealList
(
    ID              int auto_increment
        primary key,
    ItemID          int          null,
    ItemName        varchar(256) not null,
    UserID          varchar(256) null,
    addDate         datetime     null,
    Price           double       null,
    Num             mediumtext   not null,
    DealFingerPrint varchar(256) null
);
 */