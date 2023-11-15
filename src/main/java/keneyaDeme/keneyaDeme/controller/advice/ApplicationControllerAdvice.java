package keneyaDeme.keneyaDeme.controller.advice;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import keneyaDeme.keneyaDeme.dto.ErrorEntity;

@ControllerAdvice
public class ApplicationControllerAdvice {

     @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorEntity> handleUserNotFoundException(NoSuchElementException ex) {
        ErrorEntity errorEntity = new ErrorEntity("Utilisateur non trouvé.");
        return new ResponseEntity<>(errorEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorEntity> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorEntity errorEntity = new ErrorEntity(ex.getMessage());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

     

     @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorEntity> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, WebRequest request) {
        String paramName = ex.getParameterName();
        String errorMessage = "Le paramètre requis '" + paramName + "' est manquant dans la requête.";
        ErrorEntity errorEntity = new ErrorEntity(errorMessage);
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }


    // @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    // @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    // public ResponseEntity<ErrorEntity> handleHttpMediaTypeNotSupportedException(
    //         HttpMediaTypeNotSupportedException ex, WebRequest request) {
    //     String error = "Type de fichier non prise en charge.";
    //     ErrorEntity errorEntity = new ErrorEntity(error);
    //     return new ResponseEntity<>(errorEntity, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    // }
     @ExceptionHandler(MethodArgumentNotValidException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request){
        final  String[] msg = {""};
        exception.getBindingResult().getAllErrors().forEach((error)-> {
            msg[0] += "'"+((FieldError) error).getField()+"' : "+error.getDefaultMessage()+", ";
        });
        return new ErrorEntity(
                msg[0]
        );
    }
}