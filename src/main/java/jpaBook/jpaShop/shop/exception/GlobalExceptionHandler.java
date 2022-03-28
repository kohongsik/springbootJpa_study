package jpaBook.jpaShop.shop.exception;

import javassist.NotFoundException;
import jpaBook.jpaShop.shop.utils.ApiUtils;
import jpaBook.jpaShop.shop.utils.ApiUtils.ResponseApiEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(
            {Exception.class, RuntimeException.class}
    )
    public ResponseApiEntity<?> authError (Exception e ) {
        return ApiUtils.error(e, HttpStatus.NOT_FOUND);
    }
}
