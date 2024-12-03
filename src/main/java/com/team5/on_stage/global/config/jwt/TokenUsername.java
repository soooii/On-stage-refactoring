package com.team5.on_stage.global.config.jwt;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Parameter(hidden = true) // Swagger 사용 시 필수 파라미터로 표시되는 것을 방지
public @interface TokenUsername {

    String username() default "";

//    @Get("/token")
//    public ResponseEntity<String> getToken(@TokenRole TokenInfo token) {
//
//    }

}
