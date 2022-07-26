package com.neo.promote.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.promote.annotation.NotControllerResponseAdvice;
import com.neo.promote.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.neo.promote"})
public class ControllerUniteResponse implements ResponseBodyAdvice<Object> {
    /*
     * @RestControllerAdvice(basePackages = {"xxx"}) 自动扫描了所有指定包下的 controller，在 Response 时进行统一处理。
     * 重写 supports() 方法，当返回类型是 Result 时，就不需要再封装，当不为 Result 时才进行调用 beforeBodyWrite 方法，跟过滤器的效果是一样的。
     * 最后重写封装方法 beforeBodyWrite，注意除了 String 的返回值有点特殊，无法直接封装成 json，需要进行特殊处理，其他的直接 new Result(data);
     */

    /**
     * 判断是否要执行beforeBodyWrite方法，返回true为执行，false不执行
     *
     * @param methodParameter
     * @param aClass
     * @return true|false
     */
    @Override
    public boolean supports(MethodParameter methodParameter, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        // response是Result类型，或者注释了NotControllerResponseAdvice都不进行包装
        return !(methodParameter.getParameterType().isAssignableFrom(Result.class) || methodParameter.hasMethodAnnotation(NotControllerResponseAdvice.class));
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, @NotNull MediaType mediaType, @NotNull Class<? extends HttpMessageConverter<?>> aClass, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if (returnType.getGenericParameterType().equals(String.class)) {
            // String类型不能直接包装
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在 Result 里后转换为 json串进行返回
                return objectMapper.writeValueAsString(Result.success(data));
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("统一包装响应失败");
            }
        } else if (data instanceof Result) {
            // 判断响应的Content-Type为JSON格式的body
            // 如果响应返回的对象为统一响应体，则直接返回body
            log.info("result...");
            return data;
        }

        // 非JSON格式body直接返回即可,否则直接包装成Result返回
        return Result.success(data);
    }
}
