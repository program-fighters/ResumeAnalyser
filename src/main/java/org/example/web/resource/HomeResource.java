package org.example.web.resource;

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
public class HomeResource extends BaseResource {

    @GetMapping(UrlConstants.APP_GREETING)
    public ResponseEntity<?> greeting() {
        return ResponseEntity
                .ok()
                .body(GreetingResponseDto
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
    private static class GreetingResponseDto {
        String info;
        String version;
    }
}
