package me.fixca.clipstretcher.exception;

import lombok.Getter;

public class IllegalTypeException extends Exception {

    @Getter
    private String fileName;

    public IllegalTypeException(String fileName) {
        super(String.format("'%s' should be a directory!"));
        this.fileName = fileName;
    }

}
