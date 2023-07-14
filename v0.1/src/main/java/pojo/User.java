package pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Storable {
    private int ID;
    private String name;
    private int level;
    private String email;
    private String phoneNumber;
    private String passwordSHA1;
    private double totalConsumptions = 0;
    private int FailedTried;

    public boolean check() {
        boolean OK = name != null && !name.equals("");
        if (email == null || email.equals(""))
            OK = false;
        return OK;
    }
}
