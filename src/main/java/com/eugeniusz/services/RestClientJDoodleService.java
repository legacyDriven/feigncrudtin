package com.eugeniusz.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RestClientJDoodleService {

    RestClient tokenClient;

    RestClient executeClient;

    public RestClientJDoodleService(){
        tokenClient = RestClient.builder()
                .baseUrl("https://api.jdoodle.com/v1/auth-token")
                .build();

        executeClient = RestClient.builder()
                .baseUrl("https://api.jdoodle.com/v1/execute")
                .build();

    }

}
