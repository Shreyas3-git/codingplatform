package com.crio.codingplateform.utils;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.crio.codingplateform.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<CommonResponse> handleUserException(UserException ex) {
        HttpStatus status;
        String message;
        switch (ex.getErrorCode()) {
            case USER_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                message = "User not found";
                break;
            case USER_ALREADY_EXISTS:
                status = HttpStatus.CONFLICT;
                message = "User already exists";
                break;
            case INVALID_SCORE:
                status = HttpStatus.NOT_ACCEPTABLE;
                message = "Invalid Score";
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "An unexpected error occurred";
        }

        CommonResponse response = CommonResponse.builder()
                .status("Failure")
                .message(message)
                .errorCode("400")
                .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        String[] errorMessage = {""};
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            errorMessage[0] += ((FieldError) error).getField() + "--"
                        +error.getDefaultMessage();
        });

        CommonResponse response = CommonResponse.builder()
                .status("Failure")
                .message("Validation failed "+errorMessage[0])
                .errorCode("400")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericException(Exception ex) {
        CommonResponse response = CommonResponse.builder()
                .status("Failure")
                .message("An unexpected error occurred")
                .errorCode("500")
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
