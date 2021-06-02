package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.sql.Date;

@Setter
@Getter
@ToString
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private Date createtime;
    private Date updatetime;
    private int state;
}
