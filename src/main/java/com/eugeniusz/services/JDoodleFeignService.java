package com.eugeniusz.services;

import com.eugeniusz.dto.RemoteCodeExecRequest;
import com.eugeniusz.dto.feign.JDoodleAuthTokenRequest;
import com.eugeniusz.dto.feign.JDoodleAuthTokenResponse;
import com.eugeniusz.dto.feign.JDoodleRequest;
import com.eugeniusz.dto.feign.JDoodleResponse;
import com.eugeniusz.httpclients.feign.JDoodleAuthTokenClient;
import com.eugeniusz.httpclients.feign.JDoodleClient;
import com.eugeniusz.httpclients.feign.JDoodleConfig;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class JDoodleFeignService {

    JDoodleClient jdoodleClient;

    JDoodleAuthTokenClient jdoodleAuthTokenClient;

    JDoodleConfig config;

    public String getAuthToken() {
        JDoodleAuthTokenResponse response = jdoodleAuthTokenClient.getAuthToken(JDoodleAuthTokenRequest.of(config));
        return response.token();
    }

    public String executeCode(RemoteCodeExecRequest incomingRequest) {
        JDoodleRequest request = JDoodleRequest.of(incomingRequest.script(),
                incomingRequest.language(),
                incomingRequest.versionIndex(),
                config);
        JDoodleResponse response = jdoodleClient.execute(request);
        return response.output();
    }
}
