package bank.recommendationservice.fintech.controller;

import bank.recommendationservice.fintech.exception.NoTransactionsFoundException;
import bank.recommendationservice.fintech.exception.RecommendationNotFoundException;
import bank.recommendationservice.fintech.exception.RulesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //    @ExceptionHandler(RulesNotFoundException.class)
//    public ResponseEntity<String> handleRulesNotFoundException(RulesNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(RecommendationNotFoundException.class)
//    public ResponseEntity<String> handleRecommendationNotFoundException(RecommendationNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NoTransactionsFoundException.class)
//    public ResponseEntity<String> handleNoTransactionsFoundException(NoTransactionsFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler({RulesNotFoundException.class, RecommendationNotFoundException.class, NoTransactionsFoundException.class})
    public ResponseEntity<String> handleNotFoundExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}


