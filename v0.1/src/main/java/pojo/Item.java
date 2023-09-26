package pojo;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item implements Storable {
    private int ID;
    private String name;    // Not null
    private String producer;
    private Date dateInProduced;
    private String type;
    private double primeCost;
    private double retailPrice;
    private long num;

    public String toUserString() {
        return "物品编号:" + ID + "  物品名称:  " + name + "  制造商: " + producer + " 生产日期: "
                + dateInProduced + " 类型: " + type + " 进价: " + primeCost + " 零售价: " + retailPrice + " 数量: " + num;
    }
}

/*
    商品信息包括 商品编号  商品名称  生产厂家  生产日期  型号  进货价  零售价格  数量
 */

/*
create table ItemList
(
    ID             int          not null
        primary key,
    Name           varchar(256) not null,
    Producer       varchar(256) null,
    dataInProduced datetime     null,
    Type           varchar(256) null,
    PrimeCost      double       null,
    RetailPrice    double       null,
    Num            mediumtext   not null
);
 */