package org.example.web.controller;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.Constant.UrlConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController extends BaseController {

    @GetMapping(UrlConstants.APP_HEALTH)
    public ResponseEntity<?> home() {
        return ResponseEntity
                .ok()
                .body(AppInfo
                        .builder()
                        .info("welcome to resume Analyser")
                        .version("0.0.1-beta")
                        .build()
                );
    }

    @Override
    protected String getBaseUri() {
        return "";
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonPropertyOrder({"version", "info"})
    private static class AppInfo {
        String info;
        String version;
    }
}
