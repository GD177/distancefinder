package com.geographical.distancefinder.exception;

/**
 * Non retryable exception
 */
public class NonRetryableException extends Exception {
    public NonRetryableException(String message){
        super(message);
    }
}