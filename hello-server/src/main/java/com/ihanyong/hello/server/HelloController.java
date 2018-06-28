package com.ihanyong.hello.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by HanYong on 2018/6/28.
 */
@RestController
public class HelloController {
    public static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);


    @RequestMapping("hello")
    public String hello(String name) {
        LOGGER.info("say hello to {}", name);
        return new StringBuffer("hello, ").append(name).toString();
    }
}
