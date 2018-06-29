package com.ihanyong.ribbon.consumer;

import com.ihanyong.example.service.api.hello.server.HelloApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by HanYong on 2018/6/28.
 */
@RestController
public class ConsumerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HelloApi helloApiRemote;



    @RequestMapping("consumer/hello")
    public String hello(String name) {
        LOGGER.info("ask hello-server for {}", name);

        String said = restTemplate.getForEntity("http://hello-server/hello?name={name}", String.class, Collections.singletonMap("name", name)).getBody();


        return new StringBuffer("the hello-server says [").append(said).append("] to you.").toString();
    }


    @RequestMapping("consumer/hello/with/feign")
    public String helloWithFeith(String name) {
        LOGGER.info("ask hello-server for {}", name);

        String resp = helloApiRemote.helloWithFeign(name);

        return new StringBuffer("the hello-server says [").append(resp).append("] to you.").toString();
    }
}
