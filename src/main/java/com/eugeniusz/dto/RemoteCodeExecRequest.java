package com.eugeniusz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RemoteCodeExecRequest(
        @Schema(description = "Executable code")
        @NotNull(message = "Code cannot be null")
        String script,

        @Schema(description = "Programming lang", example = "java or scala or python3")
        @NotNull(message = "Use one of the following languages: java, scala, python3")
        String language,

        @Schema(description = "lg version, 0-4, 0 for JDK 1.8.0_66, 4 - JDK 17.0.1", example = "0")
        @Pattern(regexp = "[0-4]", message = "Use one of the following versions: 0, 1, 2, 3, 4")
        String versionIndex) {
}
