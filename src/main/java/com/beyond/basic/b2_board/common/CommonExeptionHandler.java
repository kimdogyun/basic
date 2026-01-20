package com.beyond.basic.b2_board.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

//컨트롤러 어노테이션이 붙어있는 모든 클래스의 예외를 해당 아래 클래스에서 인터셉팅(가로채기)
@RestControllerAdvice
public class CommonExeptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegal(IllegalArgumentException e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(400)
                .error_message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValiedExeption(MethodArgumentNotValidException e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(400)
                .error_message(e.getFieldError().getDefaultMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notSuchElement(NoSuchElementException e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(404)
                .error_message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        e.printStackTrace();
        CommonErrorDto dto = CommonErrorDto.builder()
                .status_code(500)
                .error_message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
    }

}
