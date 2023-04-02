package jpabook1.jpashop1.exception;


public class NotEnoughStockException extends RuntimeException{

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message){
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause){
        super(message, cause);
    }
}
