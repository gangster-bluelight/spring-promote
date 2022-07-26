package com.neo.promote.controller;

import com.neo.promote.entity.User;
import com.neo.promote.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Slf4j
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("")
    public List<User> findAllUsers() {
        return userService.list(null);
    }
}
