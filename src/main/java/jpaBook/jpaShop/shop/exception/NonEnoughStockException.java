package jpaBook.jpaShop.shop.exception;

public class NonEnoughStockException extends RuntimeException {
    public NonEnoughStockException() {
        super();
    }

    public NonEnoughStockException(String message) {
        super(message);
    }

    public NonEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonEnoughStockException(Throwable cause) {
        super(cause);
    }

}
