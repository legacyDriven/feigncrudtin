package com.eugeniusz.services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jdoodleClient", url = "https://api.jdoodle.com/v1/")
public interface JDoodleClient {

    @PostMapping("/execute")
    JDoodleResponse execute(@RequestBody JDoodleRequest request);
}

