package com.eugeniusz.dto.feign;

import com.eugeniusz.httpclients.feign.JDoodleConfig;

public record JDoodleAuthTokenRequest(String CLIENT_ID, String CLIENT_SECRET) {
    public static JDoodleAuthTokenRequest of(JDoodleConfig config) {
        return new JDoodleAuthTokenRequest(config.getApiKey(), config.getApiSecret());
    }
    // Konstruktor, gettery i inne metody są automatycznie generowane dla rekordów w Javie.
}



//curl -X POST https://api.jdoodle.com/v1/auth-token \
//        -H "Content-Type: application/json" \
//        -d '{"clientId": "159caf339d4c7282b8322c76e3cce4cd", "clientSecret": "79babfb07549243b38d1e8e7c1ddb93dcacda8f3a7e50062a0e301848025cd77"}'
//
//
//curl -X POST https://api.jdoodle.com/v1/auth-token -H "Content-Type: application/json" -d "{\"clientId\": \"159caf339d4c7282b8322c76e3cce4cd\", \"clientSecret\": \"79babfb07549243b38d1e8e7c1ddb93dcacda8f3a7e50062a0e301848025cd77\"}"
