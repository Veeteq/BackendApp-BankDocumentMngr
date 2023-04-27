package com.veeteq.finance.bankdocument.exception;

import java.text.MessageFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.veeteq.finance.bankdocument.dto.ErrorDetails;

@ControllerAdvice
public class CommonExceptionHandler {
  private final Logger LOG = LoggerFactory.getLogger(CommonExceptionHandler.class);
  
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
      LOG.warn(MessageFormat.format("ResourceNotFoundException: occured {0} {1}" , ex.getMessage(), request.getDescription(false)));
      
      ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
      return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
      LOG.warn(MessageFormat.format("Generic exception occured: {0} {1}" , ex.getMessage(), request.getDescription(false)));
    	
      ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
      return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
