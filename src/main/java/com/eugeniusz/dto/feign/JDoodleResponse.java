package com.eugeniusz.dto.feign;

public record JDoodleResponse(String output, int statusCode, String memory, String cpuTime) {
    // Podobnie, konstruktor, gettery i inne metody są automatycznie generowane.
}


