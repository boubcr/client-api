package com.sharpon.client.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ProblemDetail handleAccessDeniedException(WebRequest request, Exception e) {
        log.info("handling {}", e.getClass().getSimpleName());
        return createCustomResponse(e, HttpStatus.FORBIDDEN, Messages_.LOGIN_403, request);

    }

    @ExceptionHandler({ DataIntegrityViolationException.class, })
    public ProblemDetail handleDataAccessException(WebRequest request, Exception e) {
        log.error("", e);
        return createProblemDetail(e, HttpStatus.CONFLICT, "  Violation de l'intégrité des données : l'opération demandée ne peut pas être effectuée en raison de contraintes de base de données  " +HttpStatus.CONFLICT, " ", null, request);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return createCustomResponse(ex, HttpStatus.BAD_REQUEST, Messages_.ENTITY_NOTFOUND_ID, request);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ProblemDetail handleObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex,
                                                                       WebRequest request) {
        log.error("", ex);
        return createCustomResponse(ex, HttpStatus.BAD_REQUEST, Messages_.ERROR_VERSION_CHANGED, request);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(WebRequest request,
                                                                   MethodArgumentTypeMismatchException e) {
        String message = String.format("Invalid parameter: %s (%s)",
                                       Optional.of(e.getName()).orElse("unknown exception name"),
                                       Optional.ofNullable(e.getRequiredType()).map(Class::getSimpleName)
                                               .orElse("unknown"));
        return createProblemDetail(e, HttpStatus.BAD_REQUEST, message, message, null, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return createProblemDetail(ex, HttpStatus.NOT_FOUND, Messages_.ENTITY_NOTFOUND_ID, Messages_.ENTITY_NOTFOUND_ID,
                                   null, request);

    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.error("", ex);
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                   HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("", ex);
        String message = "";
        if (ex.getFieldError() != null) {
            String field = ex.getFieldError().getField();
            String defaultMessage = Objects.requireNonNullElse(ex.getFieldError().getDefaultMessage(), "");
            message = String.format("%s : %s", field, defaultMessage);
        }
        ProblemDetail problemDetail = createProblemDetail(ex, status, message, message, null, request);
        problemDetail.setProperty("timeStamp", LocalDateTime.now());
        problemDetail.setProperty("errorCode", ex.getDetailMessageCode());
        return createResponseEntity(problemDetail, headers, status, request);

    }


    private ProblemDetail createCustomResponse(Exception ex, HttpStatus status, String errorCode, String errorMessage,
                                               Object[] detailMessageArgs, WebRequest request) {

        ProblemDetail problem = createProblemDetail(ex, status, errorMessage, errorCode, detailMessageArgs, request);
        problem.setProperty("timeStamp", LocalDateTime.now());
        problem.setProperty("errorCode", errorCode);
        problem.setProperty("exception", ex.getMessage());
        return problem;

    }

    private ProblemDetail createCustomResponse(Exception e, HttpStatus badRequest, String errorCode,
                                               WebRequest request) {
        return createCustomResponse(e, badRequest, errorCode, errorCode, null, request);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.error("unexpected error ", ex);

        return createCustomResponse(ex, status,ex.getMessage(), ex.getMessage(), null, request);
    }
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("unexpected error ", ex);
        String errorMessage = messageSource.getMessage(Messages_.ERROR_500, null, LocaleContextHolder.getLocale());
        return createCustomResponse(ex, status, Messages_.ERROR_500, errorMessage, null, request);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodValidationException(
//            MethodValidationException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        log.error("", ex);
//       //
////        if (ex.get
////            String field = ex.getFieldError().getField();
////            String defaultMessage = Objects.requireNonNullElse(ex.getFieldError().getDefaultMessage(), "");
////            message = String.format("%s : %s", field, defaultMessage);
////
//        ProblemDetail problemDetail = createProblemDetail(ex, status, ex.getMessage(), ex.getMessage(), null, request);
//        problemDetail.setProperty("timeStamp", LocalDateTime.now());
//        problemDetail.setProperty("errorCode", status.getReasonPhrase()
//        return createResponseEntity(problemDetail, headers, status, request);
//
//    }
//    @ExceptionHandler({ BadCredentialsException.class })
//    public ProblemDetail handleBadCredentialsException(WebRequest request, Exception e) {
//        log.info("handling {}", e.getClass().getSimpleName());
//        return createCustomResponse(e, HttpStatus.BAD_REQUEST, Messages_.LOGIN_BAD_CREDENTIALS, request);
//    }
//    @ExceptionHandler({ ReinsuranceException.class })
//    public ProblemDetail handleReinsuranceException(WebRequest request, ReinsuranceException e) {
//        log.info("handling {}", e.getClass().getSimpleName());
//        return this.createCustomResponse(e, HttpStatus.BAD_REQUEST, e.getMessageKey(), e.getMessageKey(),
//                                         e.getMessageParams(), request);
//    }
//
//    @ExceptionHandler({ PolicyException.class })
//    public ProblemDetail handlePolicyException(WebRequest request, PolicyException e) {
//        log.info("handling {}", e.getClass().getSimpleName());
//        return this.createCustomResponse(e, HttpStatus.BAD_REQUEST, e.getMessageKey(), e.getMessageKey(),
//                                         e.getMessageParams(), request);
//    }
//
//    @ExceptionHandler({ EndorsementException.class })
//    public ProblemDetail handleEndorsementException(WebRequest request, EndorsementException e) {
//        log.info("handling {}", e.getClass().getSimpleName());
//        return this.createCustomResponse(e, HttpStatus.BAD_REQUEST, e.getMessageKey(), e.getMessageKey(),
//                                         e.getMessageParams(), request);
//    }
}
