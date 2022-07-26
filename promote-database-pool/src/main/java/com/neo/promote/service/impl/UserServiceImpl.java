package com.neo.promote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.promote.entity.User;
import com.neo.promote.mapper.UserMapper;
import com.neo.promote.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
