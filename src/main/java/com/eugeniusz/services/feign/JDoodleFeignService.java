package com.eugeniusz.services.feign;

import com.eugeniusz.dto.RemoteCodeExecRequest;
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
