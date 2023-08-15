package ru.skypro.lessons.springboot.weblibrary.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;

@RestControllerAdvice

public class EmployeeException {

    @ExceptionHandler(EmployeeNotFoundExceptions.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRequestException(IOException ioException) {
        return new ResponseEntity<>(ioException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
