package com.team5.on_stage.global.config.auth.refresh;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "Reissue Controller", description = "Refresh Token 재발급 담당 컨트롤러")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class ReissueController {

    private final ReissueService reissueService;

    @Operation(summary = "Refresh Token 재발급 엔드포인트", description = "Refresh Token의 재발급을 요청한다. 사용자 경험 향상을 위해 Access Token의 재발급도 함께 수행한다.")
    @PostMapping("/reissue")
    public ResponseEntity<Void> reissue(HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {

        reissueService.reissueRefreshToken(request, response);

        return ResponseEntity.ok().build();
    }
}
