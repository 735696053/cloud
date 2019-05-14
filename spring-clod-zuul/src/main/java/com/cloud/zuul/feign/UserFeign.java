package com.cloud.zuul.feign;

import com.cloud.zuul.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("SPRING-CLOUD-USER")
public interface UserFeign {

    @RequestMapping("/inToken/{token}")
    public User authToken(@PathVariable("token") String token);
}
