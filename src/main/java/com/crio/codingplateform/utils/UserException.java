package com.crio.codingplateform.utils;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserException extends RuntimeException
{
    private final LocalDateTime timestamp;
    private final long userId;
    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode,long userId) {
        super(errorCode.getMessage(userId));
        this.timestamp = LocalDateTime.now();
        this.userId = userId;
        this.errorCode = errorCode;
    }

}
