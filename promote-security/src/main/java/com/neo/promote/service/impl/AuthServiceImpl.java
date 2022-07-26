package com.neo.promote.service.impl;

import com.neo.promote.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
