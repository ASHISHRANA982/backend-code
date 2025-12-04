package college.project.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorClass> userNotFound(UserNotFound ex){
        ErrorClass errorClass=new ErrorClass(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorClass,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorClass>userAlreadyExist(UserAlreadyExistException ex){

        ErrorClass errorClass=new ErrorClass(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorClass,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DonorNotFound.class)
    public ResponseEntity<ErrorClass> donorNotFound(DonorNotFound ex){
        ErrorClass errorClass=new ErrorClass(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorClass,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DonorAlreadyExistsException.class)
    public ResponseEntity<ErrorClass> donorAlreadyExists(DonorAlreadyExistsException ex){
        ErrorClass errorClass=new ErrorClass(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorClass,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BloodBankNotFound.class)
    public ResponseEntity<ErrorClass> bloodbankNotFound(BloodBankNotFound ex){
        ErrorClass errorClass=new ErrorClass(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorClass,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BloodBankAlreadyExistsException.class)
    public ResponseEntity<ErrorClass> bloodbankAlreadyExists(BloodBankAlreadyExistsException ex){
        ErrorClass errorClass=new ErrorClass(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorClass,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(
                MethodArgumentNotValidException ex) {

            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorClass> handleUsernameNotFound(UsernameNotFoundException ex) {
        ErrorClass error = new ErrorClass(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                java.time.LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorClass> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        String message = "Invalid request. Please provide valid data.";

        if (ex.getCause() != null && ex.getCause().getMessage().contains("end-of-input")) {
            message = "Request body is empty. Please fill the form before submitting.";
        }
        System.out.println("Caught HttpMessageNotReadableException: " + message);

        ErrorClass error = new ErrorClass(
                HttpStatus.BAD_REQUEST.value(),
                message,
                java.time.LocalDateTime.now().toString()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorClass> handleAll(Exception ex){
        ErrorClass error=new ErrorClass(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                java.time.LocalDateTime.now().toString()

        );

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
