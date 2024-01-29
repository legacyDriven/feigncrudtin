package com.eugeniusz.services.feign;

import org.springframework.beans.factory.annotation.Value;

public record JDoodleRequest(String clientId, String clientSecret, String script, String language, String versionIndex) {

    public static JDoodleRequest of(String script, String language, String versionIndex, JDoodleConfig config){

        return new JDoodleRequest(config.getApiKey(), config.getApiSecret(), script, language, versionIndex);
    }
}



