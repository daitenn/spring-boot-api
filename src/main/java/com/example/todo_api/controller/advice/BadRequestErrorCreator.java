package com.example.todo_api.controller.advice;

import com.example.todoapi.model.BadRequestError;
import com.example.todoapi.model.InvalidParam;
import java.util.stream.Collectors;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class BadRequestErrorCreator {

  public static BadRequestError from(MethodArgumentNotValidException ex) {
    var invalidParamList = ex.getFieldErrors()
        .stream()
        .map(fieldError -> {
          var invalidParam = new InvalidParam();
//                    invalidParam.setName(fieldError.getField());
//                    invalidParam.setReason(fieldError.getDefaultMessage());
          return invalidParam;
        })
        .collect(Collectors.toList());
    var error = new BadRequestError();
    error.setInvalidParams(invalidParamList);

    return error;
  }
}
