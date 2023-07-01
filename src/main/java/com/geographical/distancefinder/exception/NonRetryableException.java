package com.geographical.distancefinder.exception;

/**
 * NonRetryable exception
 */
public class NonRetryableException extends Exception {
    public NonRetryableException(String message){
        super(message);
    }
}