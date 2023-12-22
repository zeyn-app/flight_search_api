package com.zeynapp.amadeusTravelToFuture.exceptions;

public final class AirportException extends RuntimeException{
    public static String AIRPORT_NOT_FOUND = "Airport not found exception";
    public static String AIRPORT_EXISTS = "Airport exists exception";

    public AirportException(String message){
        super(message);
    }
}