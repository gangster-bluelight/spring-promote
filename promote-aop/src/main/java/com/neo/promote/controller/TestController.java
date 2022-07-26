package com.neo.promote.controller;

import com.neo.promote.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author blue-light
 * Date: 2022-07-25 星期一
 * Description:
 */
@Slf4j
@RestController
public class TestController {
    @OperationLog
    @GetMapping("/test")
    public String test() {
        log.info("test activity...");
        return "success";
    }
}
