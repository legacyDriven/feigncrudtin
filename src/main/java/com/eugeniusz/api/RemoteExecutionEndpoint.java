package com.eugeniusz.api;

import com.eugeniusz.dto.RemoteCodeExecRequest;
import com.eugeniusz.services.feign.JDoodleFeignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/java")
@RequiredArgsConstructor
public class RemoteExecutionEndpoint {

    private final JDoodleFeignService jDoodleFeignService;

    @GetMapping("/home")
    public String home(){
        return "Welcome to Java Execution API";
    }

    @Operation(summary = "Java code remote execution")
    @PostMapping("/execute")
    public ResponseEntity<String> executeCode(@RequestBody RemoteCodeExecRequest code){
        return ResponseEntity.ok(jDoodleFeignService.executeCode(code));
    }
}
