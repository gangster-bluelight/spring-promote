package com.neo.promote.lang;

import lombok.Data;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Data
public class Result {
    private Long code;
    private String message;
    private Object data;

    private Result() {
    }

    private Result(Long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(0L, "success", data);
    }

    public static Result failure(String message) {
        return new Result(0L, message, null);
    }
}
