package ro.facultate.aplicatieHR.utils;

public class HrException extends Exception {

    public HrException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }

    public HrException(String errorMessage){
        super(errorMessage);
    }
}
