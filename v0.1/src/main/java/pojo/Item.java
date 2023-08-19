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
    private Date dataInProduced;
    private String type;
    private double primeCost;
    private double retailPrice;
    private long num;
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