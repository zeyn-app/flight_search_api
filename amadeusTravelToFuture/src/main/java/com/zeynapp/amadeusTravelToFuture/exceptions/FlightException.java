package com.zeynapp.amadeusTravelToFuture.exceptions;

public final class FlightException extends RuntimeException {
    public static String DEPARTURE_AND_ARRIVAL_AIRPORT_CANNOT_BE_THE_SAME
            = "Departure and arrival airports can not be the same";
    public static String DEPARTURE_DATE_CANNOT_BE_AFTER_RETURN_DATE
            = "Departure date can not be after the return date";

    public FlightException(String message){
        super(message);
    }
}
