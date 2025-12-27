package main.java.by.geometry.exception;

public class GeometryException extends RuntimeException {
    public GeometryException(String message) {
        super(message);
    }

    public GeometryException(String message, Object... args) {
        super(String.format(message, args));
    }
}