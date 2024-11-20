package com.team5.on_stage.global.exception;

import com.team5.on_stage.global.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    ErrorCode errorCode;
}
