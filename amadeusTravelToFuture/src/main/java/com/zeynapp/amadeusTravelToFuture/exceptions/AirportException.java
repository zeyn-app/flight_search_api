package com.zeynapp.amadeusTravelToFuture.exceptions;

public final class AirportException extends RuntimeException{
    public static String AIRPORT_NOT_FOUND = "Airport not found exception";

    public AirportException(String message){
        super(message);
    }
}