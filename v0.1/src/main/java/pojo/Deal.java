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

