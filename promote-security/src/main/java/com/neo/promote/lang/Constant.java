package com.neo.promote.lang;

import javafx.util.Pair;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
public class Constant {
    /**
     * 白名单
     */
    public static final List<Pair<HttpMethod, String>> WHITE_LIST = new ArrayList<>();
    public static final String[] WHITE_STR = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/**",
            "/favicon.ico",
    };

    static {
        // swagger文档相关路径
        WHITE_LIST.add(new Pair<>(HttpMethod.GET, "/swagger-ui/**"));
        WHITE_LIST.add(new Pair<>(HttpMethod.GET, "/swagger-ui.html"));
        WHITE_LIST.add(new Pair<>(HttpMethod.GET, "/v3/**"));

        // 图标相关路径
        WHITE_LIST.add(new Pair<>(HttpMethod.GET, "/favicon.ico"));
    }
}
