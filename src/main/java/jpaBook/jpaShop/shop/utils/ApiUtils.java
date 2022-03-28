package jpaBook.jpaShop.shop.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtils {
    public static <T> ResponseApiEntity<T> success (T data) {
        return new ResponseApiEntity<>(data);
    }
    public static ResponseApiEntity<?> error (Throwable throwable, HttpStatus httpStatus) {
        return new ResponseApiEntity<>(false, new ApiError(throwable, httpStatus), null);
    }
    public static ResponseApiEntity<?> error (String message, HttpStatus httpStatus) {
        return new ResponseApiEntity<>(false, new ApiError(message, httpStatus.value()), null);
    }
    @Getter
    public static class ResponseApiEntity<T> {
        private final boolean success;
        private final ApiError error;
        private final T data;
        public ResponseApiEntity (T data) {
            this.data = data;
            error = null;
            success = true;
        }
        public ResponseApiEntity (boolean success, ApiError error, T data) {
            this.success = success;
            this.error = error;
            this.data = data;
        }
    }
    @Getter
    public static class ApiError {
        private final String message;
        private final int status;
        public ApiError (Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status.value());
        }
        public ApiError(String message, HttpStatus status) {
            this(message, status.value());
        }
        public ApiError (String message, int status) {
            this.message = message;
            this.status = status;
        }
        public ApiError (int status) {
            this.message = "This API request has an error with status code is : " + status;
            this.status = status;
        }
    }
}
