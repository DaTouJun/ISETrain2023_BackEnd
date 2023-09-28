package pojo;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Storable {
    private int ID;
    private String name;    // Not null
    private int level = 3;
    private String email;   // Not null
    private String phoneNumber;
    private String passwordSHA1 = "";
    private double totalConsumptions = 0;
    private int failedTried;
    private Date registeredTime;

    private String getLevelString() {
        return switch (level) {
            case 0 -> "管理员";
            case 1 -> "金牌用户";
            case 2 -> "银牌用户";
            case 3 -> "铜牌用户";
            case 4 -> "游客";
            default -> "未知用户等级";
        };
    }

    public String toAdminString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "用户编号" + ID + "  用户名 : " + name + "  用户等级 : " + getLevelString() +
                "  邮箱 : " + email + "  电话号码 : " + phoneNumber + "  总消费 : " + totalConsumptions
                + "  注册时间 : " + sdf.format(registeredTime);
    }
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

