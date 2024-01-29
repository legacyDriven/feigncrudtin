package com.eugeniusz.services.feign;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JDoodleConfig {
    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret}")
    private String apiSecret;

}

