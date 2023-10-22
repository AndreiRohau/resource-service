package my.course.exception;

import org.springframework.http.HttpStatus;

public class ResponseServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public static ResponseServiceException init400() {
        return new ResponseServiceException("400 – Validation failed or request body is invalid MP3", HttpStatus.BAD_REQUEST);
    }

    public static ResponseServiceException init404() {
        return new ResponseServiceException("404 – The resource with the specified id does not exist", HttpStatus.NOT_FOUND);
    }

    public static ResponseServiceException init500() {
        return new ResponseServiceException("500 – An internal server error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static boolean isExceptionOfCode(Exception e, int httpStatusCode) {
        return e.getClass() == ResponseServiceException.class
                && ((ResponseServiceException) e).getHttpStatusValue() == httpStatusCode;
    }

    public ResponseServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getHttpStatusValue() {
        return httpStatus.value();
    }
}
