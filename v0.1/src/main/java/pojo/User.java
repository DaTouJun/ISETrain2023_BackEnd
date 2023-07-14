package pojo;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Storable {
    private int ID;
    private String name;    // not null
    private int level = 3;
    private String email;   // Not null
    private String phoneNumber;
    private String passwordSHA1 = "";
    private double totalConsumptions = 0;
    private int failedTried;
    private Date registeredTime;
}

/* DLL of userTable
create table ShopBackend.UserList
(
    ID                int auto_increment comment '用户的ID'
        primary key,
    Name              varchar(255)                        not null comment '用户的姓名',
    Level             int                                 null comment '用户的等级 为0则为管理员',
    Email             varchar(255)                        not null,
    PhoneNumber       varchar(20)                         null,
    RegisteredTime    timestamp default CURRENT_TIMESTAMP not null,
    TotalConsumptions decimal   default 0                 null,
    Password          varchar(41)                         null,
    FailedTried       int       default 0                 null comment '记录存储登录失败的次数'
);
 */