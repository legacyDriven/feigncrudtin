package com.eugeniusz.services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "jdoodleAuthToken", url = "https://api.jdoodle.com/v1/")
public interface JDoodleAuthTokenClient {
    @PostMapping("/auth-token")
    JDoodleAuthTokenResponse getAuthToken(@RequestBody JDoodleAuthTokenRequest request);
}

