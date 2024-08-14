package com.example.todo_api.controller.advice;

import com.example.todo_api.service.task.TaskEntityNotFoundException;
import com.example.todoapi.model.ResourceNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 差し込みたい処理に記載
public class CustomExceptionHandler {

    @ExceptionHandler(TaskEntityNotFoundException.class) // TaskEntityNotFoundExceptionが発生した時のhandler登録
    public ResponseEntity<ResourceNotFoundError> handleTaskEntityNotFoundException(TaskEntityNotFoundException e) {
        var error = new ResourceNotFoundError();
        error.setDetail(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
