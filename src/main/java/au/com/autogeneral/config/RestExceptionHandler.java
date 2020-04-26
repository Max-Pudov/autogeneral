package au.com.autogeneral.config;

import au.com.autogeneral.error.ToDoItemNotFoundError;
import au.com.autogeneral.error.ValidationErrorDetails;
import au.com.autogeneral.error.ToDoItemValidationError;
import au.com.autogeneral.exception.TodoNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return new ResponseEntity<>(new ApiError(ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
    ToDoItemValidationError validationError = new ToDoItemValidationError(
        e.getConstraintViolations()
            .stream()
            .map(RestExceptionHandler::convert)
            .toArray(ValidationErrorDetails[]::new),
        "ValidationError"
    );
    return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TodoNotFoundException.class)
  public ResponseEntity<Object> handleConstraintViolationException(TodoNotFoundException e) {
    ToDoItemNotFoundError validationError = new ToDoItemNotFoundError(
        new String [] { e.getMessage() },
        "NotFoundError"
    );
    return new ResponseEntity<>(validationError, HttpStatus.NOT_FOUND);
  }

  private static ValidationErrorDetails convert(ConstraintViolation<?> cv) {
    return new ValidationErrorDetails(
        //TODO mpudov: what is location?
        cv.getRootBean().toString(),
        cv.getPropertyPath().toString(),
        cv.getMessage(),
        cv.getInvalidValue().toString()
    );
  }
}
