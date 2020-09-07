package ru.vineg;

/**
 * Created by Vineg on 09.04.2014.
 */
public class AccessDeniedException extends Exception {
    public AccessDeniedException(String s) {
        super(s);
    }

    public AccessDeniedException() {
    }
}
