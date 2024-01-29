package com.eugeniusz.services.feign;

public record JDoodleAuthTokenRequest(String clientId, String clientSecret) {
    public static JDoodleAuthTokenRequest of(JDoodleConfig config) {
        return new JDoodleAuthTokenRequest(config.getApiKey(), config.getApiSecret());
    }
    // Konstruktor, gettery i inne metody są automatycznie generowane dla rekordów w Javie.
}

