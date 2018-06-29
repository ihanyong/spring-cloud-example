package com.ihanyong.example.service.api.hello.server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by HanYong on 2018/6/29.
 */
@FeignClient("hello-server")
public interface HelloApi {

    @RequestMapping("hello/with/feign")
    String helloWithFeign(@RequestParam("name") String name);

}
