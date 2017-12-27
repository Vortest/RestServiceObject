package com.vortest;

/**
 * Created by csears on 3/31/17.
 */
public class AsyncWaitTimeoutException extends RuntimeException {
    public AsyncWaitTimeoutException() {
        super();
    }

    public AsyncWaitTimeoutException(String message) {
        super(message);
    }
}
