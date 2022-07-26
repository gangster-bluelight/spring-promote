package com.neo.promote.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String remarks;
    private String createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateUser;
}
