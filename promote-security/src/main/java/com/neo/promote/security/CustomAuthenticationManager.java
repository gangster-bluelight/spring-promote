package com.neo.promote.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
public class CustomAuthenticationManager  implements AuthenticationManager {
    private final UserDetailsService userDetailService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(UserDetailsService userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails ud = userDetailService.loadUserByUsername(authentication.getName());
        // assert ud instanceof Account;
        if (passwordEncoder.matches((String) authentication.getCredentials(), ud.getPassword())) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(ud.getUsername(), ud.getPassword());
            auth.setDetails(ud);
            return auth;
        }
        return null;
    }
}
