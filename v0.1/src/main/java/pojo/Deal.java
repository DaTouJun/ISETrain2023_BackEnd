package pojo;


import lombok.*;

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