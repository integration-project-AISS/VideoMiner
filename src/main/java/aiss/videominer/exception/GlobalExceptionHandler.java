package aiss.videominer.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Errores de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, Object> invalidParams = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> fieldDetails = new HashMap<>();
            fieldDetails.put("msg", fieldError.getDefaultMessage());
            fieldDetails.put("location", "body");
            fieldDetails.put("param", fieldError.getField());
            fieldDetails.put("value", fieldError.getRejectedValue() != null
                    ? fieldError.getRejectedValue().toString() : "null");
            invalidParams.put(fieldError.getField(), fieldDetails);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("type", "about:blank");
        body.put("title", "Bad Request");
        body.put("status", 400);
        body.put("detail", "Incorrect request parameters");
        body.put("instance", request.getRequestURI());
        body.put("invalid-params", invalidParams);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // 404 - Recurso no encontrado
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("type", "about:blank");
        body.put("title", ex.getStatusCode().toString());
        body.put("status", ex.getStatusCode().value());
        body.put("detail", ex.getReason());
        body.put("instance", request.getRequestURI());

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}