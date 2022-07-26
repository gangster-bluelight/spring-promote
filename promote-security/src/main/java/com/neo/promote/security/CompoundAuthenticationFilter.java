package com.neo.promote.security;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import com.neo.promote.lang.Constant;
import com.neo.promote.utils.TokenProviderUtil;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Slf4j
@Order(0)
@Component
public class CompoundAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 将在白名单中的请求直接放行
        if (Constant.WHITE_LIST
                .stream()
                .map(p -> new AntPathRequestMatcher(p.getValue(), p.getKey().toString()))
                .anyMatch(matcher -> matcher.matches(request))
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // 获取登录用户实体信息
        Optional<UserDetails> maybeUserDetail = compoundAuth(request);
        if (maybeUserDetail.isPresent()) {
            UserDetails userDetail = maybeUserDetail.get();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetail.getUsername(), userDetail.getPassword(), userDetail.getAuthorities()
            );
            auth.setDetails(userDetail);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("user 【{}】 logged into the system at the {}.", userDetail.getUsername(), LocalDateTime.now());
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 授权验证
     * @param request 请求体
     * @return 用户对象
     */
    private Optional<UserDetails> compoundAuth(HttpServletRequest request) {
        Pair<String, String> token = getAuthToken(request);
        // 验证token存放在请求头中的方式
        if (StrUtil.equals(token.getKey(), TokenProviderUtil.JWT_AUTH_PREFIX)) {
            return Optional.of(userDetailsService.loadUserByUsername(TokenProviderUtil.parseTokenToUsername(token.getValue())));
        }
        // 验证采用用户名密码登录的方式
        if (StrUtil.equals(token.getKey(), TokenProviderUtil.BASIC_AUTH_PREFIX)) {
            Pair<String, String> pair = TokenProviderUtil.decodeBasicToken(token.getValue());
            UserDetails user = userDetailsService.loadUserByUsername(pair.getKey());
            if (!passwordEncoder.matches(pair.getValue(), user.getPassword())) {
                throw new IllegalStateException(String.format("用户【%s】密码错误，请重新输入!", pair.getKey()));
            }
            return Optional.of(user);
        }
        throw new IllegalStateException("授权方式异常，请重新授权");
    }

    /**
     * 获取请求头信息，并验证token
     * @param request 请求体
     * @return 请求方式【令牌登录，用户名密码登录】，token
     */
    private static Pair<String, String> getAuthToken(HttpServletRequest request) {
        // 获取请求头信息
        String header = request.getHeader(TokenProviderUtil.TOKEN_HEADER);

        // token验证
        if (StrUtil.startWith(header, TokenProviderUtil.JWT_AUTH_PREFIX)) {
            return Pair.of(TokenProviderUtil.JWT_AUTH_PREFIX, removeStart(header, TokenProviderUtil.JWT_AUTH_PREFIX));
        }
        // 密码验证
        if (StrUtil.startWith(header, TokenProviderUtil.BASIC_AUTH_PREFIX)) {
            return Pair.of(TokenProviderUtil.BASIC_AUTH_PREFIX, removeStart(header, TokenProviderUtil.BASIC_AUTH_PREFIX));
        }
        String[] seg = header.split(" ");
        if (seg.length == 2) {
            return Pair.of(seg[0], seg[1]);
        }
        log.info("授权错误[{}], {} => {} {}，请重新登录!", header, request.getRemoteAddr(), request.getMethod(), request.getRequestURI());
        throw new IllegalStateException("授权错误，请重新登录!");
    }

    /**
     * 当子字符串位于源字符串的开头时才删除它，否则返回源字符串。
     *
     * @param str    源字符串
     * @param remove 子字符串
     * @return 如果找到则删除字符串的子字符串，如果为 null 则为 null 字符串输入
     */
    private static String removeStart(String str, String remove) {
        if (StrUtil.isEmpty(str) || StrUtil.isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)) {
            return str.substring(remove.length());
        }
        return str;
    }
}
